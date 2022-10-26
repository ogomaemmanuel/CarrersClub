package com.careerclub.careerclub.Service;
import com.careerclub.careerclub.Advice.RecordNotFoundException;
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
        if(company.isEmpty()){
            throw new RecordNotFoundException("Company with id "+id+" doesn't exist.");
        }
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

    public Optional<Company> updateCompany(Long id,CompanyCreationRequest updateCompany){
        var company = companyRepository.findById(id);
        company.ifPresentOrElse(c->{
            if(updateCompany.getName()!=null){
                c.setName(updateCompany.getName());
            }else if(updateCompany.getDescription()!=null){
                c.setDescription(updateCompany.getDescription());
            }else if(updateCompany.getLink()!=null){
                c.setLink(updateCompany.getLink());
            }
            companyRepository.save(c);
        },()->{
            throw new RecordNotFoundException("Company with id "+id+" doesn't exist.");
        });
        return company;
    }

    public String companyToDelete(Long id){
        var companyToDelete = companyRepository.findById(id);
        companyToDelete.ifPresentOrElse(job -> {
            companyRepository.delete(job);
        }, ()->{
            throw new RecordNotFoundException("Company doesn't exist");
        });
        return "Company deleted successfully";
    }

}
