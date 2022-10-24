package com.careerclub.careerclub.Service;
import com.careerclub.careerclub.Entities.Company;
import com.careerclub.careerclub.DTOs.CompanyCreationRequest;
import com.careerclub.careerclub.Repositories.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public Page<Company> getAllCompanies(Pageable pageable){
        var companies = companyRepository.findAll(pageable);
        return companies;
    }

    public Optional<Company> getCompanyById(Long id){
        var company = companyRepository.findById(id);
        return company;
    }

    public Company createCompany(CompanyCreationRequest newCompany){
        var company = new Company();
        company.setName(newCompany.getName());
        company.setDescription(newCompany.getDescription());
        company.setLink(newCompany.getLink());
        companyRepository.save(company);
        return company;
    }

}
