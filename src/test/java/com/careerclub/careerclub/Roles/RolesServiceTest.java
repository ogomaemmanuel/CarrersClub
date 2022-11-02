package com.careerclub.careerclub.Roles;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.RolesCreationRequest;
import com.careerclub.careerclub.DTOs.RolesUpdateRequest;
import com.careerclub.careerclub.Entities.Roles;
import com.careerclub.careerclub.Repositories.RolesRepository;
import com.careerclub.careerclub.Service.RolesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RolesServiceTest {

    @InjectMocks
    RolesService rolesService;

    @Mock
    RolesRepository rolesRepository;

    @Test
    @DisplayName("Testing all roles retrieval")
    public void test_all_roles(){
        when(rolesRepository.findAll()).thenReturn(new ArrayList<Roles>());
        rolesService.getAllRoles();
        verify(rolesRepository).findAll();
    }


    @Test
    @DisplayName("Testing roll creation")
    public void test_role_creation(){
        var roleCreationRequest = new RolesCreationRequest();
        roleCreationRequest.setName("tenant");
        when(rolesRepository.save(any(Roles.class))).thenReturn(new Roles());
        rolesService.createRole(roleCreationRequest);
        verify(rolesRepository).save(any(Roles.class));
    }

    @Test
    @DisplayName("Testing roll update")
    public void test_role_update(){
        var roleUpdateRequest = new RolesUpdateRequest();
        roleUpdateRequest.setName("admin");
        when(rolesRepository.save(any(Roles.class))).thenReturn(new Roles());
        rolesService.updateRole(1L,roleUpdateRequest);
        verify(rolesRepository).save(any(Roles.class));
    }



    @Test
    @DisplayName("Testing roll deletion")
    public void test_delete_role(){
        doNothing().when(rolesRepository).deleteById(1L);
        rolesService.deleteRole(1L);
        verify(rolesRepository).deleteById(1L);
    }



}
