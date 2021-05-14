package pt.tqsua.homework.service;

import org.junit.jupiter.api.Test;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.UVIndex;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UVIndexServiceWithAPIUnitTest {

    private UVIndexService service = new UVIndexService();

    @Test
    void whenGetAll_thenReturnList() {
        // Call service
        Entity<List<UVIndex>> response = service.getAllIndexes();

        // Test response
        assertThat(response.getData())
            .hasSizeGreaterThan(18); // At least 18 districts data
    }

    @Test
    void whenGetByExistentLocation_thenReturnMatch() {
        // Call service
        Entity<List<UVIndex>> response = service.getLocationIndex(3480200);

        // Test response
        assertThat(response.getData())
            .hasSizeGreaterThan(1)
            .extracting(UVIndex::getLocation)
            .containsOnly(3480200);
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
    void whenGetByExistentLocationAndIndex_thenReturnMatch() {
        // Call service
        Entity<List<UVIndex>> response = service.getLocationIndexByDay(3480200, 0);

        // Test response
        assertThat(response.getData())
            .hasSizeGreaterThanOrEqualTo(1)
            .extracting(UVIndex::getLocation)
            .containsOnly(3480200);
        assertThat(response.getData().stream().allMatch(index -> index.isDay(0))).isTrue();
    }

    @Test
    void whenGetByInexistentIndex_thenReturnEmpty() {
        // Call service
        Entity<List<UVIndex>> response = service.getLocationIndexByDay(3480200, 5);

        // Test response
        assertThat(response.getData())
            .isEmpty();
    }

}
