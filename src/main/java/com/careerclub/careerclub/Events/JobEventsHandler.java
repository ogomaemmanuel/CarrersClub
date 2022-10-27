package com.careerclub.careerclub.Events;

import com.careerclub.careerclub.Entities.MailList;
import com.careerclub.careerclub.Repositories.MailListRepository;
import com.careerclub.careerclub.Service.EmailServiceImplement;
import com.careerclub.careerclub.Utils.EmailDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component

public class JobEventsHandler {
    private final MailListRepository mailListRepository;
    private final EmailServiceImplement emailServiceImplement;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public JobEventsHandler(MailListRepository mailListRepository, EmailServiceImplement emailServiceImplement) {
        this.mailListRepository = mailListRepository;
        this.emailServiceImplement = emailServiceImplement;
    }

    public void configureAnEmail(EmailDetails emailDetails,MailList mailList){
        emailDetails.setSubject("Job alerts");
        emailDetails.setRecipient(mailList.getUser().getEmail());
        if(mailList.getJobType()!=null && mailList.getLocation()!=null){
            emailDetails.setMsgBody("Job Type "+mailList.getJobType().getName()+", Job location "+mailList.getLocation().getName()+" and Job industry "+mailList.getIndustry().getName());
        }else if(mailList.getJobType()!=null){
            emailDetails.setMsgBody("Job Type "+mailList.getJobType().getName()+" and Job industry "+mailList.getIndustry().getName());
        }else if(mailList.getLocation()!=null){
            emailDetails.setMsgBody("Job location "+mailList.getLocation().getName()+" and Job industry "+mailList.getIndustry().getName());
        }else{
            emailDetails.setMsgBody("Job industry "+mailList.getIndustry().getName());
        }
        emailServiceImplement.sendSimpleMail(emailDetails);
    }

    @EventListener(value = {JobCreatedEvent.class})
    @Async
    public void handleJobCreated(JobCreatedEvent event){
        //Fetching data from event
        var jobType = event.getJob().getJobType();
        var industry = event.getJob().getIndustry();
        var location = event.getJob().getLocation();

        //Fetching All MailList By Industry
        var mailListByIndustries = mailListRepository.findAllByIndustry(industry);



        for(MailList mailList:mailListByIndustries){
            var emailDetails = new EmailDetails();
            if(mailList.getJobType()==jobType&&mailList.getLocation()==location){
                configureAnEmail(emailDetails,mailList);
            }else if(mailList.getJobType()==jobType&&mailList.getLocation()==null){
                configureAnEmail(emailDetails,mailList);
            }else if(mailList.getLocation()==location&&mailList.getJobType()==null){
                configureAnEmail(emailDetails,mailList);
            }else if(mailList.getLocation()==null&&mailList.getJobType()==null){
                configureAnEmail(emailDetails,mailList);
            }
        }


    }
}
