package com.careerclub.careerclub.Jobs;

import com.careerclub.careerclub.DTOs.JobPostingRequest;
import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.Repositories.CompanyRepository;
import com.careerclub.careerclub.Repositories.IndustryRepository;
import com.careerclub.careerclub.Repositories.JobRepository;
import com.careerclub.careerclub.Repositories.JobTypeRepository;
import com.careerclub.careerclub.Service.JobsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobsServiceTest {
    @InjectMocks
    JobsService jobsService;

    @Mock
    JobRepository jobRepository;

    @Mock
    CompanyRepository companyRepository;

    @Mock
    JobTypeRepository jobTypeRepository;

    @Mock
    IndustryRepository industryRepository;

    @Test
    @DisplayName("testing job retrieval by id")
    public void test_jobs_by_id(){
        when(jobRepository.findById(any(Long.class))).thenReturn(Optional.of(new Job()));
        jobsService.getJobById(1L);
        verify(jobRepository).findById(any(Long.class));
    }




}
