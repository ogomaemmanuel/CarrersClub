package com.careerclub.careerclub.DTOs;

import com.careerclub.careerclub.Entities.JobType;

public class JobUpdatingRequest {
    private String description;
    private String title;
    private String qualification;
    private String deadline;


    private Long jobTypeId;

    public Long getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(Long jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

}
