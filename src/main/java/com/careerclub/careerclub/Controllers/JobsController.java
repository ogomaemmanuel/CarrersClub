package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.JobPostingRequest;
import com.careerclub.careerclub.DTOs.JobUpdatingRequest;
import com.careerclub.careerclub.DTOs.JobsFilter;
import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.Service.JobsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Jobs controller")
@RestController
@RequestMapping(value = "/jobs")
public class JobsController {
    private final JobsService jobsService;

    public JobsController(JobsService jobsService) {
        this.jobsService = jobsService;
    }

    @GetMapping
    public ResponseEntity<Page<Job>> getAllJobs(JobsFilter jobsFilter, Pageable pageable){
        return  ResponseEntity.ok(jobsService.getAllJobs(jobsFilter,pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id){
        return ResponseEntity.of(jobsService.getJobById(id));
    }

    @PostMapping
    public ResponseEntity<Job> postNewJob(@RequestBody JobPostingRequest jobPostingRequest){
        return ResponseEntity.ok(jobsService.postJob(jobPostingRequest));
    }
    @DeleteMapping("/delete/{id}")
    public void deleteJob(@PathVariable Long id){
        jobsService.jobToDelete(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody JobUpdatingRequest jobUpdatingRequest){
        return ResponseEntity.of(jobsService.updateJob(id, jobUpdatingRequest));
    }

}
