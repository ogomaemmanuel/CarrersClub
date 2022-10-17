package com.careerclub.careerclub.DTOs;

import javax.validation.constraints.NotBlank;

public class RolesUpdateRequest {

    @NotBlank(message = "Name is Required")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
