package com.careerclub.careerclub.DTOs;

import javax.validation.constraints.NotBlank;

public class LocationCreateRequest {
    @NotBlank(message = "Name is required")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
