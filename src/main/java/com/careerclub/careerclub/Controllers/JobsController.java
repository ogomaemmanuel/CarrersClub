package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.JobPostingRequest;
import com.careerclub.careerclub.DTOs.JobUpdatingRequest;
import com.careerclub.careerclub.DTOs.JobsFilter;
import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.ResourceAssembler.JobResourceAssembler;
import com.careerclub.careerclub.Service.JobsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Tag(name = "Jobs controller")
@RestController
@RequestMapping(value = "/jobs")
public class JobsController {
    private final JobsService jobsService;
    private final JobResourceAssembler jobResourceAssembler;

    private final PagedResourcesAssembler<Job> jobPagedResourcesAssembler;

    public JobsController(JobsService jobsService, JobResourceAssembler jobResourceAssembler, PagedResourcesAssembler<Job> jobPagedResourcesAssembler) {
        this.jobsService = jobsService;
        this.jobResourceAssembler = jobResourceAssembler;
        this.jobPagedResourcesAssembler = jobPagedResourcesAssembler;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Job>>> getAllJobs(JobsFilter jobsFilter, Pageable pageable){
        var jobs= jobPagedResourcesAssembler.toModel(jobsService.getAllJobs(jobsFilter,pageable),jobResourceAssembler);

        return  ResponseEntity.ok(jobs);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id){
        return ResponseEntity.of(jobsService.getJobById(id));
    }

    @PostMapping
    public ResponseEntity<?> postNewJob(@RequestBody JobPostingRequest jobPostingRequest) throws ParseException {
        var job = jobsService.postJob(jobPostingRequest);

        //Add a get link
        var jobWithLink = jobResourceAssembler.toModel(job);

        return ResponseEntity.status(201).body(jobWithLink);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteJob(@PathVariable Long id){
        jobsService.jobToDelete(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody JobUpdatingRequest jobUpdatingRequest) throws ParseException{
        return ResponseEntity.of(jobsService.updateJob(id, jobUpdatingRequest));
    }

}
