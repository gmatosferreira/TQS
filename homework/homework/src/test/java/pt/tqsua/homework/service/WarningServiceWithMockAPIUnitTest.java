package pt.tqsua.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.Warning;
import pt.tqsua.homework.model.enums.AwarenessLevel;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WarningServiceWithMockAPIUnitTest {

    @Mock(lenient = true)
    private RestTemplate restTemplate;

    @InjectMocks
    private WarningService service;

    @Nested
    class PositiveAnswer {

        @BeforeEach
        public void setUp() {
            // Create objects
            Warning[] ws = new Warning[2];
            ws[0] = new Warning("Agitação Marítima", "Ondas altas", new Timestamp(1546344000000L).toLocalDateTime(), new Timestamp(1546516800000L).toLocalDateTime(), AwarenessLevel.RED, "BGC");
            ws[1] = new Warning("Nevoeiro", "Ondas altas", new Timestamp(1546344000000L).toLocalDateTime(), new Timestamp(1546516800000L).toLocalDateTime(), AwarenessLevel.ORANGE, "AVR");

            // Mock real API
            when(restTemplate.getForObject(WarningService.API_URL, Warning[].class)).thenReturn(ws);
        }

        @Test
        public void whenGetAll_thenReturnList() {
            // Call service
            Entity<List<Warning>> response = service.getAllWarnings();

            // Test response
            assertThat(response.getData())
                    .hasSize(2)
                    .extracting(Warning::getLevel)
                    .containsAnyElementsOf(Arrays.asList(AwarenessLevel.GREEN.toString(), AwarenessLevel.ORANGE.toString(), AwarenessLevel.RED.toString(), AwarenessLevel.YELLOW.toString()));
            assertThat(response.getData())
                    .extracting(Warning::getLocation)
                    .containsOnly("BGC", "AVR");

        }

        @Test
        public void whenGetForLocation_thenReturnList() {
            // Call service
            Entity<List<Warning>> response = service.getLocationWarnings("BGC");

            // Test response
            assertThat(response.getData())
                    .hasSize(1)
                    .extracting(Warning::getLevel)
                    .containsAnyElementsOf(Arrays.asList(AwarenessLevel.GREEN.toString(), AwarenessLevel.ORANGE.toString(), AwarenessLevel.RED.toString(), AwarenessLevel.YELLOW.toString()));
            assertThat(response.getData())
                    .extracting(Warning::getLocation)
                    .containsOnly("BGC");

        }
    }

    @Nested
    class NegativeAnswer {

        @BeforeEach
        public void setUp() {
            // Mock real API
            when(restTemplate.getForObject(WarningService.API_URL, Warning[].class)).thenReturn(new Warning[0]);
        }

        @Test
        public void whenGetAll_thenReturnEmptyList() {
            // Call service
            Entity<List<Warning>> response = service.getAllWarnings();

            // Test response
            assertThat(response.getData())
                    .hasSize(0);

        }

        @Test
        public void whenGetForLocation_thenReturnEmptyList() {
            // Call service
            Entity<List<Warning>> response = service.getLocationWarnings("BGC");

            // Test response
            assertThat(response.getData())
                    .hasSize(0);

        }
    }
}
