package com.careerclub.careerclub.User;


import com.careerclub.careerclub.Controllers.UserController;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {UserController.class})
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;



    @MockBean
    UserService userService;

    @MockBean
    UsernameValidator usernameValidator;

    @MockBean
    EmailValidator emailValidator;

    @Test
    @DisplayName("Testing get all user endpoint")
    public void test_all_users() throws Exception {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/users")).andExpect(status().isOk());
    }

}
