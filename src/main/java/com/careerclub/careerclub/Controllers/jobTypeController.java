package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.JobTypeRequest;
import com.careerclub.careerclub.Entities.JobType;
import com.careerclub.careerclub.Service.JobTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobtype")
public class jobTypeController {
    private final JobTypeService jobTypeService;

    public jobTypeController(JobTypeService jobTypeService) {
        this.jobTypeService = jobTypeService;
    }

    @PostMapping
    public ResponseEntity<JobType> postJobType(@RequestBody JobTypeRequest jobTypeRequest){
        return ResponseEntity.ok(jobTypeService.createJobType(jobTypeRequest));
    }
}
