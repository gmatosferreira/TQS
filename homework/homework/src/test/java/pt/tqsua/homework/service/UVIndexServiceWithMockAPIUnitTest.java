package pt.tqsua.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.UVIndex;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UVIndexServiceWithMockAPIUnitTest {

    private static final Logger log = LoggerFactory.getLogger(UVIndexServiceWithMockAPIUnitTest.class);

    @Mock(lenient = true)
    private RestTemplate restTemplate;

    @InjectMocks
    private UVIndexService service;

    @Nested
    class PositiveAnswer {

        @BeforeEach
        public void setUp() {
            // Create objects
            Calendar tomorrowCal = Calendar.getInstance();
            tomorrowCal.setTime(new Date());
            tomorrowCal.add(Calendar.DATE, 1);
            Date tomorrow = tomorrowCal.getTime();
            UVIndex[] indexes = new UVIndex[3];
            indexes[0] = new UVIndex(123, 12, 16, new Date(), 5.6);
            indexes[0].setIndex(indexes[0].getIndex());
            indexes[1] = new UVIndex(123, 12, 16, tomorrow, 5.8);
            indexes[1].setIndex(indexes[1].getIndex());
            indexes[2] = new UVIndex(456, 12, 16, new Date(), 7);
            indexes[2].setIndex(indexes[2].getIndex());

            // Mock real API
            when(restTemplate.getForObject(UVIndexService.APIURL, UVIndex[].class)).thenReturn(indexes);
        }

        @Test
        void whenGetAll_thenReturnList() {
            // Call service
            Entity<List<UVIndex>> response = service.getAllIndexes();

            for(UVIndex u:response.getData()) {
                log.debug(u.toString());
            }

            // Test response
            assertThat(response.getData())
                .hasSize(3)
                .extracting(UVIndex::getLocation)
                .containsOnly(123,456);
        }

        @Test
        void whenGetByExistentLocation_thenReturnMatch() {
            // Call service
            Entity<List<UVIndex>> response = service.getLocationIndex(123);

            // Test response
            assertThat(response.getData())
                .hasSize(2)
                .extracting(UVIndex::getLocation)
                .containsOnly(123);
            assertThat(response.getData().stream().allMatch(index -> (index.isDay(0)||index.isDay(1)))).isTrue();
        }

        @Test
        void whenGetByExistentLocationAndIndex_thenReturnMatch() {
            // Call service
            Entity<List<UVIndex>> response = service.getLocationIndexByDay(123, 0);

            // Test response
            assertThat(response.getData())
                .hasSize(1)
                .extracting(UVIndex::getIndex)
                .containsOnly(5.6);
            assertThat(response.getData())
                .extracting(UVIndex::getIndexClass)
                .containsOnly("Moderado");
        }

        @Test
        void whenGetByInexistentLocation_thenReturnEmpty() {
            // Call service
            Entity<List<UVIndex>> response = service.getLocationIndex(999);

            // Test response
            assertThat(response.getData())
                .isEmpty();
        }

        @Test
        void whenGetByInexistentIndex_thenReturnEmpty() {
            // Call service
            Entity<List<UVIndex>> response = service.getLocationIndexByDay(123, 2);

            // Test response
            assertThat(response.getData())
                .isEmpty();
        }
    }

    @Nested
    class NegativeAnswer {

        @BeforeEach
        public void setUp() {
            // Mock real API
            when(restTemplate.getForObject(UVIndexService.APIURL, UVIndex[].class)).thenReturn(new UVIndex[0]);
        }

        @Test
        void whenGetAll_thenReturnEmptyList() {
            // Call service
            Entity<List<UVIndex>> response = service.getAllIndexes();
            // Test response
            assertThat(response.getData())
                .isEmpty();
        }


    }










}
