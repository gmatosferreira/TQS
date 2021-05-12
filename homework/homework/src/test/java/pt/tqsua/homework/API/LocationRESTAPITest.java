package pt.tqsua.homework.API;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.model.LocationsList;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationRESTAPITest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void whenGetLocations_thenReturnJsonArray() {
        // Make request to API
        ResponseEntity<List<Location>> response = restTemplate.exchange("/api/locations", HttpMethod.GET, null, new ParameterizedTypeReference<List<Location>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .hasSizeGreaterThan(18)
                .extracting(Location::getName)
                .contains("Aveiro", "Braga", "Santarém");
    }

    @Test
    public void whenGetNameMatch_thenReturnList() {
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
        ResponseEntity<List<Location>> response = restTemplate.exchange(String.format("/api/locations/search/%s", "Arráb"), HttpMethod.GET, null, new ParameterizedTypeReference<List<Location>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(0);
    }

    @Test
    public void givenLocation_whenGetLocation_thenReturnLocation() {
             // Make request to API
        ResponseEntity<Location> response = restTemplate.getForEntity(String.format("/api/locations/%d", 1010500), Location.class);
        System.out.println(response.getBody());
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
