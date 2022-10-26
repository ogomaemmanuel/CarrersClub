package com.careerclub.careerclub.Company;


import com.careerclub.careerclub.Controllers.CompanyController;
import com.careerclub.careerclub.DTOs.CompanyCreationRequest;
import com.careerclub.careerclub.Service.CompanyService;
import com.careerclub.careerclub.Service.CustomUserDetailsService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {CompanyController.class})
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class CompanyControllerTest {


    @MockBean
    CompanyService companyService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("testing get all endpoint for companies")
    public void test_all_companies() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/companies")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("testing company retrieval by id")
    public void test_company_by_id() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}",1)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("testing company update endpoint")
    public void test_company_update() throws Exception{
        var company = new CompanyCreationRequest();
        company.setDescription("limited");
        String cmp = objectMapper.writeValueAsString(company);
        mockMvc.perform(MockMvcRequestBuilders.put("/companies/update/{id}",1)
        .content(cmp)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("testing company deletion")
    public void test_company_delete() throws Exception{
        mockMvc.perform(delete("/companies/delete/{id}",1)).andExpect(status().isOk());
    }

}
