package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.DTOs.JobPostingRequest;
import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.Repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobsService {
    private final JobRepository jobRepository;

    public JobsService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> getAllJobs(){
        var jobs = jobRepository.findAll();
        return jobs;
    }

    public Job postJob(JobPostingRequest jobPostingRequest){
        Job newJob = new Job();
        newJob.setDescription(jobPostingRequest.getDescription());
        newJob.setDeadline(jobPostingRequest.getDeadline());
        newJob.setJobType(jobPostingRequest.getJobType());
        newJob.setTitle(jobPostingRequest.getTitle());
        newJob.setQualification(jobPostingRequest.getQualification());
        jobRepository.save(newJob);
        return newJob;
    }

    public Optional<Job> updateJob(Long id, Job job){
        var jobToUpdate = jobRepository.findById(id);

        jobToUpdate.ifPresentOrElse(job1 -> {
            job1.setQualification(job.getQualification());
            job1.setDeadline(job.getDeadline());
            job1.setTitle(job.getTitle());
            job1.setJobType(job.getJobType());
            jobRepository.save(job1);
        },()->{

        });
        return jobToUpdate;
}
}
