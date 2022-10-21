package com.careerclub.careerclub.DTOs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class IndustryCreationRequest {

    @NotBlank(message = "Name is required.")
    @Size(min=2,max=100)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
