package pt.tqsua.homework.API;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.model.LocationsList;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocationRESTAPITest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    @Order(1)
    public void whenGetLocations_thenReturnJsonArray() {
        // Make request to API
        ResponseEntity<Entity<List<Location>>> response = restTemplate.exchange("/api/locations", HttpMethod.GET, null, new ParameterizedTypeReference<Entity<List<Location>>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getData())
                .hasSizeGreaterThan(18)
                .extracting(Location::getName)
                .contains("Aveiro", "Braga", "Santarém");
        assertThat(response.getBody().getRequests()).isGreaterThanOrEqualTo(1);
        assertThat(response.getBody().getCacheSize()).isEqualTo(1);
    }

    @Test
    @Order(2)
    public void whenGetNameMatch_thenReturnList() {
        // Make request to API
        ResponseEntity<Entity<List<Location>>> response = restTemplate.exchange(String.format("/api/locations/search/%s", "Brag"), HttpMethod.GET, null, new ParameterizedTypeReference<Entity<List<Location>>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getData())
                .hasSize(2)
                .extracting(Location::getName)
                .containsExactly("Braga", "Bragança");
        aassertThat(response.getBody().getRequests()).isGreaterThanOrEqualTo(1);
        assertThat(response.getBody().getCacheSize()).isEqualTo(1);
    }

    @Test
    @Order(3)
    public void whenGetNameDontMatch_thenReturnEmptyList() {
        // Make request to API
        ResponseEntity<Entity<List<Location>>> response = restTemplate.exchange(String.format("/api/locations/search/%s", "Arráb"), HttpMethod.GET, null, new ParameterizedTypeReference<Entity<List<Location>>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getData()).hasSize(0);
        assertThat(response.getBody().getRequests()).isGreaterThanOrEqualTo(1);
        assertThat(response.getBody().getCacheSize()).isEqualTo(1);
    }

    @Test
    @Order(4)
    public void givenLocation_whenGetLocation_thenReturnLocation() {
         // Make request to API
        ResponseEntity<Entity<Location>> response = restTemplate.exchange(String.format("/api/locations/%d", 1010500), HttpMethod.GET, null, new ParameterizedTypeReference<Entity<Location>>() {});
        System.out.println(response.getBody());
        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getBody().getData().getName()).isEqualTo("Aveiro");
        assertThat(response.getBody().getRequests()).isGreaterThanOrEqualTo(1);
        assertThat(response.getBody().getCacheSize()).isEqualTo(1);
    }

    @Test
    @Order(5)
    public void whenGetInexistentLocation_thenReturnNotFound() {
        // Make request to API
        ResponseEntity<Entity<Location>> response = restTemplate.exchange(String.format("/api/locations/%d", 999), HttpMethod.GET, null, new ParameterizedTypeReference<Entity<Location>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getRequests()).isGreaterThanOrEqualTo(1);
        assertThat(response.getBody().getCacheSize()).isEqualTo(1);
    }

}
