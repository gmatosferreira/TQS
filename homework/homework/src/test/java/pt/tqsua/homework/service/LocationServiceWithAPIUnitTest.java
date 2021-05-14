package pt.tqsua.homework.service;

import org.junit.jupiter.api.Test;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.Location;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LocationServiceWithAPIUnitTest {

    private LocationService locationService = new LocationService();

    @Test
    void whenGetAll_thenReturnList() {
        // Call service
        Entity<List<Location>> locations = locationService.getAllLocations();

        // Test response
        assertThat(locations.getData())
            .hasSizeGreaterThan(18) // At least 18 districts + islands
            .extracting(Location::getName)
            .contains("Aveiro", "Braga", "Braga", "Santarém");

    }

    @Test
    void whenGetByExistentName_thenReturnMatch() {
        // Call service
        Entity<List<Location>> locations = locationService.getLocationsByNameMatch("Brag");

        // Test response
        assertThat(locations.getData())
            .hasSize(2)
            .extracting(Location::getName)
            .containsExactly("Braga", "Bragança");
    }

    @Test
    void whenGetByNonExistentName_thenReturnMatch() {
        // Call service
        Entity<List<Location>> locations = locationService.getLocationsByNameMatch("Arráb");

        // Test response
        assertThat(locations.getData()).isEmpty();

    }

    @Test
    void givenValidId_whenGetDetails_returnObject() {
        // Call service
        Entity<Optional<Location>> location = locationService.getLocationDetails(1010500);

        // Test response
        assertThat(location.getData()).isPresent();
        assertThat(location.getData().get().getName()).isEqualTo("Aveiro");
    }

    @Test
    void givenInvalidId_whenGetDetails_returnObject() {
        // Call service
        Entity<Optional<Location>> location = locationService.getLocationDetails(2);

        // Test response
        assertThat(location.getData()).isNotPresent();
    }


}
