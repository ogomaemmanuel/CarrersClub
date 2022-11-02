package com.careerclub.careerclub.Location;

import com.careerclub.careerclub.DTOs.LocationCreateRequest;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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


    @Test
    @DisplayName("Testing location retrieval by id")
    public void test_location_by_id(){
        when(locationRepository.findById(any(Long.class))).thenReturn(Optional.of(new Location()));
        locationService.getLocationById(1L);
        verify(locationRepository).findById(any(Long.class));
    }

    @Test
    @DisplayName("Testing location creation")
    public void test_location_creation(){
        when(locationRepository.save(any(Location.class))).thenReturn(new Location());
        var location = new LocationCreateRequest();
        location.setName("naivasha");
        locationService.createLocation(location);
        verify(locationRepository).save(any(Location.class));
    }

    @Test
    @DisplayName("Testing location update")
    public void test_location_update(){
        when(locationRepository.findByName(any(String.class))).thenReturn(Optional.of(new Location()));
        when(locationRepository.save(any(Location.class))).thenReturn(new Location());
        var location = new LocationCreateRequest();
        location.setName("nairobi");
        locationService.updateLocation(1L,location);
        verify(locationRepository).save(any(Location.class));
        verify(locationRepository).findByName(any(String.class));
    }

    @Test
    @DisplayName("Testing location deletion")
    public void test_location_deletion(){
        when(locationRepository.findByName(any(String.class))).thenReturn(Optional.of(new Location()));
        doNothing().when(locationRepository).delete(any(Location.class));
        locationService.deleteLocation(1L);
        verify(locationRepository).delete(any(Location.class));
    }

}
