package pt.tqsua.homework.service;

import org.junit.jupiter.api.Test;
import pt.tqsua.homework.model.Entity;
import pt.tqsua.homework.model.Location;
import pt.tqsua.homework.model.Warning;
import pt.tqsua.homework.model.enums.AwarenessLevel;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WarningServiceWithAPIUnitTest {

    private WarningService service = new WarningService();

    @Test
    public void whenGetAll_thenReturnList() {
        // Call service
        Entity<List<Warning>> response = service.getAllWarnings();

        // Test response
        // It is possible that there are no warnings
        if (response.getData().size()>0) {
            assertThat(response.getData())
                .extracting(Warning::getLevel)
                .containsAnyElementsOf(Arrays.asList(AwarenessLevel.green, AwarenessLevel.orange, AwarenessLevel.red, AwarenessLevel.yellow));
        }
    }

    @Test
    public void whenGetForLocation_thenReturnList() {
        // Call service
        Entity<List<Warning>> response = service.getLocationWarnings("BGC");

        // Test response
        // It is possible that there are no warnings
        if (response.getData().size()>0) {
            assertThat(response.getData())
                .extracting(Warning::getLevel)
                .containsAnyElementsOf(Arrays.asList(AwarenessLevel.green, AwarenessLevel.orange, AwarenessLevel.red, AwarenessLevel.yellow));
            assertThat(response.getData())
                .extracting(Warning::getLocation)
                .containsOnly("BGC");
        }

    }
}
