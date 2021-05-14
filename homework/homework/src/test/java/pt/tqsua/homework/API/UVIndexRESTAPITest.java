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
import pt.tqsua.homework.model.UVIndex;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UVIndexRESTAPITest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void whenGetIndexes_thenReturnJsonArray() {
        // Make request to API
        ResponseEntity<Entity<List<UVIndex>>> response = restTemplate.exchange("/api/uvindexes", HttpMethod.GET, null, new ParameterizedTypeReference<Entity<List<UVIndex>>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getData())
            .extracting(UVIndex::getIndexClass)
            .containsAnyElementsOf(Arrays.asList("Extremo", "Muito Elevado", "Elevado", "Moderado", "Baixo"));
        assertThat(response.getBody().getRequests()).isPositive();
        assertThat(response.getBody().getCacheSize()).isEqualTo(1);
    }

    @Test
    @Order(2)
    void whenGetByLocation_thenReturnList() {
        int location = 3480200;
        // Make request to API
        ResponseEntity<Entity<List<UVIndex>>> response = restTemplate.exchange(String.format("/api/uvindexes/%d", location), HttpMethod.GET, null, new ParameterizedTypeReference<Entity<List<UVIndex>>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getData())
            .extracting(UVIndex::getIndexClass)
            .containsAnyElementsOf(Arrays.asList("Extremo", "Muito Elevado", "Elevado", "Moderado", "Baixo"));
        assertThat(response.getBody().getData())
            .extracting(UVIndex::getLocation)
            .containsOnly(location);
        assertThat(response.getBody().getRequests()).isPositive();
        assertThat(response.getBody().getCacheSize()).isEqualTo(1);
    }

    @Test
    @Order(3)
    void whenGetByLocationAndDayIndex_thenReturnList() {
        int location = 3480200;
        int index = 0;
        // Make request to API
        ResponseEntity<Entity<List<UVIndex>>> response = restTemplate.exchange(String.format("/api/uvindexes/%d/%d", location, index), HttpMethod.GET, null, new ParameterizedTypeReference<Entity<List<UVIndex>>>() {});

        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getData())
                .extracting(UVIndex::getIndexClass)
                .containsAnyElementsOf(Arrays.asList("Extremo", "Muito Elevado", "Elevado", "Moderado", "Baixo"));
        assertThat(response.getBody().getData())
                .extracting(UVIndex::getLocation)
                .containsOnly(location);
        assertThat(response.getBody().getData().stream().allMatch(i -> i.isDay(index))).isTrue();
        assertThat(response.getBody().getRequests()).isPositive();
        assertThat(response.getBody().getCacheSize()).isEqualTo(1);
    }
}
