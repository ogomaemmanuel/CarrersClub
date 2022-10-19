package com.careerclub.careerclub.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.Entities.Application;
import com.careerclub.careerclub.Repositories.ApplicationRepository;
import com.careerclub.careerclub.Repositories.JobRepository;
import com.careerclub.careerclub.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final AmazonS3 amazonS3;

    public ApplicationService(ApplicationRepository applicationRepository, JobRepository jobRepository, UserRepository userRepository, AmazonS3 amazonS3) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.amazonS3 = amazonS3;
    }

    public List<Application> getAllApplications(){
        var applications = applicationRepository.findAll();
        return applications;
    }

    public Optional<Application> getApplicationById(Long id){
        var application = applicationRepository.findById(id);
        return application;
    }

//    public Application makeAnApplication(ApplicationRequest applicationRequest){
//        Application newApplication = new Application();
//        var user = userRepository.findById(applicationRequest.getUserId());
//        user.ifPresentOrElse(u ->{
//            var job = jobRepository.findById(applicationRequest.getJobId());
//            job.ifPresentOrElse(j ->{
//                newApplication.setJob(j);
//                newApplication.setUser(u);
//                newApplication.setCv(applicationRequest.getCv());
//                applicationRepository.save(newApplication);
//            }, ()->{
//               throw new RecordNotFoundException("Job application doesn't exist") ;
//            });
//        }, ()->{
//            throw new RecordNotFoundException("Company doesn't exist");
//        });
//        return newApplication;
//    }
    public void upload(String path, String fileName, Optional<Map<String, String>> optionalMetaData, InputStream inputStream){
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map ->{
            if(!map.isEmpty()){
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        try{
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);
        }catch (AmazonServiceException e){
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    public byte[] download(String path, String key){
        try{
            S3Object object = amazonS3.getObject(path, key);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        }catch(AmazonServiceException | IOException e){
            throw new IllegalStateException("Failed to download the file", e);
        }
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
