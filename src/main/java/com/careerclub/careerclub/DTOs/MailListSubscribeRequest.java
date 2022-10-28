package com.careerclub.careerclub.DTOs;

import javax.validation.constraints.NotBlank;

public class MailListSubscribeRequest {
    private String alertName;

    @NotBlank(message = "Job Type is required")
    private String jobTypeName;
    @NotBlank(message = "Industry is required")
    private String industryName;

    @NotBlank(message = "Location is required.")
    private String location;
    private Long userId;

    public String getAlertName() {
        return alertName;
    }

    public void setAlertName(String alertName) {
        this.alertName = alertName;
    }

    public String getJobTypeName() {
        return jobTypeName;
    }

    public void setJobTypeName(String jobTypeName) {
        this.jobTypeName = jobTypeName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
