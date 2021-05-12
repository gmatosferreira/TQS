package pt.tqsua.homework.controller;

import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.service.LocationService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    @Test
    public void givenLocations_whenGetLocations_thenReturnJsonArray() throws Exception {
        // Create objects
        Location l1 = new Location(1, "AVR", "Aveiro", "1.234", "5.678");
        Location l2 = new Location(2, "BJA", "Beja", "5.698", "-5.369");

        // Mock service
        when(locationService.getAllLocations()).thenReturn(Arrays.asList(l1, l2));

        // Make request to API and validate response
        mockMvc.perform(get("/api/locations").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(l1.getName())))
                .andExpect(jsonPath("$[1].name", is(l2.getName())));

        // Validate service usage
        verify(locationService, VerificationModeFactory.times(1)).getAllLocations();
    }

    @Test
    public void notGivenLocations_whenGetLocations_thenReturnEmptyJsonArray() throws Exception {
        // Mock service
        when(locationService.getAllLocations()).thenReturn(Arrays.asList());

        // Make request to API and validate response
        mockMvc.perform(get("/api/locations").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        // Validate service usage
        verify(locationService, VerificationModeFactory.times(1)).getAllLocations();
    }

    @Test
    public void givenLocations_whenGetNameMatch_thenReturnJsonArray() throws Exception {
        String nameMatch = "Brag";
        // Create objects
        Location l1 = new Location(1, "BRG", "Braga", "1.234", "5.678");
        Location l2 = new Location(2, "BGC", "Bragança", "5.698", "-5.369");

        // Mock service
        when(locationService.getLocationsByNameMatch(nameMatch)).thenReturn(Arrays.asList(l1, l2));

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/locations/search/%s", nameMatch)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(l1.getName())))
                .andExpect(jsonPath("$[1].name", is(l2.getName())));

        // Validate service usage
        verify(locationService, VerificationModeFactory.times(1)).getLocationsByNameMatch(nameMatch);
    }

    @Test
    public void whenGetNameDontMatch_thenReturnJsonEmptyArray() throws Exception {
        String nameMatch = "Av";

        // Mock service
        when(locationService.getLocationsByNameMatch(nameMatch)).thenReturn(Arrays.asList());

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/locations/search/%s", nameMatch)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        // Validate service usage
        verify(locationService, VerificationModeFactory.times(1)).getLocationsByNameMatch(nameMatch);
    }

    @Test
    public void givenLocation_whenGetLocation_thenReturnJsonObject()  throws Exception {
        // Create object
        Location l1 = new Location(1, "AVR", "Aveiro", "1.234", "5.678");

        // Mock service
        when(locationService.getLocationDetails(l1.getId())).thenReturn(Optional.of(l1));

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/locations/%d", l1.getId())).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.name", is(l1.getName())))
                .andExpect(jsonPath("$.idArea", is(l1.getIdArea())))
                .andExpect(jsonPath("$.latitude", is(l1.getLatitude())))
                .andExpect(jsonPath("$.longitude", is(l1.getLongitude())));

        // Validate service usage
        verify(locationService, VerificationModeFactory.times(1)).getLocationDetails(l1.getId());
    }

    @Test
    public void whenGetInexistentLocation_thenReturnNotFound()  throws Exception {
        int id = 1;

        // Mock service
        when(locationService.getLocationDetails(id)).thenReturn(Optional.empty());

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/locations/%d", id)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // Validate service usage
        verify(locationService, VerificationModeFactory.times(1)).getLocationDetails(id);
    }



}