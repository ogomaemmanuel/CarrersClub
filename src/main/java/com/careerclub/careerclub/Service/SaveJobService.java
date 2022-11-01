package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.SaveJobRequest;
import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.Repositories.JobRepository;
import com.careerclub.careerclub.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class SaveJobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;


    public SaveJobService(JobRepository jobRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public String saveJob(SaveJobRequest saveJobRequest){
        //Fetching
        var job = jobRepository.findById(saveJobRequest.getJobId());
        var user = userRepository.findById(saveJobRequest.getUserId());


        user.ifPresentOrElse(u -> {
            job.ifPresentOrElse(u::addJob,()->{
                throw new RecordNotFoundException("Job doesn't exist.");
            });
            userRepository.save(u);
        },()->{
            throw new RecordNotFoundException("User doesn't exist");
        });

        return "Job saved successfully";
    }

    public List<?> getAllUserSavedJobs(Long userId){
        var user = userRepository.findById(userId);
        List<Job> saved_jobs = new ArrayList<Job>();
        user.ifPresentOrElse(u->{
            saved_jobs.addAll(u.getSavedJobs());
        },()->{
            throw new RecordNotFoundException("User with the given id doesn't exist.");
        });
        return saved_jobs;
    }


}
