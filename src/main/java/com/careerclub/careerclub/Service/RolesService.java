package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Entities.Roles;
import com.careerclub.careerclub.Repositories.RolesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolesService {
    private final RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    public List<Roles> getAllRoles(){
        return rolesRepository.findAll();
    }

    public Optional<Roles> getSingleRoleByName(String name){
        var role = rolesRepository.findByName(name);
        return Optional.ofNullable(role);
    }

}
