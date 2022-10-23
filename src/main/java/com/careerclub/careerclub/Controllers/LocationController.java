package com.careerclub.careerclub.Controllers;

import com.careerclub.careerclub.DTOs.LocationCreateRequest;
import com.careerclub.careerclub.Entities.Location;
import com.careerclub.careerclub.Service.LocationService;
import com.careerclub.careerclub.Utils.ErrorConverter;
import com.careerclub.careerclub.Utils.LocationValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

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

    @GetMapping("/{name}")
    public ResponseEntity<Location> getLocationByName(@PathVariable String name){
        var location = locationService.getLocationByName(name);
        return ResponseEntity.of(location);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id){
        var location = locationService.getLocationById(id);
        return ResponseEntity.of(location);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createLocation(@Valid @RequestBody LocationCreateRequest locationCreateRequest, BindingResult errors){
        locationValidator.validate(locationCreateRequest,errors);
        if(!(errors.hasErrors())){
            var location = locationService.createLocation(locationCreateRequest);
            return ResponseEntity.ok(location);
        }
        return ResponseEntity.badRequest().body(ErrorConverter.convert(errors));

    }

    @PutMapping("/update/{name}")
    public ResponseEntity<?> updateLocation(@PathVariable String name,@Valid @RequestBody LocationCreateRequest locationCreateRequest,BindingResult errors){
        locationValidator.validate(locationCreateRequest,errors);
        if(!(errors.hasErrors())){
            var location = locationService.updateLocation(name,locationCreateRequest);
            return ResponseEntity.of(location);
        }
        return ResponseEntity.badRequest().body(ErrorConverter.convert(errors));
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<HashMap<Object,Object>> deleteLocation(@PathVariable String name){
        var message = locationService.deleteLocation(name);
        return ResponseEntity.ok(message);
    }

}
