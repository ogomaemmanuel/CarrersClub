package com.careerclub.careerclub.Utils;

import com.careerclub.careerclub.DTOs.RolesCreationRequest;
import com.careerclub.careerclub.DTOs.UserCreationRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class RoleValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RolesCreationRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
