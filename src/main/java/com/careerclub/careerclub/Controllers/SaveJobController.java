package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.SaveJobRequest;
import com.careerclub.careerclub.Service.SaveJobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("save-job")
public class SaveJobController {
    private final SaveJobService saveJobService;

    public SaveJobController(SaveJobService saveJobService) {
        this.saveJobService = saveJobService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getAllSavedJobByUser(@PathVariable Long userId){
        var user_jobs = saveJobService.getAllUserSavedJobs(userId);
        return ResponseEntity.ok(user_jobs);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveJob(@RequestBody SaveJobRequest saveJobRequest){
        var message = new HashMap<>();
        var jobs_saved = saveJobService.saveJob(saveJobRequest);
        message.put("message",jobs_saved);
        return ResponseEntity.ok(message);
    }

}
