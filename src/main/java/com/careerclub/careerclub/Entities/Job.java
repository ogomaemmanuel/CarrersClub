package com.careerclub.careerclub.Entities;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="JobPosting")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String title;
    private String qualification;
    @CreationTimestamp
    private LocalDateTime datePosted;
    private String deadline;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "companyId")

    private Company company;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "jobTypeId")
    private JobType jobType;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "industryId")
    private Industry industry;

    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location location;

    public Industry getIndustry() {
        return industry;
    }

    @Transient
    public List<Object> events = new ArrayList<>();

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public JobType getJobType() {
        return jobType;
    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }





}
