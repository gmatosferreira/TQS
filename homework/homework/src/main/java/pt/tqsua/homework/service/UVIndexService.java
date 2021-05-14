package pt.tqsua.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pt.tqsua.homework.cache.Cache;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.LocationsList;
import pt.tqsua.homework.model.UVIndex;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UVIndexService {

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private Cache<List<UVIndex>> cache = new Cache<>();

    static String API_URL = "https://api.ipma.pt/open-data/forecast/meteorology/uv/uv.json";

    public Entity<List<UVIndex>> getAllIndexes() {
        // Get data from API or cache
        List<UVIndex> indexesList = this.getWarnings();
        System.out.println(String.format("GET warnings list from API returned list of size %d", indexesList.size()));
        // Return locations list
        return new Entity<>(indexesList, this.cache.getHits(), this.cache.getMisses(), this.cache.getSize(), this.cache.getExpired());
    }

    public Entity<List<UVIndex>> getLocationIndexByDay(int location, int day) {
        // Get data from API or cache
        List<UVIndex> indexesList = this.getWarnings().stream().filter(w -> w.getLocation()==location && w.isDay(day)).collect(Collectors.toList());
        System.out.println(String.format("GET IV Index from API filtered by location %d for day %d returned list of size %d", location, day, indexesList.size()));
        // Return locations list
        return new Entity<>(indexesList, this.cache.getHits(), this.cache.getMisses(), this.cache.getSize(), this.cache.getExpired());
    }

    public Entity<List<UVIndex>> getLocationIndex(int location) {
        // Get data from API or cache
        List<UVIndex> indexesList = this.getWarnings().stream().filter(w -> w.getLocation()==location).collect(Collectors.toList());
        System.out.println(String.format("GET IV Index from API filtered by location %d returned list of size %d", location, indexesList.size()));
        // Return locations list
        return new Entity<>(indexesList, this.cache.getHits(), this.cache.getMisses(), this.cache.getSize(), this.cache.getExpired());
    }

    private List<UVIndex> getWarnings() {
        // Check if cache has locations
        Optional<List<UVIndex>> list = cache.get(UVIndexService.API_URL);
        if(list.isPresent()) {
            System.out.println("Cache has it, getting...");
            return list.get();
        }
        System.out.println("Cache does not have it, getting from API...");
        // If it has not, make request to API
        UVIndex[] response = restTemplate.getForObject(UVIndexService.API_URL, UVIndex[].class);
        if(response==null) {
            response = new UVIndex[0];
        }
        List<UVIndex> indexes = response.length>0 ? Arrays.asList(response) : Arrays.asList();
        // Save to cache
        this.cache.put(UVIndexService.API_URL, indexes);
        return indexes;
    }

}
