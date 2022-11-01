package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.LocationCreateRequest;
import com.careerclub.careerclub.Entities.Location;
import com.careerclub.careerclub.Service.LocationService;
import com.careerclub.careerclub.Utils.ErrorConverter;
import com.careerclub.careerclub.Utils.LocationValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Tag(name = "Location controller")
@RestController
@RequestMapping("locations")
public class LocationController {
    private final LocationService locationService;
    private final LocationValidator locationValidator;

    public LocationController(LocationService locationService, LocationValidator locationValidator) {
        this.locationService = locationService;
        this.locationValidator = locationValidator;
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations(){
        var locations = locationService.getAllLocations();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id){
        var location = locationService.getLocationById(id);
        return ResponseEntity.of(location);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLocation(@Valid @RequestBody LocationCreateRequest locationCreateRequest){
        var location = locationService.createLocation(locationCreateRequest);
        return ResponseEntity.status(201).body(location);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable Long id,@Valid @RequestBody LocationCreateRequest locationCreateRequest){
        var location = locationService.updateLocation(id,locationCreateRequest);
        return ResponseEntity.of(location);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HashMap<Object,Object>> deleteLocation(@PathVariable Long id){
        var message = locationService.deleteLocation(id);
        return ResponseEntity.ok(message);
    }

}
