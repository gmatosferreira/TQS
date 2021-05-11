package pt.tqsua.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarService_UnitTest {

    @Mock(lenient = true)
    CarRepository carRepository;

    @InjectMocks
    CarManagerService carManagerService;

    @BeforeEach
    public void setUp() {
        // Mock repository
        Car c1 = new Car(123L, "BMW", "i3");
        Car c2 = new Car(124L, "Renault", "Zoe");
        Car c3 = new Car(125L, "VW", "e-Golf");

        when(carRepository.findAll()).thenReturn(Arrays.asList(c1, c2, c3));
        when(carRepository.findById(999L)).thenReturn(Optional.empty());
        when(carRepository.findById(c1.getId())).thenReturn(Optional.of(c1));
    }

    @Test
    public void whenValidId_thenShouldBeReturned() {
        // Call service
        Optional<Car> car = carManagerService.getCarDetails(123L);

        // Test response
        assertThat(car.isPresent()).isTrue();
        assertThat(car.get().getMaker()).isEqualTo("BMW");

        // Test repository usage
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findById(123L);
    }

    @Test
    public void whenInvalidId_thenShouldReturnNull() {
        // Call service
        Optional<Car> car = carManagerService.getCarDetails(999L);

        // Test response
        assertThat(car.isEmpty()).isTrue();

        // Test repository usage
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findById(999L);
    }

    @Test
    public void whenNotCars_getempty() {
        // Override repository mocking
        when(carRepository.findAll()).thenReturn(new ArrayList<Car>());

        // Call service
        List<Car> cars = carManagerService.getAllCars();

        // Test response
        assertThat(cars).hasSize(0);

        // Test repository usage
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }

    @Test
    public void whenCarsList_getAll() {
        // Call service
        List<Car> cars = carManagerService.getAllCars();

        // Test response
        assertThat(cars).hasSize(3).extracting(Car::getMaker).contains("BMW", "Renault", "VW");

        // Test repository usage
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }


}
