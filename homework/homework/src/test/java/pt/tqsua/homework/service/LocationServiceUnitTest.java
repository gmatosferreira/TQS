package pt.tqsua.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.repository.LocationRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LocationServiceUnitTest {

    @Mock(lenient = true)
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    public void setUp() {
        // Create objects
        Location l1 = new Location(1, "AVR", "Aveiro", "1.234", "5.678");
        Location l2 = new Location(2, "BRG", "Braga", "1.234", "5.678");
        Location l3 = new Location(3, "BGC", "Bragança", "5.698", "-5.369");

        // Mock repository
        when(locationRepository.findAll()).thenReturn(Arrays.asList(l1, l2, l3));
        when(locationRepository.findByNameContains("Brag")).thenReturn(Arrays.asList(l2, l3));
        when(locationRepository.findByNameContains("Por")).thenReturn(Arrays.asList());
        when(locationRepository.findById(l1.getId())).thenReturn(Optional.of(l1));
        when(locationRepository.findById(5)).thenReturn(Optional.empty());
    }

    @Test
    public void givenLocations_whenGetAll_thenReturnList() {
        // Call service
        List<Location> locations = locationService.getAllLocations();

        // Test response
        assertThat(locations)
            .hasSize(3)
            .extracting(Location::getName)
            .contains("Aveiro", "Braga", "Bragança");

        // Test repository usage
        Mockito.verify(locationRepository, VerificationModeFactory.times(1)).findAll();
    }

    @Test
    public void notGivenLocations_whenGetAll_thenReturnEmptyList () {
        // Override mocking
        when(locationRepository.findAll()).thenReturn(Arrays.asList());

        // Call service
        List<Location> locations = locationService.getAllLocations();

        // Test response
        assertThat(locations).hasSize(0);

        // Test repository usage
        Mockito.verify(locationRepository, VerificationModeFactory.times(1)).findAll();
    }

    @Test
    public void whenGetNameMatch_thenReturnList() {
        // Call service
        List<Location> locations = locationService.getLocationsByNameMatch("Brag");

        // Test response
        assertThat(locations)
                .hasSize(2)
                .extracting(Location::getName)
                .contains("Braga", "Bragança");

        // Test repository usage
        Mockito.verify(locationRepository, VerificationModeFactory.times(1)).findByNameContains("Brag");
    }

    @Test
    public void whenGetNameDontMatch_thenReturnEmptyList() {
        // Call service
        List<Location> locations = locationService.getLocationsByNameMatch("Por");

        // Test response
        assertThat(locations)
                .hasSize(0);

        // Test repository usage
        Mockito.verify(locationRepository, VerificationModeFactory.times(1)).findByNameContains("Por");
    }

    @Test
    public void givenLocation_whenGetLocation_thenReturnLocation() {
        // Call service
        Optional<Location> location = locationService.getLocationDetails(1);

        // Test response
        assertThat(location.isEmpty()).isFalse();
        assertThat(location.get().getName()).isEqualTo("Aveiro");

        // Test repository usage
        Mockito.verify(locationRepository, VerificationModeFactory.times(1)).findById(1);
    }

    @Test
    public void whenGetInexistentLocation_thenReturnNotFound() {
        // Call service
        Optional<Location> location = locationService.getLocationDetails(20);

        // Test response
        assertThat(location.isEmpty()).isTrue();

        // Test repository usage
        Mockito.verify(locationRepository, VerificationModeFactory.times(1)).findById(20);
    }



}
