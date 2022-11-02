package com.careerclub.careerclub.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.CvDownloadRequest;
import com.careerclub.careerclub.Entities.Application;
import com.careerclub.careerclub.Repositories.ApplicationRepository;
import com.careerclub.careerclub.Repositories.JobRepository;
import com.careerclub.careerclub.Repositories.UserRepository;
import com.careerclub.careerclub.Utils.EmailDetails;
import com.careerclub.careerclub.Utils.FileUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final AmazonS3 amazonS3;
    private final EmailServiceImplement emailServiceImplement;

    public ApplicationService(ApplicationRepository applicationRepository, JobRepository jobRepository, UserRepository userRepository, AmazonS3 amazonS3, EmailServiceImplement emailServiceImplement) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.amazonS3 = amazonS3;
        this.emailServiceImplement = emailServiceImplement;
    }

    public List<Application> getAllApplications(){
        var applications = applicationRepository.findAll();
        return applications;
    }

    public Optional<Application> getApplicationById(Long id){
        var application = applicationRepository.findById(id);
        if(application.isEmpty()){
            throw new RecordNotFoundException("Application with the given id doesn't exist");
        }
        return application;
    }

    public Application makeAnApplication(MultipartFile cv, Long jobId, Long userId){
        Application newApplication = new Application();
        var user = userRepository.findById(userId);
        var job = jobRepository.findById(jobId);
        user.ifPresentOrElse(u -> {
            job.ifPresentOrElse(j -> {
                var fileUpload = new FileUpload(amazonS3);
                var file = fileUpload.upload(cv);
                newApplication.setJob(j);
                newApplication.setUser(u);
                newApplication.setCvPath(file.get(0));
                newApplication.setCvFileName(file.get(1));
                applicationRepository.save(newApplication);
                var emailDetails = new EmailDetails();
                emailDetails.setRecipient(u.getEmail());
                emailDetails.setSubject("You have applied for "+ " "+j.getTitle() + "at " +j.getCompany().getName());
                emailServiceImplement.sendSimpleMail(emailDetails);
            },() -> {
                throw new RecordNotFoundException("Job doesn't exist");
            });
        }, () -> {
            throw new RecordNotFoundException("User doesn't exist");
        });
        return newApplication;
    }

    public InputStream getCv(CvDownloadRequest downloadRequest) {
        S3Object s3Object= amazonS3.getObject(downloadRequest.getCvPath(),downloadRequest.getCvFileName());
        return s3Object.getObjectContent();
    }


//    public String deleteApplication(Long id){
//        var application = applicationRepository.findById(id);
//        application.ifPresentOrElse(a ->{
//            applicationRepository.delete(a);
//        }, ()->{
//            throw new RecordNotFoundException("Application doesn't exist");
//        });
//
//        return "Application deleted successfully";
//    }
}