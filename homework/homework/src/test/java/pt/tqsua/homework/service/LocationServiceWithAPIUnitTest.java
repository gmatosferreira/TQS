package pt.tqsua.homework.service;

import org.junit.jupiter.api.Test;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.Location;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationServiceWithAPIUnitTest {

    private LocationService locationService = new LocationService();

    @Test
    public void whenGetAll_thenReturnList() {
        // Call service
        Entity<List<Location>> locations = locationService.getAllLocations();

        // Test response
        assertThat(locations.getData())
            .hasSizeGreaterThan(18) // At least 18 districts + islands
            .extracting(Location::getName)
            .contains("Aveiro", "Braga", "Braga", "Santarém");

    }

    @Test
    public void whenGetByExistentName_thenReturnMatch() {
        // Call service
        Entity<List<Location>> locations = locationService.getLocationsByNameMatch("Brag");

        // Test response
        assertThat(locations.getData())
            .hasSize(2)
            .extracting(Location::getName)
            .containsExactly("Braga", "Bragança");
    }

    @Test
    public void whenGetByNonExistentName_thenReturnMatch() {
        // Call service
        Entity<List<Location>> locations = locationService.getLocationsByNameMatch("Arráb");

        // Test response
        assertThat(locations.getData()).hasSize(0);

    }

    @Test
    public void givenValidId_whenGetDetails_returnObject() {
        // Call service
        Entity<Optional<Location>> location = locationService.getLocationDetails(1010500);

        // Test response
        assertThat(location.getData().isEmpty()).isFalse();
        assertThat(location.getData().get().getName()).isEqualTo("Aveiro");
    }

    @Test
    public void givenInvalidId_whenGetDetails_returnObject() {
        // Call service
        Entity<Optional<Location>> location = locationService.getLocationDetails(2);

        // Test response
        assertThat(location.getData().isEmpty()).isTrue();
    }


}
