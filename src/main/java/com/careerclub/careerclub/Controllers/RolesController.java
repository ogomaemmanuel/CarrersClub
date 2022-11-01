package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.AddRoleToUserRequest;
import com.careerclub.careerclub.DTOs.RolesCreationRequest;
import com.careerclub.careerclub.DTOs.RolesUpdateRequest;
import com.careerclub.careerclub.Entities.Roles;
import com.careerclub.careerclub.Service.RolesService;
import com.careerclub.careerclub.Utils.ErrorConverter;
import com.careerclub.careerclub.Utils.RoleValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Tag(name = "Roles Controller")
@RestController
@RequestMapping("roles")
public class RolesController {
    private final RolesService rolesService;
    private final RoleValidator roleValidator;

    public RolesController(RolesService rolesService, RoleValidator roleValidator) {
        this.rolesService = rolesService;
        this.roleValidator = roleValidator;
    }

    @GetMapping
    public ResponseEntity<List<Roles>> getAllRoles(){
        var roles = rolesService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roles> getRoleById(@PathVariable Long id){
        var role = rolesService.getRoleById(id);
        return ResponseEntity.of(role);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRole(@Valid @RequestBody RolesCreationRequest rolesCreationRequest, BindingResult errors){
        roleValidator.validate(rolesCreationRequest,errors);
        if(!errors.hasErrors()){
            var role = rolesService.createRole(rolesCreationRequest);
            return ResponseEntity.status(201).body(role);
        }
        return ResponseEntity.badRequest().body(ErrorConverter.convert(errors));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Roles> updateRole(@PathVariable Long id, @Valid @RequestBody RolesUpdateRequest rolesUpdateRequest){
        var role = rolesService.updateRole(id,rolesUpdateRequest);
        return ResponseEntity.of(role);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<Object,Object>> deleteRole(@PathVariable Long id){
        var validate = rolesService.deleteRole(id);
        return ResponseEntity.ok(validate);
    }

    @PostMapping("/add-role")
    public ResponseEntity<HashMap<Object,Object>> addRoleToUser(@Valid @RequestBody AddRoleToUserRequest addRoleToUserRequest){
        var validate = rolesService.addRoleToUser(addRoleToUserRequest);
        return ResponseEntity.ok(validate);
    }

}
