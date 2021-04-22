package edu.pt.ua.tqs.lab5.s92972;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlazeDemoSteps {

    private WebDriver driver;
    JavascriptExecutor js;

    // Start Selenium
    public BlazeDemoSteps() {
        System.setProperty("webdriver.gecko.driver", "/opt/WebDriver/bin/geckodriver");
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
    }

    // BeforeAll()
    @Given("I am at {string} website")
    public void i_am_at_website(String string) {
        driver.get(string);
        driver.manage().window().setSize(new Dimension(1036, 940));
    }

    // Actions
    @When("I search a trip from {string} to {string}")
    public void i_search_a_trip_from_to(String string, String string2) {
        driver.findElement(By.name("fromPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = '"+string+"']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(3)")).click();
        driver.findElement(By.name("toPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = '"+string2+"']")).click();
        }
    }

    @When("I click the result number {int}")
    public void i_click_the_result_number(Integer int1) {
        driver.findElement(By.cssSelector(String.format("tr:nth-child(%d) .btn", int1))).click();
    }

    @When("I fill in {string} with {string}")
    public void i_fill_in_with(String string, String string2) {
        // xpath based on https://www.guru99.com/xpath-selenium.html#16
        driver.findElement(By.xpath("//*[contains(text(),'" + string + "')]//parent::div//descendant::input")).sendKeys(string2);;
    }

    @When("I submit the form")
    public void i_submit_the_form() {
        driver.findElement(By.xpath("//form//descendant::input[@type='submit']")).click();;
    }

    // Excepected results
    @Then("the page heading must have the text {string}")
    public void the_page_heading_must_have_the_text(String string) {
        assertEquals(string, driver.findElement(By.cssSelector("h1:first-of-type")).getText(), "Error: Page heading does not have expected text!");
    }

    @Then("the page must have the title {string}")
    public void the_page_must_have_the_title(String string) {
        assertEquals(string, driver.getTitle(), "Error: Page does not have the expected title!");
    }

    @Then("table must have {int} rows")
    public void table_must_have_rows(Integer int1) {
        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
        assertEquals(int1, rows.size(), "Error: Table does not have expected row number!");
    }

    // After, close driver
    @After
    public void doSomethingAfter(Scenario scenario){
        driver.quit();
    }

}
