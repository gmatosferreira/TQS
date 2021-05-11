package pt.tqsua.car;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CarControllerTest {

    @Autowired
    MockMvc servlet;

    @MockBean
    CarManagerService service;

    @Test
    public void createCar() throws Exception {
        // Create entities
        Car c1 = new Car(123L, "BMW", "i3");

        // Mock service
        when(service.save(Mockito.any())).thenReturn(c1);

        // Make request to API and validate response
        servlet.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(c1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is(c1.getMaker())))
                .andExpect(jsonPath("$.model", is(c1.getModel())));

        // Validate service usage
        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void getAllCars() throws Exception {
        // Create entities
        Car c1 = new Car(123L, "BMW", "i3");
        Car c2 = new Car(124L, "Renault", "Zoe");
        Car c3 = new Car(125L, "VW", "e-Golf");
        List<Car> allCars = Arrays.asList(c1, c2, c3);

        // Mock service
        given(service.getAllCars()).willReturn(allCars);

        // Make request to API and validate response
        servlet.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].maker", is(c1.getMaker())))
                .andExpect(jsonPath("$[1].maker", is(c2.getMaker())))
                .andExpect(jsonPath("$[2].maker", is(c3.getMaker())))
                .andExpect(jsonPath("$[0].model", is(c1.getModel())))
                .andExpect(jsonPath("$[1].model", is(c2.getModel())))
                .andExpect(jsonPath("$[2].model", is(c3.getModel())));

        // Validate service usage
        verify(service, VerificationModeFactory.times(1)).getAllCars();
    }

    @Test
    void getCarById() throws Exception {
        // Create entities
        Car c1 = new Car(123L, "BMW", "i3");

        // Mock service
        given(service.getCarDetails(c1.getId())).willReturn(Optional.of(c1));

        // Make request to API and validate response
        servlet.perform(get(String.format("/api/cars/%d", c1.getId())).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.maker", is(c1.getMaker())))
                .andExpect(jsonPath("$.model", is(c1.getModel())));

        // Validate service usage
        verify(service, VerificationModeFactory.times(1)).getCarDetails(c1.getId());
    }
}
