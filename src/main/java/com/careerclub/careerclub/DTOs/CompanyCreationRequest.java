package com.careerclub.careerclub.DTOs;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

public class CompanyCreationRequest {

    private String name;
    private String description;
    @URL
    private String link;

     public String getName(){
        return name;
     }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
