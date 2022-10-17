package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Repositories.RolesRepository;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    private final RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }
}
