package com.careerclub.careerclub.Location;

import com.careerclub.careerclub.Entities.Location;
import com.careerclub.careerclub.Repositories.LocationRepository;
import com.careerclub.careerclub.Service.LocationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @InjectMocks
    LocationService locationService;

    @Mock
    LocationRepository locationRepository;

    @Test
    @DisplayName("Testing all locations")
    public void test_all_location(){
        when(locationRepository.findAll()).thenReturn(new ArrayList<>());
        locationService.getAllLocations();
        verify(locationRepository).findAll();
    }

}
