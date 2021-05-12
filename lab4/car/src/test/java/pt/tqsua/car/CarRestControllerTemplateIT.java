package pt.tqsua.car;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @TestPropertySource( locations = "application-integrationtest.properties") // USe a real database
@AutoConfigureTestDatabase // Use in memory database
class CarRestControllerTemplateIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CarRepository carRepository;

    @AfterEach
    public void resetDB() {
        carRepository.deleteAll();
    }

    @Test
    public void whenValidInput_thenCreateCar() {
        // Create entity through controller
        Car c1 = new Car("BMW", "i3");
        ResponseEntity<Car> response = restTemplate.postForEntity("/api/cars", c1, Car.class);
        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMaker()).isEqualTo(c1.getMaker());

        // Call repository to check that it was saved on the db
        List<Car> found = carRepository.findAll();
        // Validate response
        assertThat(found).extracting(Car::getMaker).containsOnly("BMW");
    }

    @Test
    public void givenCars_whenGetCars_thenStatus200()  {
        // Save cars to db through repository
        carRepository.saveAndFlush(new Car("BMW", "i3"));
        carRepository.saveAndFlush(new Car("Renault", "Zoe"));

        // Call controller to get cars
        ResponseEntity<List<Car>> response = restTemplate.exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {});
        // Validate response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getMaker).containsExactly("BMW", "Renault");
    }



}
