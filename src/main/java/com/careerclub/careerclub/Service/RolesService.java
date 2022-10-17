package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.RolesCreationRequest;
import com.careerclub.careerclub.DTOs.RolesUpdateRequest;
import com.careerclub.careerclub.Entities.Roles;
import com.careerclub.careerclub.Repositories.RolesRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    public Roles getSingleRoleByName(String name){
        var role = rolesRepository.findByName(name);
        if (role!=null) {
            return role;
        }
        throw new RecordNotFoundException("Role with name, "+name+ ",doesn't exist 🚫");
    }

    public Roles createRole(RolesCreationRequest rolesCreationRequest){
        var fetchRole = rolesRepository.findByName(rolesCreationRequest.getName());
        if(fetchRole==null) {
            var role = new Roles();
            role.setName(rolesCreationRequest.getName());
            rolesRepository.save(role);
            return role;
        }
        throw new RecordNotFoundException("Role with name, "+rolesCreationRequest.getName()+", already exists.");
    }

    public Optional<Roles> updateRole(Long id, RolesUpdateRequest rolesUpdateRequest){
        var role = rolesRepository.findById(id);

        role.ifPresentOrElse(r->{
            r.setName(rolesUpdateRequest.getName());
            rolesRepository.save(r);
        },()->{
            throw new RecordNotFoundException("Role with id "+id+" doesn't exist 🚫");
        });

        return role;
    }

    public HashMap<Object, Object> deleteRole(Long id){
        var validate = new HashMap<>();
        var role = rolesRepository.findById(id);
        role.ifPresentOrElse(r->{
            rolesRepository.delete(r);
            validate.put("message","Role Deleted Successfully ✅");
        },()->{
            throw new RecordNotFoundException("Role with id "+id+" doesn't exist 🚫");
        });

        return validate;

    }

}
