package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.RecordNotFoundException;
import com.careerclub.careerclub.DTOs.LocationCreateRequest;
import com.careerclub.careerclub.Entities.Location;
import com.careerclub.careerclub.Repositories.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getAllLocations(){
        return locationRepository.findAll();
    }

    public Optional<Location> getLocationByName(String name){
        var location = locationRepository.findByName(name);
        if(location.isEmpty()){
            throw new RecordNotFoundException("Location with the name "+name+" doesn't exist");
        }
        return location;
    }

    public Location createLocation(LocationCreateRequest locationCreateRequest){
        var location = new Location();
        location.setName(locationCreateRequest.getName());
        locationRepository.save(location);
        return  location;
    }

    public Optional<Location> updateLocation(String name,LocationCreateRequest locationCreateRequest){
        var location = locationRepository.findByName(name);
        location.ifPresentOrElse(l->{
            l.setName(locationCreateRequest.getName());
            locationRepository.save(l);
        },()->{
            throw new RecordNotFoundException("Location with name "+name+" doesn't exist.");
        });
        return location;
    }

    public HashMap<Object,Object> deleteLocation(String name){
        var validate = new HashMap<>();
        var location = locationRepository.findByName(name);
        location.ifPresentOrElse(l->{
            locationRepository.delete(l);
            validate.put("message","Location deleted successfully.");
        },()->{
            throw new RecordNotFoundException("Location with the name "+name+" doesn't exist.");
        });
        return validate;
    }

}

