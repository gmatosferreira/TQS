package pt.tqsua.homework.API;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.repository.LocationRepository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class LocationRESTAPITest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LocationRepository locationRepository;

    @AfterEach
    public void resetDb() {
        locationRepository.deleteAll();
    }

    @Test
    public void givenLocations_whenGetLocations_thenReturnJsonArray() {
        // Create objects and persist them on repository
        locationRepository.saveAndFlush(new Location(1, "AVR", "Aveiro", "1.234", "5.678"));
        locationRepository.saveAndFlush(new Location(2, "BRG", "Braga", "1.234", "5.678"));

        // Make request to API
        ResponseEntity<List<Location>> response = restTemplate.exchange("/api/locations", HttpMethod.GET, null, new ParameterizedTypeReference<List<Location>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
            .hasSize(2)
            .extracting(Location::getName)
            .containsExactly("Aveiro", "Braga");
    }

    @Test
    public void notGivenLocations_whenFindAll_thenReturnEmptyList() {
        // Make request to API
        ResponseEntity<List<Location>> response = restTemplate.exchange("/api/locations", HttpMethod.GET, null, new ParameterizedTypeReference<List<Location>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .hasSize(0);
    }

    @Test
    public void whenGetNameMatch_thenReturnList() {
        // Create objects and persist them on repository
        locationRepository.saveAndFlush(new Location(1, "AVR", "Aveiro", "1.234", "5.678"));
        locationRepository.saveAndFlush(new Location(2, "BRG", "Braga", "1.234", "5.678"));
        locationRepository.saveAndFlush(new Location(3, "BGC", "Bragança", "5.698", "-5.369"));

        // Make request to API
        ResponseEntity<List<Location>> response = restTemplate.exchange(String.format("/api/locations/search/%s", "Brag"), HttpMethod.GET, null, new ParameterizedTypeReference<List<Location>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
            .hasSize(2)
            .extracting(Location::getName)
            .containsExactly("Braga", "Bragança");
    }

    @Test
    public void whenGetNameDontMatch_thenReturnEmptyList() {
        // Make request to API
        ResponseEntity<List<Location>> response = restTemplate.exchange(String.format("/api/locations/search/%s", "Port"), HttpMethod.GET, null, new ParameterizedTypeReference<List<Location>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .hasSize(0);
    }

    @Test
    public void givenLocation_whenGetLocation_thenReturnLocation() {
        // Create objects and persist them on db
        locationRepository.saveAndFlush(new Location(1, "AVR", "Aveiro", "1.234", "5.678"));

        // Make request to API
        ResponseEntity<Location> response = restTemplate.exchange(String.format("/api/locations/%d", 1), HttpMethod.GET, null, new ParameterizedTypeReference<Location>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getBody().getName()).isEqualTo("Aveiro");
    }

    @Test
    public void whenGetInexistentLocation_thenReturnNotFound() {
        // Make request to API
        ResponseEntity<Location> response = restTemplate.exchange(String.format("/api/locations/%d", 999), HttpMethod.GET, null, new ParameterizedTypeReference<Location>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
