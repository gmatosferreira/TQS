package pt.tqsua.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.UVIndex;
import pt.tqsua.homework.service.UVIndexService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"localhost", "127.0.0.1"})
public class UVIndexController {

    private static final Logger log = LoggerFactory.getLogger(UVIndexController.class);

    @Autowired
    private UVIndexService service;

    @GetMapping("/uvindexes")
    public Entity<List<UVIndex>> getAllIndexes() {
        return service.getAllIndexes();
    }

    @GetMapping("/uvindexes/{locationId}")
    public Entity<List<UVIndex>> getIndexesByLocation(@PathVariable @NotNull int locationId) {
        log.debug(String.format("GET UVIndex for location %d", locationId));
        return service.getLocationIndex(locationId);
    }

    @GetMapping("/uvindexes/{locationId}/{day}")
    public Entity<List<UVIndex>> getIndexesByLocation(@PathVariable @NotNull int locationId, @PathVariable @NotNull int day) {
        log.debug(String.format("GET UVIndex for location %d and day %d", locationId, day));
        return service.getLocationIndexByDay(locationId, day);
    }

}
