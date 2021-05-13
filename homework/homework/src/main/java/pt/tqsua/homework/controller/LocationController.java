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
import pt.tqsua.homework.model.Entity;
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
    public Entity<List<Location>> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/locations/search/{nameMatch}")
    public Entity<List<Location>> getLocationByNameMatch(@PathVariable String nameMatch) {
        System.out.println(String.format("GET Search by %s", nameMatch));
        return locationService.getLocationsByNameMatch(nameMatch);
    }

    @GetMapping("/locations/{locationId}")
    public ResponseEntity<Entity<Optional<Location>>> getLocationById(@PathVariable @NotNull Integer locationId) {
        Entity<Optional<Location>> l = locationService.getLocationDetails(locationId);
        Location location = l.getData().isPresent() ? l.getData().get() : null;
        HttpStatus status = l.getData().isPresent() ? HttpStatus.FOUND : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(l, status);
    }

}
