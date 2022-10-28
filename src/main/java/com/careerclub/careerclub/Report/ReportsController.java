package com.careerclub.careerclub.Report;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/reports")
public class ReportsController {
    private final ReportsService reportsService;

    public ReportsController(ReportsService reportsService) {
        this.reportsService = reportsService;
    }
    @GetMapping
    public ResponseEntity<List<JobsByLocationReports>> getJobsByLocation(){

      List<JobsByLocationReports> jobsByLocationReports=  this.reportsService.getJobsByLocation();
      return  ResponseEntity.ok(jobsByLocationReports);
    }
}
