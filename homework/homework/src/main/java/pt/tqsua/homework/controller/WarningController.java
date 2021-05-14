package pt.tqsua.homework.controller;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(WarningController.class);

    @Autowired
    private WarningService service;

    @GetMapping("/warnings")
    public Entity<List<Warning>> getAllWarnings() {
        return service.getAllWarnings();
    }

    @GetMapping("/warnings/{locationId}")
    public Entity<List<Warning>> getLocationByNameMatch(@PathVariable @NotNull String locationId) {
        log.debug(String.format("GET Warnings search by %s", locationId));
        return service.getLocationWarnings(locationId);
    }

}
