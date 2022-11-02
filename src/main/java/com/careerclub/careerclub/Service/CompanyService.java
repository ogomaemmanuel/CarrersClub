package com.careerclub.careerclub.Service;
import com.careerclub.careerclub.Advice.BadRequestException;
import com.careerclub.careerclub.Advice.DuplicateException;
import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.Entities.Company;
import com.careerclub.careerclub.DTOs.CompanyCreationRequest;
import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.Repositories.CompanyRepository;
import com.careerclub.careerclub.Repositories.JobRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

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

    public List<Job> getAllJobsOfACompany(Long companyId){
        var company = companyRepository.findById(companyId);
        if(company.isEmpty()){
            throw new RecordNotFoundException("Company with the given id doesn't exist");
        }
        return jobRepository.findAllByCompanyId(companyId);
    }

    public Optional<Company> getCompanyById(Long id){
        var company = companyRepository.findById(id);
        if(company.isEmpty()){
            throw new RecordNotFoundException("Company with the given id doesn't exist.");
        }
        return company;
    }

    public Company createCompany(CompanyCreationRequest newCompany){
        var companyName = companyRepository.findByName(newCompany.getName());
        if(companyName.isPresent()){
            throw new DuplicateException("Company with the given name already exists.");
        }
        //Url validation
//        String regex = "((http|https)://)(www.)?"
//                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
//                + "{2,256}\\.[a-z]"
//                + "{2,6}\\b([-a-zA-Z0-9@:%"
//                + "._\\+~#?&//=]*)";
//
//        Pattern p = compile(regex);
//        var link = newCompany.getLink();
//
//        if(!p.matcher(link).matches()){
//            throw new BadRequestException("The given url isn't valid.");
//        }

        var company = new Company();
        company.setName(newCompany.getName());
        company.setDescription(newCompany.getDescription());
        company.setLink(newCompany.getLink());
        companyRepository.save(company);
        return company;
    }

    public Optional<Company> updateCompany(Long id,CompanyCreationRequest updateCompany){
        var company = companyRepository.findById(id);
        var companyName = companyRepository.findByName(updateCompany.getName());
        if(companyName.isPresent()){
            throw new DuplicateException("Company with the given name already exists");
        }
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
            throw new RecordNotFoundException("Company with the given id doesn't exist.");
        });
        return company;
    }

    public String companyToDelete(Long id){
        var companyToDelete = companyRepository.findById(id);
        companyToDelete.ifPresentOrElse(company -> {
            var jobs = jobRepository.findAllByCompanyId(company.getId());
            jobRepository.deleteAll(jobs);
            companyRepository.delete(company);
        }, ()->{
            throw new RecordNotFoundException("Company with the given id doesn't exist");
        });
        return "Company deleted successfully";
    }

}
