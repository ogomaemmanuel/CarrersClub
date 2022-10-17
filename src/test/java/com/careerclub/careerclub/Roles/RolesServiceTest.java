package com.careerclub.careerclub.Roles;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.RolesCreationRequest;
import com.careerclub.careerclub.Entities.Roles;
import com.careerclub.careerclub.Repositories.RolesRepository;
import com.careerclub.careerclub.Service.RolesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    @DisplayName("Testing single role retrieval by name")
    public void test_single_role(){
        when(rolesRepository.findByName(any(String.class))).thenReturn(new Roles());
        rolesService.getSingleRoleByName("admin");
        verify(rolesRepository).findByName("admin");
    }


}
