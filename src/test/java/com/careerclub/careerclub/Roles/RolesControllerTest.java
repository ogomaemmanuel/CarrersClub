package com.careerclub.careerclub.Roles;

import com.careerclub.careerclub.Controllers.RolesController;
import com.careerclub.careerclub.Service.CustomUserDetailsService;
import com.careerclub.careerclub.Service.RolesService;
import com.careerclub.careerclub.Utils.RoleValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @Test
    @DisplayName("Testing get request for all roles")
    public void test_all_roles() throws Exception {
        when(rolesService.getAllRoles()).thenReturn(new ArrayList());
        mockMvc.perform(MockMvcRequestBuilders.get("/roles")).andExpect(status().isOk());
    }

}
