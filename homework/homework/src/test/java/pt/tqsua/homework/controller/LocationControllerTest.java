package pt.tqsua.homework.controller;

import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.service.LocationService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocationController.class)
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    @Test
    void givenLocations_whenGetLocations_thenReturnJsonArray() throws Exception {
        // Create objects
        Location l1 = new Location(1, "AVR", "Aveiro", "1.234", "5.678");
        Location l2 = new Location(2, "BJA", "Beja", "5.698", "-5.369");

        // Mock service
        when(locationService.getAllLocations()).thenReturn(new Entity<List<Location>>(Arrays.asList(l1, l2), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get("/api/locations").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(2)))
                .andExpect(jsonPath("data[0].name", is(l1.getName())))
                .andExpect(jsonPath("data[1].name", is(l2.getName())));

        // Validate service usage
        verify(locationService, VerificationModeFactory.times(1)).getAllLocations();
    }

    @Test
    void notGivenLocations_whenGetLocations_thenReturnEmptyJsonArray() throws Exception {
        // Mock service
        when(locationService.getAllLocations()).thenReturn(new Entity<List<Location>>(Arrays.asList(), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get("/api/locations").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(0)));

        // Validate service usage
        verify(locationService, VerificationModeFactory.times(1)).getAllLocations();
    }

    @Test
    void givenLocations_whenGetNameMatch_thenReturnJsonArray() throws Exception {
        String nameMatch = "Brag";
        // Create objects
        Location l1 = new Location(1, "BRG", "Braga", "1.234", "5.678");
        Location l2 = new Location(2, "BGC", "Bragança", "5.698", "-5.369");

        // Mock service
        when(locationService.getLocationsByNameMatch(nameMatch)).thenReturn(new Entity<List<Location>>(Arrays.asList(l1, l2), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/locations/search/%s", nameMatch)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(2)))
                .andExpect(jsonPath("data[0].name", is(l1.getName())))
                .andExpect(jsonPath("data[1].name", is(l2.getName())));

        // Validate service usage
        verify(locationService, VerificationModeFactory.times(1)).getLocationsByNameMatch(nameMatch);
    }

    @Test
    void whenGetNameDontMatch_thenReturnJsonEmptyArray() throws Exception {
        String nameMatch = "Av";

        // Mock service
        when(locationService.getLocationsByNameMatch(nameMatch)).thenReturn(new Entity<List<Location>>(Arrays.asList(), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/locations/search/%s", nameMatch)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(0)));

        // Validate service usage
        verify(locationService, VerificationModeFactory.times(1)).getLocationsByNameMatch(nameMatch);
    }

    @Test
    void givenLocation_whenGetLocation_thenReturnJsonObject()  throws Exception {
        // Create object
        Location l1 = new Location(1, "AVR", "Aveiro", "1.234", "5.678");

        // Mock service
        when(locationService.getLocationDetails(l1.getId())).thenReturn(new Entity<Optional<Location>>(Optional.of(l1), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/locations/%d", l1.getId())).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("data.name", is(l1.getName())))
                .andExpect(jsonPath("data.idArea", is(l1.getIdArea())))
                .andExpect(jsonPath("data.latitude", is(l1.getLatitude())))
                .andExpect(jsonPath("data.longitude", is(l1.getLongitude())));

        // Validate service usage
        verify(locationService, VerificationModeFactory.times(1)).getLocationDetails(l1.getId());
    }

    @Test
    void whenGetInexistentLocation_thenReturnNotFound()  throws Exception {
        int id = 1;

        // Mock service
        when(locationService.getLocationDetails(id)).thenReturn(new Entity<Optional<Location>>(Optional.empty(), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/locations/%d", id)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Validate service usage
        verify(locationService, VerificationModeFactory.times(1)).getLocationDetails(id);
    }



}
