package com.careerclub.careerclub.Seeders;

import com.careerclub.careerclub.Entities.*;
import com.careerclub.careerclub.Repositories.*;
import com.careerclub.careerclub.Utils.Dateformat;
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
    private final IndustryRepository industryRepository;
    private final LocationRepository locationRepository;

    private final JobTypeRepository jobTypeRepository;

    public DbSeeders(UserRepository userRepository, RolesRepository rolesRepository, JobRepository jobRepository, CompanyRepository companyRepository, IndustryRepository industryRepository, LocationRepository locationRepository, JobTypeRepository jobTypeRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.industryRepository = industryRepository;
        this.locationRepository = locationRepository;
        this.jobTypeRepository = jobTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        var userCount = userRepository.count();
        var roleCount = rolesRepository.count();
        var jobCount = jobRepository.count();
        var jobTypeCount = jobRepository.count();
        var companyCount = companyRepository.count();
        var industryCount = industryRepository.count();
        var locationCount = locationRepository.count();


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
            var adminRole = rolesRepository.findByName("admin").get();
            roles.add(adminRole);
            var user = new User();
            user.setUsername("Testing");
            user.setPassword("Testing");
            user.setRoles(roles);
            userRepository.save(user);
        }
        if(industryCount==0) {
            var industries = new String[]{"advertisement", "education", "government", "it", "law", "real estate", "tourism", "retail"};
            for (String industry : industries) {
                var ids = new Industry();
                ids.setName(industry);
                industryRepository.save(ids);
            }
        }
        if (locationCount == 0) {
            var locations = new String[]{"nairobi", "kisumu", "mombasa", "nakuru", "rest of kenya", "outside kenya"};
            for (String lc : locations) {
                var location = new Location();
                location.setName(lc);
                locationRepository.save(location);
            }

        }
        if(companyCount==0){
            var company = new Company();
            company.setName("TestingPLC");
            company.setDescription("A testing company");
            company.setLink("https://crabs-eugene.netlify.app/");
            companyRepository.save(company);
        }
        if(jobTypeCount == 0){
            var types = new String[]{"fulltime", "remote", "part-time"};
            for(String j:types){
                var jobType = new JobType();
                jobType.setName(j);
                jobTypeRepository.save(jobType);
            }

        }
        if(jobCount==0){
            var testCompany = companyRepository.findById(1L).get();
            var testJobType = jobTypeRepository.findById(1L).get();
            var testIndustry = industryRepository.findById(1L).get();
            var testLocation = locationRepository.findById(1L).get();
            var date = Dateformat.formatDate("08/12/2022");
            var job = new Job();
            job.setTitle("DevOps Engineer");
            job.setDescription("Development and Deploying");
            job.setJobType(testJobType);
            job.setDeadline(date);
            job.setQualification("Docker & Kubernettes");
            job.setCompany(testCompany);
            job.setIndustry(testIndustry);
            job.setLocation(testLocation);
            jobRepository.save(job);
        }


            

    }
}
