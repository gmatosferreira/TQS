package pt.tqsua.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.service.LocationService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"localhost", "127.0.0.1"})
public class LocationController {

    private static final Logger log = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    @GetMapping("/locations")
    public Entity<List<Location>> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/locations/search/{nameMatch}")
    public Entity<List<Location>> getLocationByNameMatch(@PathVariable String nameMatch) {
        log.debug(String.format("GET Search by %s", nameMatch));
        return locationService.getLocationsByNameMatch(nameMatch);
    }

    @GetMapping("/locations/{locationId}")
    public ResponseEntity<Entity<Optional<Location>>> getLocationById(@PathVariable @NotNull Integer locationId) {
        Entity<Optional<Location>> l = locationService.getLocationDetails(locationId);
        Optional<Location> locationOp = l.getData();
        Location location = null;
        if (locationOp.isPresent()) {
            location = locationOp.get();
        }
        HttpStatus status = location!=null ? HttpStatus.FOUND : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(l, status);
    }

}
