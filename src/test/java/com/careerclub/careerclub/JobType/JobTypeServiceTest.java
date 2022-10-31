package com.careerclub.careerclub.JobType;


import com.careerclub.careerclub.DTOs.JobTypeRequest;
import com.careerclub.careerclub.Entities.JobType;
import com.careerclub.careerclub.Enums.JobTypeEnums;
import com.careerclub.careerclub.Repositories.JobTypeRepository;
import com.careerclub.careerclub.Service.JobTypeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobTypeServiceTest {
    @InjectMocks
    JobTypeService jobTypeService;
    @Mock
    JobTypeRepository jobTypeRepository;


    @Test
    @DisplayName("testing all job type retrieval")
    public void test_all_job(){
        when(jobTypeRepository.findAll()).thenReturn(new ArrayList<>());
        jobTypeService.getAllJobs();
        verify(jobTypeRepository).findAll();
    }

    @Test
    @DisplayName("testing job type creation")
    public void test_jobType_creation(){
        when(jobTypeRepository.save(any(JobType.class))).thenReturn(new JobType());
        var jobType = new JobTypeRequest();
        jobType.setName("REMOTE");
        jobTypeService.createJobType(jobType);
        verify(jobTypeRepository).save(any(JobType.class));
    }

}
