package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.JobPostingRequest;
import com.careerclub.careerclub.DTOs.JobUpdatingRequest;
import com.careerclub.careerclub.DTOs.JobsFilter;
import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.Events.JobCreatedEvent;
import com.careerclub.careerclub.Repositories.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobsService {

    private final ApplicationEventPublisher eventPublisher;
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final JobTypeRepository jobTypeRepository;
    private final LocationRepository locationRepository;
    private final IndustryRepository industryRepository;

    public JobsService(ApplicationEventPublisher eventPublisher, JobRepository jobRepository, CompanyRepository companyRepository, JobTypeRepository jobTypeRepository, LocationRepository locationRepository, IndustryRepository industryRepository) {
        this.eventPublisher = eventPublisher;
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

    public Job postJob(JobPostingRequest jobPostingRequest){
        Job newJob = new Job();
        var company = companyRepository.findById(jobPostingRequest.getCompanyId());
        var jobType = jobTypeRepository.findById(jobPostingRequest.getJobTypeId());
        var industry = industryRepository.findById(jobPostingRequest.getIndustryId());
        company.ifPresentOrElse(c ->{
            jobType.ifPresentOrElse(jobType1 -> {
                industry.ifPresentOrElse(i->{
                    newJob.setDescription(jobPostingRequest.getDescription());
                    newJob.setDeadline(jobPostingRequest.getDeadline());
                    newJob.setJobType(jobType1);
                    newJob.setTitle(jobPostingRequest.getTitle());
                    newJob.setQualification(jobPostingRequest.getQualification());
                    newJob.setCompany(c);
                    newJob.setIndustry(i);
                    jobRepository.save(newJob);
                    this.eventPublisher.publishEvent(new JobCreatedEvent(newJob));
                },()->{
                    throw new RecordNotFoundException("Given industry doesn't exist.");
                });
            }, ()->{
                throw new RecordNotFoundException("The selected job type doesn't exist");
            });

        },() ->{
            throw new RecordNotFoundException("Company posting the job doesn't exist");
        });

        return newJob;
    }

    public Optional<Job> updateJob(Long id, JobUpdatingRequest jobUpdatingRequest) {
        var jobToUpdate = jobRepository.findById(id);

        jobToUpdate.ifPresentOrElse(job1 -> {
            job1.setQualification(jobUpdatingRequest.getQualification());
            job1.setDeadline(jobUpdatingRequest.getDeadline());
            job1.setTitle(jobUpdatingRequest.getTitle());
            job1.setJobType(jobUpdatingRequest.getJobTypeId());
            job1.setDescription(jobUpdatingRequest.getDescription());
            jobRepository.save(job1);
        }, () -> {
            throw new RecordNotFoundException("Job doesn't Exist");
        });
        return jobToUpdate;
    }

    public Optional<Job> getJobById(Long id){
        var job = jobRepository.findById(id);
        return job;
    }

    public String jobToDelete(Long id){
        var jobToBeDeleted = jobRepository.findById(id);
        jobToBeDeleted.ifPresentOrElse(job -> {
            jobRepository.delete(job);
        }, ()->{
            throw new RecordNotFoundException("Job doesn't exist");
        });
        return "Job deleted successfully";
    }

    public Page<Job> getAllJobs(JobsFilter jobsFilter, Pageable pageable) {
        return jobRepository.findAll(jobsFilter.jobExample(),pageable);
    }
}

