package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.JobPostingRequest;
import com.careerclub.careerclub.DTOs.JobUpdatingRequest;
import com.careerclub.careerclub.DTOs.JobsFilter;
import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.Events.JobCreatedEvent;
import com.careerclub.careerclub.Repositories.*;
import com.careerclub.careerclub.Utils.Dateformat;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
public class JobsService {


    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final JobTypeRepository jobTypeRepository;
    private final LocationRepository locationRepository;
    private final IndustryRepository industryRepository;

    public JobsService(JobRepository jobRepository, CompanyRepository companyRepository, JobTypeRepository jobTypeRepository, LocationRepository locationRepository, IndustryRepository industryRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.jobTypeRepository = jobTypeRepository;
        this.locationRepository = locationRepository;
        this.industryRepository = industryRepository;
    }

    public Page<Job> getAllJobs(Pageable pageable){
        var jobs = jobRepository.findAll(pageable);
        return jobs;
    }

    public Job postJob(JobPostingRequest jobPostingRequest) throws ParseException {
        Job newJob = new Job();
        var company = companyRepository.findById(jobPostingRequest.getCompanyId());
        var jobType = jobTypeRepository.findById(jobPostingRequest.getJobTypeId());
        var industry = industryRepository.findById(jobPostingRequest.getIndustryId());
        var location  = locationRepository.findById(jobPostingRequest.getLocationId());
        company.ifPresentOrElse(newJob::setCompany,()->{
            throw new RecordNotFoundException("Company posting the job doesn't exist");
        });
        jobType.ifPresentOrElse(newJob::setJobType,()->{
            throw new RecordNotFoundException("The selected job type doesn't exist");
        });
        industry.ifPresentOrElse(newJob::setIndustry,()->{
            throw new RecordNotFoundException("Given industry doesn't exist.");
        });
        location.ifPresentOrElse(newJob::setLocation,()->{
            throw new RecordNotFoundException("The given location doesn't exist.");
        });
        newJob.setDescription(jobPostingRequest.getDescription());
        newJob.setDeadline(Dateformat.formatDate(jobPostingRequest.getDeadline()));
        newJob.setTitle(jobPostingRequest.getTitle());
        newJob.setQualification(jobPostingRequest.getQualification());
        newJob.registerCreateEvent();
        jobRepository.save(newJob);
        return newJob;
    }

    public Optional<Job> updateJob(Long id, JobUpdatingRequest jobUpdatingRequest) throws ParseException {
        var jobToUpdate = jobRepository.findById(id).get();
        var jobType = jobTypeRepository.findById(jobUpdatingRequest.getJobTypeId());
        jobType.ifPresentOrElse(j->{
            jobToUpdate.setJobType(j);
        },()->{
            throw new RecordNotFoundException("The given job type doesn't exist.");
        });
        if(jobToUpdate==null){
            throw new RecordNotFoundException("Job doesn't Exist");
        }


        jobToUpdate.setQualification(jobUpdatingRequest.getQualification());
        jobToUpdate.setDeadline(Dateformat.formatDate(jobUpdatingRequest.getDeadline()));
        jobToUpdate.setTitle(jobUpdatingRequest.getTitle());
        jobToUpdate.setDescription(jobUpdatingRequest.getDescription());
        jobRepository.save(jobToUpdate);

        return Optional.of(jobToUpdate);
    }

    public Optional<Job> getJobById(Long id){
        return jobRepository.findById(id);
    }

    public String jobToDelete(Long id){
        var jobToBeDeleted = jobRepository.findById(id);
        jobToBeDeleted.ifPresentOrElse(jobRepository::delete, ()->{
            throw new RecordNotFoundException("Job doesn't exist");
        });
        return "Job deleted successfully";
    }

    public Page<Job> getAllJobs(JobsFilter jobsFilter, Pageable pageable) {
        return jobRepository.findAll(jobsFilter.jobExample(),pageable);
    }
}

