package com.careerclub.careerclub.Report;


import com.careerclub.careerclub.Repositories.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service

public class ReportsService {
    private final JobRepository jobRepository;

    public ReportsService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    public List<JobsByLocationReports> getJobsByLocation() {
        return this.jobRepository.getJobsByLocations();
    }
}
