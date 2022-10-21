package com.careerclub.careerclub.Utils;

import com.careerclub.careerclub.DTOs.IndustryCreationRequest;
import com.careerclub.careerclub.DTOs.UserCreationRequest;
import com.careerclub.careerclub.Repositories.IndustryRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class IndustryValidator implements Validator {


    private final IndustryRepository industryRepository;

    public IndustryValidator(IndustryRepository industryRepository) {
        this.industryRepository = industryRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(IndustryCreationRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var request = (IndustryCreationRequest) target;
        var industry = this.industryRepository.findByName(request.getName());
        if (industry.isPresent()) {
            errors.rejectValue("name", "field.exists", "Name already exists");
        }
    }
}
