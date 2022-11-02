package com.careerclub.careerclub.Roles;

import com.careerclub.careerclub.Controllers.RolesController;
import com.careerclub.careerclub.DTOs.AddRoleToUserRequest;
import com.careerclub.careerclub.DTOs.RolesCreationRequest;
import com.careerclub.careerclub.DTOs.RolesUpdateRequest;
import com.careerclub.careerclub.Service.CustomUserDetailsService;
import com.careerclub.careerclub.Service.RolesService;
import com.careerclub.careerclub.Utils.RoleValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(controllers = {RolesController.class})
public class RolesControllerTest {

    @MockBean
    RolesService rolesService;

    @MockBean
    RoleValidator roleValidator;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Testing get request for all roles")
    public void test_all_roles() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/roles")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("testing get role by id endpoint")
    public void test_get_role_by_id() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/roles/{id}",1)).andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("testing role creation endpoint")
    public void test_role_creation() throws Exception{
        var role = new RolesCreationRequest();
        role.setName("tenant");
        String rl = objectMapper.writeValueAsString(role);
        mockMvc.perform(MockMvcRequestBuilders.post("/roles/create")
                .content(rl)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("testing role update endpoint")
    public void test_role_update() throws Exception{
        var role = new RolesUpdateRequest();
        role.setName("landlord");
        String rl = objectMapper.writeValueAsString(role);
        mockMvc.perform(MockMvcRequestBuilders.put("/roles/update/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(rl)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("testing role deletion endpoint")
    public void test_role_deletion() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/roles/delete/{id}",1)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("testing role assigning to users")
    public void test_role_add_to_user() throws Exception{
        var roleToAdd = new AddRoleToUserRequest();
        roleToAdd.setUserId(1L);
        roleToAdd.setRoleId(1L);
        String rlToAdd = objectMapper.writeValueAsString(roleToAdd);
        mockMvc.perform(MockMvcRequestBuilders.post("/roles/add-role")
                .content(rlToAdd)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}
