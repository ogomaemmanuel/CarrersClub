package com.careerclub.careerclub.JobType;

import com.careerclub.careerclub.Controllers.jobTypeController;
import com.careerclub.careerclub.DTOs.JobTypeRequest;
import com.careerclub.careerclub.Service.CustomUserDetailsService;
import com.careerclub.careerclub.Service.JobTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {jobTypeController.class})
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class JobTypeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    JobTypeService jobTypeService;

    @Test
    @DisplayName("testing job type creation")
    public void test_create_jobsType() throws Exception{
        var jobType = new JobTypeRequest();
        jobType.setName("HOME");
        String jtp = objectMapper.writeValueAsString(jobType);
        mockMvc.perform(post("/jobtype")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jtp)).andExpect(status().isOk());
    }

}
