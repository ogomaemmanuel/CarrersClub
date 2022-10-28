package com.careerclub.careerclub.Service;


import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.MailListSubscribeRequest;
import com.careerclub.careerclub.DTOs.MailListUnsubscribeRequest;
import com.careerclub.careerclub.Entities.MailList;
import com.careerclub.careerclub.Repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailListService {
    private final MailListRepository mailListRepository;
    private final IndustryRepository industryRepository;
    private final LocationRepository locationRepository;
    private final JobTypeRepository jobTypeRepository;

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public MailListService(MailListRepository mailListRepository, IndustryRepository industryRepository, LocationRepository locationRepository, JobTypeRepository jobTypeRepository, UserRepository userRepository, CompanyRepository companyRepository) {
        this.mailListRepository = mailListRepository;
        this.industryRepository = industryRepository;
        this.locationRepository = locationRepository;
        this.jobTypeRepository = jobTypeRepository;
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
    }

    public List<MailList> getAllSubscription(){
        return mailListRepository.findAll();
    }

    public MailList subscribeToMailList(MailListSubscribeRequest mailListSubscribeRequest){
        //User Making request
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var user = userRepository.findByUsername(userDetails.getUsername());

        //Fetching
        var jobType = jobTypeRepository.getByname(mailListSubscribeRequest.getJobTypeName());
        var industry = industryRepository.findByName(mailListSubscribeRequest.getIndustryName());
        var location = locationRepository.findByName(mailListSubscribeRequest.getLocation());

        var mailList = new MailList();
        mailList.setAlertName(mailListSubscribeRequest.getAlertName());
        user.ifPresentOrElse(mailList::setUser,()->{
            throw new RecordNotFoundException("The given user id doesn't exist");
        });
        industry.ifPresent(mailList::setIndustry);
        location.ifPresent(mailList::setLocation);
        if(jobType!=null){
            mailList.setJobType(jobType);
        }
        return mailListRepository.save(mailList);

    }

    public Map<Object,Object> unsubscribeFromTheMailingList(MailListUnsubscribeRequest mailListUnsubscribeRequest){
        var validate = new HashMap<>();
        var mail = mailListRepository.findByIdAndUserId(mailListUnsubscribeRequest.getId(), mailListUnsubscribeRequest.getUserId());
        mail.ifPresentOrElse(m->{
            mailListRepository.delete(m);
            validate.put("message","Unsubscribed successfully.");
        },()->{
            throw new RecordNotFoundException("There is no valid subscription for that user");
        });
        return validate;
    }


}
