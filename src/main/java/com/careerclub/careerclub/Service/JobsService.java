package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.JobPostingRequest;
import com.careerclub.careerclub.DTOs.JobUpdatingRequest;
import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.Repositories.CompanyRepository;
import com.careerclub.careerclub.Repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobsService {
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public JobsService(JobRepository jobRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    public List<Job> getAllJobs(){
        var jobs = jobRepository.findAll();
        return jobs;
    }

    public Job postJob(JobPostingRequest jobPostingRequest){
        Job newJob = new Job();
        var company = companyRepository.findById(jobPostingRequest.getCompanyId());
        company.ifPresentOrElse(c ->{
            newJob.setDescription(jobPostingRequest.getDescription());
            newJob.setDeadline(jobPostingRequest.getDeadline());
            newJob.setJobType(jobPostingRequest.getJobType());
            newJob.setTitle(jobPostingRequest.getTitle());
            newJob.setQualification(jobPostingRequest.getQualification());
            newJob.setCompany(c);
            jobRepository.save(newJob);
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
            job1.setJobType(jobUpdatingRequest.getJobType());
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
}

