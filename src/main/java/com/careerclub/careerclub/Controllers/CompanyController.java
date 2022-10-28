package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.CompanyCreationRequest;
import com.careerclub.careerclub.Entities.Company;
import com.careerclub.careerclub.Service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Company controller")
@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCompanies(Pageable pageable) {
        var companies = companyService.getAllCompanies(pageable);
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/jobs/{companyId}")
    public ResponseEntity<?> getAllJobOfACompany(@PathVariable Long companyId){
        var jobs = companyService.getAllJobsOfACompany(companyId);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        var company = companyService.getCompanyById(id);
        return ResponseEntity.of(company);
    }

    @PostMapping("/create")
    public ResponseEntity<Company> createCompany(@RequestBody CompanyCreationRequest newCompany) {
        var company = companyService.createCompany(newCompany);
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteJob(@PathVariable Long id) {
        companyService.companyToDelete(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyCreationRequest updateCompany) {
        var company = companyService.updateCompany(id, updateCompany);
        return ResponseEntity.of(company);
    }
}
