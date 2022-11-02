package com.careerclub.careerclub.Jobs;


import com.careerclub.careerclub.Controllers.JobsController;
import com.careerclub.careerclub.Service.CustomUserDetailsService;
import com.careerclub.careerclub.Service.JobsService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {JobsController.class})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class JobsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    JobsService jobsService;

    @MockBean
    JobsController jobsController;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("testing job retrieval by id")
    public void test_job_id() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/jobs/{id}",1)).andExpect(status().isOk());
    }
}
