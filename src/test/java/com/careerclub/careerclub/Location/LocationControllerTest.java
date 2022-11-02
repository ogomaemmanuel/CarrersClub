package com.careerclub.careerclub.Location;


import com.careerclub.careerclub.Controllers.LocationController;
import com.careerclub.careerclub.DTOs.LocationCreateRequest;
import com.careerclub.careerclub.Service.CustomUserDetailsService;
import com.careerclub.careerclub.Service.LocationService;
import com.careerclub.careerclub.Utils.LocationValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {LocationController.class})
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class LocationControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LocationService locationService;

    @MockBean
    LocationValidator locationValidator;

    @MockBean
    CustomUserDetailsService customUserDetailsService;


    @Test
    @DisplayName("testing all locations endpoint")
    public void test_all_locations() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/locations")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("testing location retrieval by id endpoint")
    public void test_location_get_id() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/locations/{id}",1)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("testing location retrieval by name endpoint")
    public void test_location_get_by_name() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/locations/name/{name}","nairobi")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("testing location creation endpoint")
    public void test_location_creation() throws Exception{
        var location = new LocationCreateRequest();
        location.setName("naivasha");
        String lct = objectMapper.writeValueAsString(location);
        mockMvc.perform(MockMvcRequestBuilders.post("/locations/create")
                .content(lct)
                .contentType(MediaType.APPLICATION_JSON
                )).andExpect(status().isCreated());

    }

    @Test
    @DisplayName("testing location update endpoint")
    public void test_location_update() throws Exception{
        var location = new LocationCreateRequest();
        location.setName("kajiado");
        String lct = objectMapper.writeValueAsString(location);
        mockMvc.perform(MockMvcRequestBuilders.put("/locations/update/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(lct)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("testing location deletion endpoint")
    public void test_location_deletion() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/locations/delete/{name}","nairobi")).andExpect(status().isBadRequest());
    }

}
