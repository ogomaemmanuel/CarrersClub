package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.DuplicateException;
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


    public Optional<Roles> getRoleById(Long id){
        var role = rolesRepository.findById(id);
        if(role.isEmpty()){
            throw new RecordNotFoundException("Role with the given id doesn't exist.");
        }
        return role;
    }

    public Roles createRole(RolesCreationRequest rolesCreationRequest){
        var fetchRole = rolesRepository.findByName(rolesCreationRequest.getName());
        if(fetchRole==null) {
            var role = new Roles();
            role.setName(rolesCreationRequest.getName());
            rolesRepository.save(role);
            return role;
        }
        throw new DuplicateException("Role with the given name already exists.");
    }

    public Optional<Roles> updateRole(Long id, RolesUpdateRequest rolesUpdateRequest){
        var role = rolesRepository.findById(id);

        role.ifPresentOrElse(r->{
            r.setName(rolesUpdateRequest.getName());
            rolesRepository.save(r);
        },()->{
            throw new RecordNotFoundException("Role with the given id doesn't exist");
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
            throw new RecordNotFoundException("Role with the given id doesn't exist 🚫");
        });

        return validate;

    }

    public HashMap<Object,Object> addRoleToUser(AddRoleToUserRequest addRoleToUserRequest){
        var validate = new HashMap<>();
        var user = userRepository.findById(addRoleToUserRequest.getUserId());
        var role = rolesRepository.findById(addRoleToUserRequest.getRoleId());
        user.ifPresentOrElse(u->{
            role.ifPresentOrElse(r->{
                u.addRole(r);
                userRepository.save(u);
                validate.put("message","Role "+r.getName()+" added successfully to user with the username "+ u.getUsername()+" ✅");
            },()->{
                throw new RecordNotFoundException("Role with the given id doesn't exist");
            });

        },()->{
            throw new RecordNotFoundException("User with the given id doesn't exist");
        });
        return validate;
    }

}
