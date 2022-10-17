package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.JobPostingRequest;
import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.Service.JobsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/jobs")
public class JobsController {
    private final JobsService jobsService;

    public JobsController(JobsService jobsService) {
        this.jobsService = jobsService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs(){
        return  ResponseEntity.ok(jobsService.getAllJobs());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobBYId(@PathVariable Long id){
        return ResponseEntity.of(jobsService.getJobById(id));
    }

    @PostMapping
    public ResponseEntity<Job> postNewJob(@RequestBody JobPostingRequest jobPostingRequest){
        return ResponseEntity.ok(jobsService.postJob(jobPostingRequest));
    }
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id){
        jobsService.jobToDelete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody Job job){
        return ResponseEntity.of(jobsService.updateJob(id, job));
    }

}
