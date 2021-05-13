package pt.tqsua.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import pt.tqsua.homework.cache.Cache;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.model.LocationsList;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LocationServiceWithMockAPIAndCacheValidationUnitTest {

    @Mock(lenient = true)
    private RestTemplate restTemplate;

    @InjectMocks
    private LocationService locationService;

    @Nested
    class PositiveAnswer {

        @BeforeEach
        public void setUp() {
            // Create objects
            Location l1 = new Location(1, "AVR", "Aveiro", "1.234", "5.678");
            Location l2 = new Location(2, "BRG", "Braga", "1.234", "5.678");
            Location l3 = new Location(3, "BGR", "Bragança", "5.698", "-5.369");
            Location l4 = new Location(4, "STR", "Santarém", "5.698", "-5.869");

            // Mock real API
            when(restTemplate.getForObject(LocationService.API_URL, LocationsList.class)).thenReturn(new LocationsList(Arrays.asList(l1,l2,l3,l4)));
        }

        @Test
        public void whenGetAll_thenReturnList() {
            // Call service
            Entity<List<Location>> locations = locationService.getAllLocations();

            // Test response
            assertThat(locations.getData())
                    .hasSize(4)
                    .extracting(Location::getName)
                    .containsExactly("Aveiro", "Braga", "Bragança", "Santarém");
            assertThat(locations.getRequests()).isEqualTo(1);

            // Test RestTemplate usage
            Mockito.verify(restTemplate, VerificationModeFactory.times(1)).getForObject("https://api.ipma.pt/open-data/distrits-islands.json", LocationsList.class);
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
            assertThat(locations.getRequests()).isEqualTo(1);

            // Test RestTemplate usage
            Mockito.verify(restTemplate, VerificationModeFactory.times(1)).getForObject("https://api.ipma.pt/open-data/distrits-islands.json", LocationsList.class);
        }

        @Test
        public void whenGetByNonExistentName_thenReturnMatch() {
            // Call service
            Entity<List<Location>> locations = locationService.getLocationsByNameMatch("Port");

            // Test response
            assertThat(locations.getData()).hasSize(0);
            assertThat(locations.getRequests()).isEqualTo(1);

            // Test RestTemplate usage
            Mockito.verify(restTemplate, VerificationModeFactory.times(1)).getForObject("https://api.ipma.pt/open-data/distrits-islands.json", LocationsList.class);
        }

        @Test
        public void whenGetDetails_returnObject() {
            // Call service
            Entity<Optional<Location>> location = locationService.getLocationDetails(1);

            // Test response
            assertThat(location.getData().isEmpty()).isFalse();
            assertThat(location.getData().get().getName()).isEqualTo("Aveiro");
            assertThat(location.getRequests()).isEqualTo(1);

            // Test RestTemplate usage
            Mockito.verify(restTemplate, VerificationModeFactory.times(1)).getForObject("https://api.ipma.pt/open-data/distrits-islands.json", LocationsList.class);
        }

        @Test
        public void whenNotCache_getFromAPI() {
            // Call service
            Entity<List<Location>> locations = locationService.getAllLocations();

            // Test response
            assertThat(locations.getCacheHits()).isEqualTo(0);
            assertThat(locations.getCacheMisses()).isEqualTo(1);
            assertThat(locations.getCacheSize()).isEqualTo(1);
            assertThat(locations.getRequests()).isEqualTo(1);
            assertThat(locations.getCacheExpired()).isEqualTo(0);

            // Test RestTemplate usage
            Mockito.verify(restTemplate, VerificationModeFactory.times(1)).getForObject("https://api.ipma.pt/open-data/distrits-islands.json", LocationsList.class);
        }

        @Test
        public void whenCache_getFromCache() {
            // Call service
            Entity<List<Location>> locations = locationService.getAllLocations();
            locations = locationService.getAllLocations();
            locations = locationService.getAllLocations();

            // Test response
            assertThat(locations.getCacheHits()).isEqualTo(2);
            assertThat(locations.getCacheMisses()).isEqualTo(1);
            assertThat(locations.getCacheSize()).isEqualTo(1);
            assertThat(locations.getRequests()).isEqualTo(3);
            assertThat(locations.getCacheExpired()).isEqualTo(0);

            // Test RestTemplate usage
            Mockito.verify(restTemplate, VerificationModeFactory.times(1)).getForObject("https://api.ipma.pt/open-data/distrits-islands.json", LocationsList.class);
        }

        @Test
        public void whenCacheExpired_getFromAPI() throws Exception {
            // Call service
            Entity<List<Location>> locations = locationService.getAllLocations();

            // wait for TTL to exceed
            TimeUnit.SECONDS.sleep(Cache.DEFAULTTTL+1);

            // Call service again
            locations = locationService.getAllLocations();

            // Test response
            assertThat(locations.getCacheHits()).isEqualTo(0);
            assertThat(locations.getCacheMisses()).isEqualTo(2);
            assertThat(locations.getCacheSize()).isEqualTo(1);
            assertThat(locations.getRequests()).isEqualTo(2);
            assertThat(locations.getCacheExpired()).isEqualTo(1);
        }

    }

    @Nested
    class NegativeAnswer {

        @BeforeEach
        public void setUp() {
            // Mock real API
            when(restTemplate.getForObject(LocationService.API_URL, LocationsList.class)).thenReturn(new LocationsList(Arrays.asList()));
        }

        @Test
        public void whenGetAll_thenReturnEmptyList() {
            // Call service
            Entity<List<Location>> locations = locationService.getAllLocations();

            // Test response
            assertThat(locations.getData()).hasSize(0);
            assertThat(locations.getRequests()).isEqualTo(1);

            // Test RestTemplate usage
            Mockito.verify(restTemplate, VerificationModeFactory.times(1)).getForObject("https://api.ipma.pt/open-data/distrits-islands.json", LocationsList.class);
        }

        @Test
        public void whenGetByName_thenReturnMatch() {
            // Call service
            Entity<List<Location>> locations = locationService.getLocationsByNameMatch("Brag");

            // Test response
            assertThat(locations.getData()).hasSize(0);
            assertThat(locations.getRequests()).isEqualTo(1);

            // Test RestTemplate usage
            Mockito.verify(restTemplate, VerificationModeFactory.times(1)).getForObject("https://api.ipma.pt/open-data/distrits-islands.json", LocationsList.class);
        }

        @Test
        public void whenGetDetails_returnObject() {
            // Call service
            Entity<Optional<Location>> location = locationService.getLocationDetails(1);

            // Test response
            assertThat(location.getData().isEmpty()).isTrue();
            assertThat(location.getRequests()).isEqualTo(1);

            // Test RestTemplate usage
            Mockito.verify(restTemplate, VerificationModeFactory.times(1)).getForObject("https://api.ipma.pt/open-data/distrits-islands.json", LocationsList.class);
        }

    }



}
