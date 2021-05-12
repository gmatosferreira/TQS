package pt.tqsua.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.repository.LocationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;


    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public List<Location> getLocationsByNameMatch(String nameMatch) {
        return locationRepository.findByNameContains(nameMatch);
    }

    public Optional<Location> getLocationDetails(Integer locationId) {
        return locationRepository.findById(locationId);
    }

}
