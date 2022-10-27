package com.careerclub.careerclub.Events;

import com.careerclub.careerclub.Entities.Job;

public class JobCreatedEvent {

    private Job job;

    public JobCreatedEvent(Job job) {
        this.job = job;
    }

    public Job getJob() {
        return job;
    }
}
