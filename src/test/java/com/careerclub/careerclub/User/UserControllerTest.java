package com.careerclub.careerclub.User;


import com.careerclub.careerclub.Controllers.UserController;
import com.careerclub.careerclub.DTOs.UserCreationRequest;
import com.careerclub.careerclub.DTOs.UserUpdateRequest;
import com.careerclub.careerclub.Entities.User;
import com.careerclub.careerclub.Service.CustomUserDetailsService;
import com.careerclub.careerclub.Service.UserService;
import com.careerclub.careerclub.Utils.EmailValidator;
import com.careerclub.careerclub.Utils.UsernameValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {UserController.class})
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomUserDetailsService customUserDetailsService;
    @MockBean
    UserService userService;

    @MockBean
    UsernameValidator usernameValidator;

    @MockBean
    EmailValidator emailValidator;


    @Test
    @DisplayName("Testing get single user by id")
    public void test_single_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}",1)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Testing get all user endpoint")
    public void test_all_user() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/users/")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testing User creation")
    public void test_user_creation() throws Exception{
        var user = new UserCreationRequest();
        user.setUsername("testing");
        user.setPassword("testing123");
        user.setEmail("testing@testing.com");
        String usr = objectMapper.writeValueAsString(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                .content(usr)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testing user update endpoint")
    public void test_user_update() throws Exception{
        var user = new UserUpdateRequest();
        user.setProfession("Softwate Developer");
        String usr = objectMapper.writeValueAsString(user);
        mockMvc.perform(MockMvcRequestBuilders.put("/users/update/{id}",1)
                        .content(usr)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("testing user deletion endpoint")
    public void test_user_delete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/delete/{id}",1)).andExpect(status().isOk());
    }

}
