package pt.tqsua.homework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pt.tqsua.homework.cache.Cache;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.model.LocationsList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private static final Logger log = LoggerFactory.getLogger(LocationService.class);

    // https://stackoverflow.com/questions/28024942/how-to-autowire-resttemplate-using-annotations
    // https://howtodoinjava.com/spring-boot2/resttemplate/spring-restful-client-resttemplate-example/
    // https://spring.io/guides/gs/consuming-rest/
    // https://gist.github.com/RealDeanZhao/38821bc1efeb7e2a9bcd554cc06cdf96
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private Cache<LocationsList> cache = new Cache<>();

    static String API_URL = "https://api.ipma.pt/open-data/distrits-islands.json";

    public Entity<List<Location>> getAllLocations() {
        // Get data from API or cache
        LocationsList locationList = this.getLocations();
        log.debug("GET locations list from API returned list of size {}", locationList.getLocations().size());
        // Return locations list
        return new Entity<>(locationList.getLocations(), this.cache.getHits(), this.cache.getMisses(), this.cache.getSize(), this.cache.getExpired());
    }

    public Entity<List<Location>> getLocationsByNameMatch(String nameMatch) {
        // Get data from API or cache
        LocationsList locationList = this.getLocations();
        // Filter by name
        List<Location> location = locationList.getLocations().stream().filter(l -> l.getName().toLowerCase().contains(nameMatch.toLowerCase())).collect(Collectors.toList());
        log.debug("Filtering {} locations for name {} and got {} matches.", locationList.getLocations().size(), nameMatch, location.size());
        log.debug(location.size()>0 ? location.get(0).toString() : "NOT FOUND");
        return new Entity<>(location, this.cache.getHits(), this.cache.getMisses(), this.cache.getSize(), this.cache.getExpired());
    }

    public Entity<Optional<Location>> getLocationDetails(Integer locationId) {
        // Get data from API or cache
        LocationsList locationList = this.getLocations();
        // Filter by given ID
        List<Location> location = locationList.getLocations().stream().filter(l -> l.getId().equals(locationId)).collect(Collectors.toList());
        log.debug("Filtering {} locations for ID {} and got {} matches.", locationList.getLocations().size(), locationId, location.size());
        log.debug(location.size()>0 ? location.get(0).toString() : "NOT FOUND");
        Optional<Location> response = location.size()==1 ? Optional.of(location.get(0)) : Optional.empty();
        return new Entity<>(response, this.cache.getHits(), this.cache.getMisses(), this.cache.getSize(), this.cache.getExpired());
    }

    private LocationsList getLocations() {
        // Check if cache has locations
        Optional<LocationsList> list = cache.get(LocationService.API_URL);
        if(list.isPresent()) {
            log.debug("Cache has it, getting...");
            return list.get();
        }
        log.debug("Cache does not have it, getting from API...");
        // If it has not, make request to API
        LocationsList locations = restTemplate.getForObject(LocationService.API_URL, LocationsList.class);
        if(locations==null) {
            locations = new LocationsList();
        }
        // Save to cache
        this.cache.put(LocationService.API_URL, locations);
        return locations;
    }

}
