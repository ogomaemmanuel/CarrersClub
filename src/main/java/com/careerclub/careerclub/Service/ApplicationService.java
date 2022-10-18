package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.ApplicationRequest;
import com.careerclub.careerclub.Entities.Application;
import com.careerclub.careerclub.Repositories.ApplicationRepository;
import com.careerclub.careerclub.Repositories.JobRepository;
import com.careerclub.careerclub.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public ApplicationService(ApplicationRepository applicationRepository, JobRepository jobRepository, UserRepository userRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public List<Application> getAllApplications(){
        var applications = applicationRepository.findAll();
        return applications;
    }

    public Optional<Application> getApplicationById(Long id){
        var application = applicationRepository.findById(id);
        return application;
    }

    public Application makeAnApplication(ApplicationRequest applicationRequest){
        Application newApplication = new Application();
        var user = userRepository.findById(applicationRequest.getUserId());
        user.ifPresentOrElse(u ->{
            var job = jobRepository.findById(applicationRequest.getJobId());
            job.ifPresentOrElse(j ->{
                newApplication.setJob(j);
                newApplication.setUser(u);
                newApplication.setCv(applicationRequest.getCv());
                applicationRepository.save(newApplication);
            }, ()->{
               throw new RecordNotFoundException("Job application doesn't exist") ;
            });
        }, ()->{
            throw new RecordNotFoundException("Company doesn't exist");
        });
        return newApplication;
    }

    public String deleteApplication(Long id){
        var application = applicationRepository.findById(id);
        application.ifPresentOrElse(a ->{
            applicationRepository.delete(a);
        }, ()->{
            throw new RecordNotFoundException("Application doesn't exist");
        });

        return "Application deleted successfully";
    }
}
