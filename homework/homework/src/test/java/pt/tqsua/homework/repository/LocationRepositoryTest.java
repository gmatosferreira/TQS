package pt.tqsua.homework.repository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.tqsua.homework.model.Location;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class LocationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void givenLocations_whenFindAll_thenReturnList() {
        // Create objects and persist them on db
        entityManager.persistAndFlush(new Location(1, "AVR", "Aveiro", "1.234", "5.678"));
        entityManager.persistAndFlush(new Location(2, "BRG", "Braga", "1.234", "5.678"));

        // Call repository
        List<Location> locations = locationRepository.findAll();

        // Validate response
        assertThat(locations)
            .hasSize(2)
            .extracting(Location::getName)
            .contains("Aveiro", "Braga");
    }

    @Test
    public void notGivenLocations_whenFindAll_thenReturnEmptyList() {
        // Call repository
        List<Location> locations = locationRepository.findAll();

        // Validate response
        assertThat(locations)
                .hasSize(0);
    }

    @Test
    public void whenGetNameMatch_thenReturnList() {
        // Create objects and persist them on db
        entityManager.persistAndFlush(new Location(1, "AVR", "Aveiro", "1.234", "5.678"));
        entityManager.persistAndFlush(new Location(2, "BRG", "Braga", "1.234", "5.678"));
        entityManager.persistAndFlush(new Location(3, "BGC", "Bragança", "5.698", "-5.369"));

        // Call repository
        List<Location> locations = locationRepository.findByNameContains("Brag");

        // Validate response
        assertThat(locations)
                .hasSize(2)
                .extracting(Location::getName)
                .contains("Bragança", "Braga");
    }

    @Test
    public void whenGetNameDontMatch_thenReturnEmptyList() {
        // Call repository
        List<Location> locations = locationRepository.findByNameContains("Port");

        // Validate response
        assertThat(locations)
                .hasSize(0);
    }

    @Test
    public void givenLocation_whenGetLocation_thenReturnLocation() {
        // Create objects and persist them on db
        entityManager.persistAndFlush(new Location(1, "AVR", "Aveiro", "1.234", "5.678"));

        // Call repository
        Optional<Location> location = locationRepository.findById(1);

        // Validate response
        assertThat(location.isEmpty()).isFalse();
        assertThat(location.get().getName()).isEqualTo("Aveiro");
    }

    @Test
    public void whenGetInexistentLocation_thenReturnEmpty() {
        // Call repository
        Optional<Location> location = locationRepository.findById(999);

        // Validate response
        assertThat(location.isEmpty()).isTrue();
    }

}
