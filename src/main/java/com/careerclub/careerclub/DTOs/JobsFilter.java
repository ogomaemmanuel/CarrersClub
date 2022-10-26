package com.careerclub.careerclub.DTOs;

import com.careerclub.careerclub.Entities.Job;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class JobsFilter {
    private String title;
    private String description;
    private String qualification;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Example<Job> jobExample(){
        Job job = new Job();
        job.setTitle(this.getTitle());
        job.setDescription(this.getDescription());
        job.setQualification(this.getQualification());
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()
                        )
                .withMatcher("description",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()
                        )
                .withMatcher("qualification",ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Job> jobExample = Example.of(job,matcher);
        return jobExample;
    }

}
