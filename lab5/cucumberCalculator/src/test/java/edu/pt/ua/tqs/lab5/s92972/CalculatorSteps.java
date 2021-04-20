package edu.pt.ua.tqs.lab5.s92972;

import io.cucumber.java.an.Dada;
import io.cucumber.java.it.Quando;
import io.cucumber.java.pt.Entao;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorSteps {

    private Calculator c;

    // Pre-conditions
    @Dada("uma calculadora que acabei de ligar")
    public void setup() {
        c = new Calculator();
    }

    // Events or Actions
    @Quando("eu adiciono {int} e {int}")
    public void adiciono(Integer int1, Integer int2) {
        c.push(int1);
        c.push(int2);
        c.push("+");
    }

    @Quando("eu subtraio {int} a {int}")
    public void subtraio(Integer int1, Integer int2) {
        c.push(int2);
        c.push(int1);
        c.push("-");
    }

    // Expected outcomes (test)
    @Entao("o resultado é {float}")
    public void the_result_is(Float float1) {
        assertEquals(Double.parseDouble(float1.toString()), c.value());
    }
}
