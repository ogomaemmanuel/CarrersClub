package com.careerclub.careerclub.Utils;

import com.careerclub.careerclub.DTOs.IndustryCreationRequest;
import com.careerclub.careerclub.DTOs.LocationCreateRequest;
import com.careerclub.careerclub.Repositories.LocationRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LocationValidator implements Validator {

    private final LocationRepository locationRepository;

    public LocationValidator(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(LocationCreateRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        var request = (LocationCreateRequest) target;
        var industry = this.locationRepository.findByName(request.getName());
        if (industry.isPresent()) {
            errors.rejectValue("name", "field.exists", "Name already exists");
        }
    }
}
