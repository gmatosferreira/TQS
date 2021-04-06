package edu.pt.ua.tqs.lab3.s92972;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    WebDriver browser;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.gecko.driver", "/opt/WebDriver/bin/geckodriver");
        browser = new FirefoxDriver();
    }

    @AfterEach
    void tearDown() {
        browser.close();
    }

    @Test
    public void site_header_is_on_home_page() {
        browser.get("https://www.saucelabs.com");
        WebElement href = browser.findElement(By.xpath("//a[@href='https://accounts.saucelabs.com/']"));
        assertTrue((href.isDisplayed()));
    }
}