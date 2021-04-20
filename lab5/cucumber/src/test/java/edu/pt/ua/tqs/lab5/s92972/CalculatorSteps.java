package edu.pt.ua.tqs.lab5.s92972;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorSteps {

    private Calculator c;

    // Pre-conditions
    @Given("a calculator I just turned on")
    public void setup() {
        c = new Calculator();
    }

    // Events or Actions
    @When("I add {int} and {int}")
    public void i_add_and(Integer int1, Integer int2) {
        c.push(int1);
        c.push(int2);
        c.push("+");
    }

    @When("I substract {int} to {int}")
    public void i_substract_to(Integer int1, Integer int2) {
        c.push(int1);
        c.push(int2);
        c.push("-");
    }

    // Expected outcomes (test)
    @Then("the result is {float}")
    public void the_result_is(Float float1) {
        assertEquals(Double.parseDouble(float1.toString()), c.value());
    }
}
