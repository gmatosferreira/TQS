package edu.pt.ua.tqs.lab3.s92972;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;


class BlazeDemoTest {

    private WebDriver driver;
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.gecko.driver", "/opt/WebDriver/bin/geckodriver");
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void buyatrip() {
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(1036, 940));
        driver.findElement(By.name("fromPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = 'Boston']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(3)")).click();
        driver.findElement(By.name("toPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = 'New York']")).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(5)")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertEquals(driver.findElement(By.cssSelector("h3:nth-child(1)")).getText(), "Flights from Boston to New York:");
        driver.findElement(By.cssSelector("tr:nth-child(1) .btn")).click();
        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys("David Ferreira");
        driver.findElement(By.id("address")).sendKeys("Main Town St. 3");
        driver.findElement(By.id("city")).sendKeys("Atlanta");
        driver.findElement(By.id("state")).sendKeys("Atlanta");
        driver.findElement(By.id("creditCardMonth")).click();
        driver.findElement(By.id("creditCardMonth")).sendKeys("05");
        driver.findElement(By.id("creditCardYear")).sendKeys("2000");
        driver.findElement(By.id("nameOnCard")).sendKeys("David Ferreira");
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertEquals(driver.findElement(By.cssSelector("h1")).getText(), "Thank you for your purchase today!");
        assertEquals(driver.findElement(By.cssSelector("tr:nth-child(2) > td:nth-child(2)")).getText(), "PendingCapture");
        assertEquals(driver.getTitle(), "BlazeDemo Confirmation");
    }
}