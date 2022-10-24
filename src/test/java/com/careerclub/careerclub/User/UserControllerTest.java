package com.careerclub.careerclub.User;


import com.careerclub.careerclub.Controllers.UserController;
import com.careerclub.careerclub.Entities.User;
import com.careerclub.careerclub.Service.CustomUserDetailsService;
import com.careerclub.careerclub.Service.UserService;
import com.careerclub.careerclub.Utils.EmailValidator;
import com.careerclub.careerclub.Utils.UsernameValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        when(userService.getSingleUserById(1L)).thenReturn(Optional.of(new User()));
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testing User creation")
    public void test_user_creation(){

    }

}
