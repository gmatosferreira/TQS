package pt.tqsua.homework.controller;

import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.Warning;
import pt.tqsua.homework.model.enums.AwarenessLevel;
import pt.tqsua.homework.service.WarningService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WarningController.class)
public class WarningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WarningService service;

    @Test
    public void givenWarnings_whenGetAll_thenReturnJsonArray() throws Exception {
        // Create objects
        Warning w1 = new Warning("Agitação Marítima", "Ondas altas", new Date(1546344000000L), new Date(1546516800000L), AwarenessLevel.red, "BGC");
        Warning w2 = new Warning("Nevoeiro", "Ondas altas", new Date(1546344000000L), new Date(1546516800000L), AwarenessLevel.orange, "AVR");

        // Mock service
        when(service.getAllWarnings()).thenReturn(new Entity<List<Warning>>(Arrays.asList(w1, w2), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get("/api/warnings").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(2)))
                .andExpect(jsonPath("data[0].name", is(w1.getName())))
                .andExpect(jsonPath("data[1].name", is(w2.getName())));

        // Validate service usage
        verify(service, VerificationModeFactory.times(1)).getAllWarnings();
    }

    @Test
    public void notGivenWarnings_whenGetAll_thenReturnEmptyJsonArray() throws Exception {
        // Mock service
        when(service.getAllWarnings()).thenReturn(new Entity<List<Warning>>(Arrays.asList(), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get("/api/warnings").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(0)));

        // Validate service usage
        verify(service, VerificationModeFactory.times(1)).getAllWarnings();
    }

    @Test
    public void givenWarnings_whenGetLocationMatch_thenReturnJsonArray() throws Exception {
        String nameMatch = "AVR";
        // Create objects
        Warning w2 = new Warning("Nevoeiro", "Ondas altas", new Date(1546344000000L), new Date(1546516800000L), AwarenessLevel.orange, "AVR");

        // Mock service
        when(service.getLocationWarnings(nameMatch)).thenReturn(new Entity<List<Warning>>(Arrays.asList(w2), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/warnings/%s", nameMatch)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(1)))
                .andExpect(jsonPath("data[0].name", is(w2.getName())));

        // Validate service usage
        verify(service, VerificationModeFactory.times(1)).getLocationWarnings(nameMatch);
    }

    @Test
    public void whenGetLocationDontMatch_thenReturnJsonEmptyArray() throws Exception {
        String nameMatch = "PTR";

        // Mock service
        when(service.getLocationWarnings(nameMatch)).thenReturn(new Entity<List<Warning>>(Arrays.asList(), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/warnings/%s", nameMatch)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(0)));

        // Validate service usage
        verify(service, VerificationModeFactory.times(1)).getLocationWarnings(nameMatch);
    }

}
