package pt.tqsua.homework.controller;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.model.Warning;
import pt.tqsua.homework.service.LocationService;
import pt.tqsua.homework.service.WarningService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"localhost", "127.0.0.1"})
public class WarningController {

    @Autowired
    private WarningService service;

    @GetMapping("/warnings")
    public Entity<List<Warning>> getAllWarnings() {
        return service.getAllWarnings();
    }

    @GetMapping("/warnings/{locationId}")
    public Entity<List<Warning>> getLocationByNameMatch(@PathVariable @NotNull String locationId) {
        System.out.println(String.format("GET Warnings search by %s", locationId));
        return service.getLocationWarnings(locationId);
    }

}
