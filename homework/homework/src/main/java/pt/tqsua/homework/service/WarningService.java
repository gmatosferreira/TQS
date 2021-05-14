package pt.tqsua.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pt.tqsua.homework.cache.Cache;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.Warning;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarningService {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private Cache<List<Warning>> cache = new Cache<>();

    static String API_URL = "https://api.ipma.pt/open-data/forecast/warnings/warnings_www.json";

    public Entity<List<Warning>> getAllWarnings() {
        // Get data from API or cache
        List<Warning> warningsList = this.getWarnings();
        System.out.println(String.format("GET warnings list from API returned list of size %d", warningsList.size()));
        // Return locations list
        return new Entity<>(warningsList, this.cache.getHits(), this.cache.getMisses(), this.cache.getSize(), this.cache.getExpired());
    }

    public Entity<List<Warning>> getLocationWarnings(String location) {
        // Get data from API or cache
        List<Warning> warningsList = this.getWarnings().stream().filter(w -> w.getLocation().equals(location)).collect(Collectors.toList());
        System.out.println(String.format("GET warnings list from API filtered by location %s returned list of size %d", location, warningsList.size()));
        // Return locations list
        return new Entity<>(warningsList, this.cache.getHits(), this.cache.getMisses(), this.cache.getSize(), this.cache.getExpired());
    }

    private List<Warning> getWarnings() {
        // Check if cache has locations
        Optional<List<Warning>> list = cache.get(WarningService.API_URL);
        if(list.isPresent()) {
            System.out.println("Cache has it, getting...");
            return list.get();
        }
        System.out.println("Cache does not have it, getting from API...");
        // If it has not, make request to API
        Warning[] response = restTemplate.getForObject(WarningService.API_URL, Warning[].class);
        if(response==null) {
            response = new Warning[0];
        }
        List<Warning> warnings = response.length>0 ? Arrays.asList(response) : Arrays.asList();
        // Save to cache
        this.cache.put(WarningService.API_URL, warnings);
        return warnings;
    }

}
