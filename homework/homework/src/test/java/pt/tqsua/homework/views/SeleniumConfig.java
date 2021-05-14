package pt.tqsua.homework.views;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

/**
 * Based on https://www.baeldung.com/java-selenium-with-junit-and-testng
 */
public class SeleniumConfig {

  private WebDriver driver;

  public SeleniumConfig() {
    Capabilities capabilities = DesiredCapabilities.firefox();
    driver = new FirefoxDriver(capabilities);
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
  }

  static {
    System.setProperty("webdriver.gecko.driver", "/opt/WebDriver/bin/geckodriver");
  }

  public WebDriver getDriver() {
    return driver;
  }
}
