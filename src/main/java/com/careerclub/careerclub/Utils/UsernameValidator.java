package com.careerclub.careerclub.Utils;

import com.careerclub.careerclub.DTOs.UserCreationRequest;
import com.careerclub.careerclub.Repositories.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UsernameValidator implements Validator {
    private final UserRepository userRepository;

    public UsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UserCreationRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var request = (UserCreationRequest) target;
        var user = this.userRepository.findByUsername(request.getUsername());
        if (!(user == null)) {
            errors.rejectValue("username", "field.exists", "Username already exists");
        }
    }
}
