package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.Entities.Roles;
import com.careerclub.careerclub.Service.RolesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.of(role);
    }

}
