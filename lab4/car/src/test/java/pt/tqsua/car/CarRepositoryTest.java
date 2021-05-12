package pt.tqsua.car;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    CarRepository carRepository;

    @Test
    public void findByIdTest() {
        // Create object and persist on the db
        Car c1 = new Car("BMW", "i3");
        c1 = entityManager.persistAndFlush(c1); //ensure data is persisted at this point

        // Call repository
        Optional<Car> found = carRepository.findById(c1.getId());
        // Validate response
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get()).isEqualTo(c1);
    }
}
