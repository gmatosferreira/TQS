package pt.tqsua.homework.views;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class ViewObject {

    private WebDriver driver;

    // Context variables
    public static final String URL = "http://127.0.0.1";
    public static final String LOCATIONS_URL = "/api/location/";
    public static final String UVINDEXES_URL = "/api/uvindexes/";
    public static final String ALERTS_URL = "/api/warnings/";

    // Elements
    @FindBy(id = "logs")
    WebElement logs;

    @FindBy(css = "#locations")
    WebElement locationsCard;
    @FindBy(css = "#locations h5.card-title")
    WebElement locationsTitle;
    @FindBy(css = "#locationCacheStats .requests")
    WebElement locationsCacheRequests;
    @FindBy(id = "location-selectized")
    WebElement locationsSelect;
    @FindBy(css = "#locations .selectize-dropdown-content:nth-child(1)")
    WebElement locationsSelectOption;

    @FindBy(css = "#uvindexes")
    WebElement uvAlertsCard;
    @FindBy(css = "#uvindexes h5.card-title")
    WebElement uvAlertsTitle;
    @FindBy(css = "#uvindexesCacheStats .requests")
    WebElement uvAlertsCacheRequests;
    @FindBy(css = "#uvindexes .data .badge")
    WebElement uvAlertsBadges;
    @FindBy(css = "#uvindexes .data p.small")
    WebElement uvAlertsPeriod;
    @FindBy(id = "uvDays-selectized")
    WebElement uvAlertsSelect;
    @FindBy(css = "#uvindexes .selectize-dropdown-content:nth-child(1)")
    WebElement uvAlertsSelectOption;

    @FindBy(css = "#warnings")
    WebElement alertsCard;
    @FindBy(css = "#warnings h5.card-title")
    WebElement alertsTitle;
    @FindBy(css = "#warningsCacheStats .requests")
    WebElement alertsCacheRequests;
    @FindBy(css = "#warnings .data .badge")
    WebElement alertsBadges;

    // Constructor
    public ViewObject(WebDriver driver, int port) {
        this.driver = driver;
        this.driver.get(String.format("%s:%d/", URL, port));
        // Initialize elements
        PageFactory.initElements(this.driver, this);
        // Wait to make request to API and render elements
        try {
            this.waitSomeSeconds();
        } catch (Exception e) {
            // Do nothing
        }
    }

    private void waitSomeSeconds() throws Exception {
        TimeUnit.SECONDS.sleep(2);
    }

    // Actions

    // Validators
    public boolean isTitleValid() {
        return this.driver.getTitle().equals("Meteorology Alerts");
    }

    public boolean onlyLocationsCardVisible() {
        return
            !locationsCard.getAttribute("class").contains("d-none")
            && uvAlertsCard.getAttribute("class").contains("d-none")
            && alertsCard.getAttribute("class").contains("d-none");
    }

    public boolean allCardsVisible() {
        return
            !locationsCard.getAttribute("class").contains("d-none")
            && !uvAlertsCard.getAttribute("class").contains("d-none")
            && !alertsCard.getAttribute("class").contains("d-none");
    }

    public boolean validateCardsTitles() {
        return
            locationsTitle.getText().equals("Alertas meteorológicos")
            && uvAlertsTitle.getText().equals("Índices de ultravioletas")
            && alertsTitle.getText().equals("Alertas em vigor");
    }

    public boolean validateCaches() {
        return
            locationsCacheRequests.getText() != ""
            && uvAlertsCacheRequests.getText() != ""
            && alertsCacheRequests.getText() != ""
            && Integer.parseInt(locationsCacheRequests.getText())>0
            && Integer.parseInt(uvAlertsCacheRequests.getText())>0
            && Integer.parseInt(alertsCacheRequests.getText())>0;
    }

    public boolean logsHasText(String text) {
        return logs.getText().contains(text);
    }

    public boolean validUvBadges() {
        return
            uvAlertsBadges.getText().contains("Baixo")
            || uvAlertsBadges.getText().contains("Moderado")
            ||  uvAlertsBadges.getText().contains("Elevado");
    }

    public boolean validAlertBadges() {
        return
            alertsBadges.getText().contains("Alerta");
    }

    public boolean validUvIndexAlertDateToday() {
        LocalDate today = LocalDate.now();
        StringBuilder todayText = new StringBuilder();
        todayText.append(today.getDayOfMonth());
        todayText.append("/");
        todayText.append(today.getMonthValue());
        return uvAlertsPeriod.getText().contains(todayText.toString());
    }

    // Actions
    public void selectFirstCity() {
        locationsSelect.click();
        locationsSelectOption.click();
        // Wait to make request to API and render elements
        try {
            this.waitSomeSeconds();
        } catch (Exception e) {
            // Do nothing
        }
    }

    public void selectUVDayForToday() {
        uvAlertsSelect.click();
        uvAlertsSelectOption.click();
        // Wait to make request to API and render elements
        try {
            this.waitSomeSeconds();
        } catch (Exception e) {
            // Do nothing
        }
    }

}
