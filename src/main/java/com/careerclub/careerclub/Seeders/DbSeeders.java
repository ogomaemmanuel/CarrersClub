package com.careerclub.careerclub.Seeders;

import com.careerclub.careerclub.Entities.Company;
import com.careerclub.careerclub.Entities.Job;
import com.careerclub.careerclub.Entities.Roles;
import com.careerclub.careerclub.Entities.User;
import com.careerclub.careerclub.Repositories.CompanyRepository;
import com.careerclub.careerclub.Repositories.JobRepository;
import com.careerclub.careerclub.Repositories.RolesRepository;
import com.careerclub.careerclub.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Component
@Transactional
public class DbSeeders implements CommandLineRunner {


    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public DbSeeders(UserRepository userRepository, RolesRepository rolesRepository, JobRepository jobRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        var userCount = userRepository.count();
        var roleCount = rolesRepository.count();
        var jobCount = jobRepository.count();
        var companyCount = companyRepository.count();
        if(roleCount==0){
            var rolesToAdd = new String[]{"admin", "member","hr"};
            for(String s:rolesToAdd){
                var role = new Roles();
                role.setName(s);
                rolesRepository.save(role);
            }
        }
        if(userCount==0){
            var roles = new ArrayList<Roles>();
            var adminRole = rolesRepository.findByName("admin");
            roles.add(adminRole);
            var user = new User();
            user.setUsername("Testing");
            user.setPassword("Testing");
            user.setRoles(roles);
            userRepository.save(user);
        }
        if(companyCount==0){
            var company = new Company();
            company.setName("TestingPLC");
            company.setDescription("A testing company");
            company.setLink("https://crabs-eugene.netlify.app/");
            companyRepository.save(company);
        }
        if(jobCount==0){
            var testCompany = companyRepository.findById(1L).get();
            var job = new Job();
            job.setTitle("DevOps Engineer");
            job.setDescription("Development and Deploying");
            job.setJobType("Full-Time");
            job.setDeadline("08/12/2022");
            job.setQualification("Docker & Kubernettes");
            job.setCompany(testCompany);
            jobRepository.save(job);
        }
    }
}
