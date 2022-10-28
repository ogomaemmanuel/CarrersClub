package com.careerclub.careerclub.Events;

import com.careerclub.careerclub.Entities.MailList;
import com.careerclub.careerclub.Repositories.MailListRepository;
import com.careerclub.careerclub.Service.EmailServiceImplement;
import com.careerclub.careerclub.Utils.EmailDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Component

public class JobEventsHandler {
    private final MailListRepository mailListRepository;
    private final EmailServiceImplement emailServiceImplement;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public JobEventsHandler(MailListRepository mailListRepository, EmailServiceImplement emailServiceImplement) {
        this.mailListRepository = mailListRepository;
        this.emailServiceImplement = emailServiceImplement;
    }

    public void configureAnEmail(MailList mailList, String msg) {
        var emailDetails = new EmailDetails();
        emailDetails.setSubject("Job alerts");
        emailDetails.setRecipient(mailList.getUser().getEmail());
        emailDetails.setMsgBody(msg);
        emailServiceImplement.sendSimpleMail(emailDetails);
    }

    @EventListener(value = {JobCreatedEvent.class})
    @Async
    public void handleJobCreated(JobCreatedEvent event) {

        //MailList to help generate custom queries
        var mailList = new MailList();
        mailList.setJobType(event.getJob().getJobType());
        mailList.setLocation(event.getJob().getLocation());
        mailList.setIndustry(event.getJob().getIndustry());


        //Matchers
        ExampleMatcher exactMatcher = ExampleMatcher.matching().withMatcher("industry", exact())
                .withMatcher("location", exact())
                .withMatcher("jobType", exact());


        var mailListSubscribers = mailListRepository.findAll(Example.of(mailList, exactMatcher));


        for (MailList subscriber : mailListSubscribers) {
            String msg = "New job listed from your alert "+subscriber.getAlertName();
            configureAnEmail(subscriber, msg);
        }


    }
}
