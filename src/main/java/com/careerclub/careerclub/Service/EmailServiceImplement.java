package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Repositories.EmailService;
import com.careerclub.careerclub.Utils.EmailDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImplement implements EmailService {

    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender javaMailSender;

    public EmailServiceImplement(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public String sendSimpleMail(EmailDetails details) {
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
