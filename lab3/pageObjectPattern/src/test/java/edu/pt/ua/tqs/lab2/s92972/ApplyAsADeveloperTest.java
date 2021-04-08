package edu.pt.ua.tqs.lab2.s92972;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplyAsADeveloperTest {
    WebDriver driver;

    @BeforeEach
    public void setup(){
        //use FF Driver
        System.setProperty("webdriver.gecko.driver", "/opt/WebDriver/bin/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void applyAsDeveloper() {
        //Create object of HomePage Class
        HomePage home = new HomePage(driver);
        home.clickOnDeveloperApplyButton();

        //Create object of ApplyPage
        DeveloperApplyPage developerApplyPage =new DeveloperApplyPage(driver);

        //Check if page is opened
        assertTrue(developerApplyPage.isPageOpened());

        //Fill up data
        developerApplyPage.setDeveloper_email("dejan@toptal.com");
        developerApplyPage.setDeveloper_full_name("Dejan Zivanovic Automated Test");
        developerApplyPage.setDeveloper_password("password123");
        developerApplyPage.setDeveloper_password_confirmation("password123");

        // Submit
        // applyPage.click_submit();
    }

    @AfterEach
    public void close(){
        driver.close();
    }
}