package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.ApplicationRequest;
import com.careerclub.careerclub.Entities.Application;
import com.careerclub.careerclub.Service.ApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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

////    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
////    public ResponseEntity<Application> makeApplication(ApplicationRequest applicationRequest){
////        return ResponseEntity.ok(applicationService.makeAnApplication(applicationRequest));
////    }
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Application> saveApplication(@RequestParam("file") MultipartFile file){
//        return new ResponseEntity<>(applicationService.(file), HttpStatus.OK)
//    }
}
