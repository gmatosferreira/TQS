package edu.pt.ua.tqs.lab2.s92972;

import org.junit.Test;
import java.util.ArrayList;
import io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class TodoAPITest {

    @Test
    public void listAllAvailable() {
        given().when().get("https://jsonplaceholder.typicode.com/todos").then().assertThat().statusCode(200);
    }

    @Test
    public void titleOf4th() {
        given().when().get("https://jsonplaceholder.typicode.com/todos").then().body("[3].title", equalTo("et porro tempora"));
        given().when().get("https://jsonplaceholder.typicode.com/todos/4").then().body("title", equalTo("et porro tempora"));
    }

    @Test
    public void listHasIds() {
        ArrayList<Integer> ids = given().when().get("https://jsonplaceholder.typicode.com/todos").then().extract().path("id");
        assertTrue(ids.contains(198));
        assertTrue(ids.contains(199));
    }

}