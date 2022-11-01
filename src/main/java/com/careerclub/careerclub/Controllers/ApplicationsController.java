package com.careerclub.careerclub.Controllers;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.careerclub.careerclub.Config.BucketName;
import com.careerclub.careerclub.DTOs.ApplicationRequest;
import com.careerclub.careerclub.DTOs.CvDownloadRequest;
import com.careerclub.careerclub.Entities.Application;
import com.careerclub.careerclub.Service.ApplicationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Tag(name = "Applications controller")
@RestController
@RequestMapping("/applications")
public class ApplicationsController {
    private final ApplicationService applicationService;


    public ApplicationsController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications(){
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id){
        return ResponseEntity.of(applicationService.getApplicationById(id));
    }

    @PostMapping(path = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Application> makeApplication(@RequestParam("file") MultipartFile file, @RequestParam("jobId") Long jobId, @RequestParam("userId") Long userId){
        var application = applicationService.makeAnApplication(file,jobId,userId);
        return ResponseEntity.status(201).body(application);

    }

    @GetMapping("/csv")
    public ResponseEntity<?> downLoadCv(CvDownloadRequest  downloadRequest) {
      var result=  this.applicationService.getCv(downloadRequest);
        InputStreamResource resource = new InputStreamResource(result);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
}
