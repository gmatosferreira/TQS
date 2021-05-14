package pt.tqsua.homework.controller;

import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.UVIndex;
import pt.tqsua.homework.model.Warning;
import pt.tqsua.homework.model.enums.AwarenessLevel;
import pt.tqsua.homework.service.UVIndexService;
import pt.tqsua.homework.service.WarningService;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UVIndexController.class)
public class UVIndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UVIndexService service;

    @Test
    public void givenIndexes_whenGetAll_thenReturnJsonArray() throws Exception {
        // Create objects
        Calendar tomorrowCal = Calendar.getInstance();
        tomorrowCal.setTime(new Date());
        tomorrowCal.add(Calendar.DATE, 1);
        Date tomorrow = tomorrowCal.getTime();
        UVIndex u1 = new UVIndex(123, 12, 16, new Date(), 5.6);
        UVIndex u2 = new UVIndex(123, 12, 16, tomorrow, 5.8);
        UVIndex u3 = new UVIndex(456, 12, 16, new Date(), 7);

        // Mock service
        when(service.getAllIndexes()).thenReturn(new Entity<List<UVIndex>>(Arrays.asList(u1, u2, u3), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get("/api/uvindexes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(3)))
                .andExpect(jsonPath("data[0].index", is(u1.getIndex())))
                .andExpect(jsonPath("data[1].index", is(u2.getIndex())))
                .andExpect(jsonPath("data[2].index", is(u3.getIndex())));

        // Validate service usage
        verify(service, VerificationModeFactory.times(1)).getAllIndexes();
    }

    @Test
    public void notGivenIndexes_whenGetAll_thenReturnEmptyJsonArray() throws Exception {
        // Mock service
        when(service.getAllIndexes()).thenReturn(new Entity<List<UVIndex>>(Arrays.asList(), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get("/api/uvindexes").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("data", hasSize(0)));

        // Validate service usage
        verify(service, VerificationModeFactory.times(1)).getAllIndexes();
    }

    @Test
    public void givenIndexes_whenGetLocation_thenReturnJsonArray() throws Exception {
        int location = 123;
        // Create objects
        Calendar tomorrowCal = Calendar.getInstance();
        tomorrowCal.setTime(new Date());
        tomorrowCal.add(Calendar.DATE, 1);
        Date tomorrow = tomorrowCal.getTime();
        UVIndex u1 = new UVIndex(123, 12, 16, new Date(), 5.6);
        UVIndex u2 = new UVIndex(123, 12, 16, tomorrow, 5.8);

        // Mock service
        when(service.getLocationIndex(location)).thenReturn(new Entity<List<UVIndex>>(Arrays.asList(u1, u2), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/uvindexes/%d", location)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("data", hasSize(2)))
            .andExpect(jsonPath("data[0].index", is(u1.getIndex())))
            .andExpect(jsonPath("data[0].indexClass", is(u1.getIndexClass())))
            .andExpect(jsonPath("data[1].index", is(u2.getIndex())))
            .andExpect(jsonPath("data[1].indexClass", is(u2.getIndexClass())));

        // Validate service usage
        verify(service, VerificationModeFactory.times(1)).getLocationIndex(location);
    }

    @Test
    public void whenGetLocationDontMatch_thenReturnJsonEmptyArray() throws Exception {
        int location = 123;

        // Mock service
        when(service.getLocationIndex(location)).thenReturn(new Entity<List<UVIndex>>(Arrays.asList(), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/uvindexes/%d", location)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("data", hasSize(0)));

        // Validate service usage
        verify(service, VerificationModeFactory.times(1)).getLocationIndex(location);
    }

    @Test
    public void givenIndexes_whenGetLocationAndDay_thenReturnJsonArray() throws Exception {
        int location = 123;
        int day = 0;
        // Create objects
        UVIndex u1 = new UVIndex(123, 12, 16, new Date(), 5.6);

        // Mock service
        when(service.getLocationIndexByDay(location, day)).thenReturn(new Entity<List<UVIndex>>(Arrays.asList(u1), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/uvindexes/%d/%d", location, day)).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("data", hasSize(1)))
            .andExpect(jsonPath("data[0].index", is(u1.getIndex())))
            .andExpect(jsonPath("data[0].indexClass", is(u1.getIndexClass())));


        // Validate service usage
        verify(service, VerificationModeFactory.times(1)).getLocationIndexByDay(location, day);
    }

    @Test
    public void whenGetLocationAndDayDontMatch_thenReturnJsonEmptyArray() throws Exception {
        int location = 123;
        int day = 0;

        // Mock service
        when(service.getLocationIndexByDay(location, day)).thenReturn(new Entity<List<UVIndex>>(Arrays.asList(), 0, 1, 1, 0));

        // Make request to API and validate response
        mockMvc.perform(get(String.format("/api/uvindexes/%d/%d", location, day)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", hasSize(0)));

        // Validate service usage
        verify(service, VerificationModeFactory.times(1)).getLocationIndexByDay(location, day);
    }

}
