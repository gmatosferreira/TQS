package pt.tqsua.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.model.LocationsList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService {

    // https://stackoverflow.com/questions/28024942/how-to-autowire-resttemplate-using-annotations
    // https://howtodoinjava.com/spring-boot2/resttemplate/spring-restful-client-resttemplate-example/
    // https://spring.io/guides/gs/consuming-rest/
    // https://gist.github.com/RealDeanZhao/38821bc1efeb7e2a9bcd554cc06cdf96
    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    public List<Location> getAllLocations() {
        // Call API
        LocationsList locationList = restTemplate.getForObject("https://api.ipma.pt/open-data/distrits-islands.json", LocationsList.class);
        System.out.println(String.format("GET locations list from API returned list of size %d", locationList.getLocations().size()));
        // Return locations list
        return locationList.getLocations();
    }

    public List<Location> getLocationsByNameMatch(String nameMatch) {
        // Call API
        LocationsList locationList = restTemplate.getForObject("https://api.ipma.pt/open-data/distrits-islands.json", LocationsList.class);
        return locationList.getLocations().stream().filter(l -> l.getName().contains(nameMatch)).collect(Collectors.toList());
    }

    public Optional<Location> getLocationDetails(Integer locationId) {
        // Call API
        LocationsList locationList = restTemplate.getForObject("https://api.ipma.pt/open-data/distrits-islands.json", LocationsList.class);
        List<Location> location = locationList.getLocations().stream().filter(l -> l.getId().equals(locationId)).collect(Collectors.toList());
        System.out.println(String.format("Filtering %d locations for ID %d and got %d matches.", locationList.getLocations().size(), locationId, location.size()));
        return location.size()==1 ? Optional.of(location.get(0)) : Optional.empty();
    }

}
