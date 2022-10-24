package com.careerclub.careerclub.DTOs;

import com.careerclub.careerclub.Entities.JobType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

public class JobPostingRequest {

    private String description;
    private String title;
    private String qualification;
    private String deadline;
    private JobType jobTypeId;
    private Long companyId;

    public JobType getJobTypeId() {
        return jobTypeId;
    }

    public void setJobTypeId(JobType jobTypeId) {
        this.jobTypeId = jobTypeId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
