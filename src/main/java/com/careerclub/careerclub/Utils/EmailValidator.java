package com.careerclub.careerclub.Utils;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.UserCreationRequest;
import com.careerclub.careerclub.Repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator implements Validator {

    private final  UserRepository userRepository;

    public EmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public static boolean validate(String email){
        String emailValidateRegex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(emailValidateRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UserCreationRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var request = (UserCreationRequest) target;
        var user = this.userRepository.findByEmail(request.getEmail());
        if (!(user == null)) {
            errors.rejectValue("email", "field.exists", "Email already exists");
        }
    }
}
