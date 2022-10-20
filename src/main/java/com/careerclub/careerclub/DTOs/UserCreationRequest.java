package com.careerclub.careerclub.DTOs;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserCreationRequest {
    @NotBlank(message = "Username is required.")
    @Size(min = 2, message = "Username is too short.")
    @Size(max = 50, message = "Username is too big.")
    private String username;
    private String password;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
