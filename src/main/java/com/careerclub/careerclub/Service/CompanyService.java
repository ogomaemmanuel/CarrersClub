package com.careerclub.careerclub.Service;
import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.Entities.Company;
import com.careerclub.careerclub.DTOs.CompanyCreationRequest;
import com.careerclub.careerclub.Repositories.CompanyRepository;
import com.careerclub.careerclub.Repositories.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final JobRepository jobRepository;

    public CompanyService(CompanyRepository companyRepository, JobRepository jobRepository){
        this.companyRepository = companyRepository;
        this.jobRepository = jobRepository;
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

    public String companyToDelete(Long id){
        var companyToDelete = companyRepository.findById(id);
        companyToDelete.ifPresentOrElse(company -> {
            var jobs = jobRepository.findAllByCompanyId(company.getId());
            jobRepository.deleteAll(jobs);
            companyRepository.delete(company);
        }, ()->{
            throw new RecordNotFoundException("Company doesn't exist");
        });
        return "Company deleted successfully";
    }

}
