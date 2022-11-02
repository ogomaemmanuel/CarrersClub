package com.careerclub.careerclub.Company;


import com.careerclub.careerclub.DTOs.CompanyCreationRequest;
import com.careerclub.careerclub.Entities.Company;
import com.careerclub.careerclub.Repositories.CompanyRepository;
import com.careerclub.careerclub.Service.CompanyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @InjectMocks
    CompanyService companyService;

    @Mock
    CompanyRepository companyRepository;

    @Test
    @DisplayName("Testing company retrieval by id")
    public void test_company_by_id(){
        when(companyRepository.findById(any(Long.class))).thenReturn(Optional.of(new Company()));
        companyService.getCompanyById(1L);
        verify(companyRepository).findById(any(Long.class));
    }

    @Test
    @DisplayName("Testing company creation")
    public void test_company_create(){
        when(companyRepository.save(any(Company.class))).thenReturn(new Company());
        var company = new CompanyCreationRequest();
        company.setName("Testing Plc");
        company.setDescription("Testing limited");
        company.setLink("www.google.com");
        companyService.createCompany(company);
        verify(companyRepository).save(any(Company.class));
    }

    @Test
    @DisplayName("Testing company deletion")
    public void test_company_deletion(){
        doNothing().when(companyRepository).delete(any(Company.class));
        companyService.companyToDelete(1L);
        verify(companyRepository).delete(any(Company.class));
    }

    @Test
    @DisplayName("Testing company update")
    public void test_company_update(){
        when(companyRepository.save(any(Company.class))).thenReturn(new Company());
        var company = new CompanyCreationRequest();
        company.setName("Changes PLC");
        company.setDescription("testing changes");
        companyService.updateCompany(1L,company);
        verify(companyRepository).save(any(Company.class));
    }

}
