package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.AddRoleToUserRequest;
import com.careerclub.careerclub.DTOs.RolesCreationRequest;
import com.careerclub.careerclub.DTOs.RolesUpdateRequest;
import com.careerclub.careerclub.Entities.Roles;
import com.careerclub.careerclub.Repositories.RolesRepository;
import com.careerclub.careerclub.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RolesService {
    private final RolesRepository rolesRepository;
    private final UserRepository userRepository;

    public RolesService(RolesRepository rolesRepository, UserRepository userRepository) {
        this.rolesRepository = rolesRepository;
        this.userRepository = userRepository;
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

    public HashMap<Object,Object> addRoleToUser(AddRoleToUserRequest addRoleToUserRequest){
        var validate = new HashMap<>();
        var user = userRepository.findById(addRoleToUserRequest.getUserId());
        var role = rolesRepository.findByName(addRoleToUserRequest.getRoleName());
        user.ifPresentOrElse(u->{
            if(role==null){
                throw new RecordNotFoundException("Role with name, "+addRoleToUserRequest.getRoleName()+" doesn't exist 🚫");
            }
            u.addRole(role);
            userRepository.save(u);
            validate.put("message","Role "+role.getName()+" added successfully to user with the username "+ u.getUsername()+" ✅");
        },()->{
            throw new RecordNotFoundException("User with id, "+addRoleToUserRequest.getUserId()+" doesn't exist 🚫");
        });
        return validate;
    }

}
