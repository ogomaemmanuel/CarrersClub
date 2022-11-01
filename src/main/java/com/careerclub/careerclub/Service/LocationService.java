package com.careerclub.careerclub.Service;

import com.careerclub.careerclub.Advice.DuplicateException;
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


    public Optional<Location> getLocationById(Long id){
        var location = locationRepository.findById(id);
        if(location.isEmpty()){
            throw new RecordNotFoundException("Location with the given id doesn't exist.");
        }
        return location;
    }

    public Location createLocation(LocationCreateRequest locationCreateRequest){
        var location = new Location();
        var locationName = locationRepository.findByName(locationCreateRequest.getName());
        if(locationName.isPresent()){
            throw new DuplicateException("Location with the given name already exists");
        }

        location.setName(locationCreateRequest.getName());
        locationRepository.save(location);

        return  location;
    }

    public Optional<Location> updateLocation(Long locationId,LocationCreateRequest locationCreateRequest){
        var location = locationRepository.findById(locationId);
        var locationName = locationRepository.findByName(locationCreateRequest.getName());
        if(locationName.isPresent()){
            throw new DuplicateException("Location with the given name already exists");
        }
        location.ifPresentOrElse(l->{
            l.setName(locationCreateRequest.getName());
            locationRepository.save(l);
        },()->{
            throw new RecordNotFoundException("Location with the given id doesn't exist.");
        });
        return location;
    }

    public HashMap<Object,Object> deleteLocation(Long locationId){
        var validate = new HashMap<>();
        var location = locationRepository.findById(locationId);
        location.ifPresentOrElse(l->{
            locationRepository.delete(l);
            validate.put("message","Location deleted successfully.");
        },()->{
            throw new RecordNotFoundException("Location with the given id doesn't exist.");
        });
        return validate;
    }

}

