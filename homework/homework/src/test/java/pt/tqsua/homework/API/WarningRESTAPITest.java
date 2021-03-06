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
import pt.tqsua.homework.model.Warning;
import pt.tqsua.homework.model.enums.AwarenessLevel;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WarningRESTAPITest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void whenGetWarnings_thenReturnJsonArray() {
        // Make request to API
        ResponseEntity<Entity<List<Warning>>> response = restTemplate.exchange("/api/warnings", HttpMethod.GET, null, new ParameterizedTypeReference<Entity<List<Warning>>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        if (response.getBody().getData().size()>0) {
            assertThat(response.getBody().getData())
                .extracting(Warning::getLevel)
                .containsAnyElementsOf(Arrays.asList(AwarenessLevel.GREEN.toString(), AwarenessLevel.ORANGE.toString(), AwarenessLevel.RED.toString(), AwarenessLevel.YELLOW.toString()));
        }
        assertThat(response.getBody().getRequests()).isPositive();
        assertThat(response.getBody().getCacheSize()).isEqualTo(1);
    }

    @Test
    @Order(2)
    void whenGetLocation_thenReturnList() {
        // Make request to API
        ResponseEntity<Entity<List<Warning>>> response = restTemplate.exchange(String.format("/api/warnings/%s", "AVR"), HttpMethod.GET, null, new ParameterizedTypeReference<Entity<List<Warning>>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        if (response.getBody().getData().size()>0) {
            assertThat(response.getBody().getData())
                .extracting(Warning::getLevel)
                .containsAnyElementsOf(Arrays.asList(AwarenessLevel.GREEN.toString(), AwarenessLevel.ORANGE.toString(), AwarenessLevel.RED.toString(), AwarenessLevel.YELLOW.toString()));
            assertThat(response.getBody().getData())
                .extracting(Warning::getLocation)
                .containsOnly("AVR");
        }
        assertThat(response.getBody().getRequests()).isPositive();
        assertThat(response.getBody().getCacheSize()).isEqualTo(1);
    }
}
