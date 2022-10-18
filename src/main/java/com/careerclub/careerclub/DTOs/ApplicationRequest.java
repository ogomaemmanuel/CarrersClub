package com.careerclub.careerclub.DTOs;

import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.Entities.User;

import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

public class ApplicationRequest {
    private Long jobId;

    private Long userId;

    @Lob
    private byte[] cv;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public byte[] getCv() {
        return cv;
    }

    public void setCv(byte[] cv) {
        this.cv = cv;
    }
}
