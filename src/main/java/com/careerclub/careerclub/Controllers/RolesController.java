package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.AddRoleToUserRequest;
import com.careerclub.careerclub.DTOs.RolesCreationRequest;
import com.careerclub.careerclub.DTOs.RolesUpdateRequest;
import com.careerclub.careerclub.Entities.Roles;
import com.careerclub.careerclub.Service.RolesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("roles")
public class RolesController {
    private final RolesService rolesService;

    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    @GetMapping
    public ResponseEntity<List<Roles>> getAllRoles(){
        var roles = rolesService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Roles> getSingleRoleByName(@PathVariable String name){
        var role = rolesService.getSingleRoleByName(name);
        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity<Roles> createRole(@Valid @RequestBody RolesCreationRequest rolesCreationRequest){
        var role = rolesService.createRole(rolesCreationRequest);
        return ResponseEntity.ok(role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Roles> updateRole(@PathVariable Long id, @Valid @RequestBody RolesUpdateRequest rolesUpdateRequest){
        var role = rolesService.updateRole(id,rolesUpdateRequest);
        return ResponseEntity.of(role);
    }

    @DeleteMapping("/{id}")
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
