package pt.tqsua.homework.controller;

import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.UVIndex;
import pt.tqsua.homework.model.Warning;
import pt.tqsua.homework.service.UVIndexService;
import pt.tqsua.homework.service.WarningService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UVIndexController {

    @Autowired
    private UVIndexService service;

    @GetMapping("/uvindexes")
    public Entity<List<UVIndex>> getAllIndexes() {
        return null;
    }

    @GetMapping("/uvindexes/{locationId}")
    public Entity<List<UVIndex>> getIndexesByLocation(@PathVariable @NotNull int locationId) {
        return null;
    }

    @GetMapping("/uvindexes/{locationId}/{day}")
    public Entity<List<UVIndex>> getIndexesByLocation(@PathVariable @NotNull int locationId, @PathVariable @NotNull int day) {
        return null;
    }

}
