package com.careerclub.careerclub.Controllers;
import com.careerclub.careerclub.DTOs.CompanyCreationRequest;
import com.careerclub.careerclub.Entities.Company;
import com.careerclub.careerclub.Service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import  java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
        private final CompanyService companyService;
        public CompanyController(CompanyService companyService){
            this.companyService = companyService;
        }

        @GetMapping
        public ResponseEntity <List<Company>> getAllCompanies(){
            var companies = companyService.getAllCompanies();
            return  ResponseEntity.ok(companies);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
            var company = companyService.getCompanyById(id);
            return  ResponseEntity.of(company);
        }

        @PostMapping
        public ResponseEntity<Company> createCompany(@RequestBody CompanyCreationRequest newCompany){
            var company = companyService.createCompany(newCompany);
            return  ResponseEntity.ok(company);
        }

//        @PutMapping
//        public ResponseEntity<Company> updateCompany(){
//        }
}
