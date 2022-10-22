package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.JobTypeRequest;
import com.careerclub.careerclub.Entities.JobType;
import com.careerclub.careerclub.Enums.JobTypeEnums;
import com.careerclub.careerclub.Repositories.JobTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobTypeService {
    private final JobTypeRepository jobTypeRepository;
    private  JobTypeEnums jobTypeEnums;

    public JobTypeService(JobTypeRepository jobTypeRepository) {
        this.jobTypeRepository = jobTypeRepository;
    }

    public List<JobType> getAllJobs(){
        var jobTypes = jobTypeRepository.findAll();
        return jobTypes;
    }
    public JobType createJobType(JobTypeRequest jobTypeRequest){
        JobType jobType = new JobType();
        if(jobTypeRequest.getName().equals(jobTypeEnums.FULLTIME) || jobTypeRequest.getName().equals(jobTypeEnums.HYBRID) || jobTypeRequest.getName().equals(jobTypeEnums.CONTRACT)){
            jobType.setName(jobTypeRequest.getName());
            jobTypeRepository.save(jobType);
            return jobType;
        }
        throw new RecordNotFoundException("Job type is invalid");
    }
//    public enum JobTypeEnums {
//        FULLTIME,
//        REMOTE,
//        HYBRID,
//        CONTRACT,
//        INTERNSHIP
//    }

}
