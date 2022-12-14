package com.careerclub.careerclub.DTOs;

import javax.validation.constraints.NotBlank;

public class UserUpdateRequest {

    private String phoneNumber;
    private String profession;
    private String bio;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

}
