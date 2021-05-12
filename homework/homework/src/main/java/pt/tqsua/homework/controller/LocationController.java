package pt.tqsua.homework.controller;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.service.LocationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/locations")
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/locations/search/{nameMatch}")
    public List<Location> getLocationByNameMatch(@PathVariable @NotNull String nameMatch) {
        return locationService.getLocationsByNameMatch(nameMatch);
    }

    @GetMapping("/locations/{locationId}")
    public ResponseEntity<Location> getLocationById(@PathVariable @NotNull Integer locationId) {
        Optional<Location> l = locationService.getLocationDetails(locationId);
        Location location = l.isPresent() ? l.get() : null;
        HttpStatus status = l.isPresent() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(location, status);
    }

}
