package pt.tqsua.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.model.LocationsList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class LocationServiceWithAPIUnitTest {

    private LocationService locationService = new LocationService();

    @Test
    public void whenGetAll_thenReturnList() {
        // Call service
        List<Location> locations = locationService.getAllLocations();

        // Test response
        assertThat(locations)
            .hasSizeGreaterThan(18) // At least 18 districts + islands
            .extracting(Location::getName)
            .contains("Aveiro", "Braga", "Braga", "Santarém");

    }

    @Test
    public void whenGetByExistentName_thenReturnMatch() {
        // Call service
        List<Location> locations = locationService.getLocationsByNameMatch("Brag");

        // Test response
        assertThat(locations)
            .hasSize(2)
            .extracting(Location::getName)
            .containsExactly("Braga", "Bragança");
    }

    @Test
    public void whenGetByNonExistentName_thenReturnMatch() {
        // Call service
        List<Location> locations = locationService.getLocationsByNameMatch("Arráb");

        // Test response
        assertThat(locations).hasSize(0);

    }

    @Test
    public void givenValidId_whenGetDetails_returnObject() {
        // Call service
        Optional<Location> location = locationService.getLocationDetails(1010500);

        // Test response
        assertThat(location.isEmpty()).isFalse();
        assertThat(location.get().getName()).isEqualTo("Aveiro");
    }

    @Test
    public void givenInvalidId_whenGetDetails_returnObject() {
        // Call service
        Optional<Location> location = locationService.getLocationDetails(2);

        // Test response
        assertThat(location.isEmpty()).isTrue();
    }


}
