package com.careerclub.careerclub.Repositories;

import com.careerclub.careerclub.Utils.EmailDetails;
import org.springframework.stereotype.Service;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);


}
