package com.careerclub.careerclub.Company;


import com.careerclub.careerclub.Controllers.CompanyController;
import com.careerclub.careerclub.DTOs.CompanyCreationRequest;
import com.careerclub.careerclub.Service.CompanyService;
import com.careerclub.careerclub.Service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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


    @DisplayName("testing get all endpoint for companies")
    @WithMockUser(roles={
            "admin"
    })
    @ParameterizedTest
    @CsvSource(textBlock = """
            admin, 200
            user, 200
            """)
    public void test_all_companies(String role,Integer status) throws Exception{
        when(companyService.getAllCompanies(any(Pageable.class))).thenReturn(Page.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/companies")
                .with(user("user").roles(role))).andExpect(status().is(status));
        verify(companyService).getAllCompanies(any(Pageable.class));
    }

    @Test
    @DisplayName("testing company retrieval by id")
    public void test_company_by_id() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}",1))
                .andExpect(status().isNotFound());
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
    @WithMockUser(roles = {
            "admin"
    })
    public void test_company_delete() throws Exception{
        when(companyService.companyToDelete(any(Long.class))).thenReturn(any(String.class));
        mockMvc.perform(delete("/companies/delete/{id}",1))
                .andExpect(status().isOk());
        verify(companyService).companyToDelete(any(Long.class));
    }

}
