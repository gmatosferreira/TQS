package pt.tqsua.homework.views;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ViewSeleniumTest {

    private SeleniumConfig config;

    @LocalServerPort
    int randomServerPort;

    @BeforeEach
    public void setUp() {
        config = new SeleniumConfig();
    }

    @AfterEach
    public void clean() {
        config.getDriver().close();
    }

    @Test
    public void test() {
        ViewObject view = new ViewObject(config.getDriver(), randomServerPort);

        // Validate general stuff
        assertTrue(view.isTitleValid());
        assertTrue(view.onlyLocationsCardVisible());
        assertTrue(view.logsHasText(ViewObject.LOCATIONS_URL));

        // Select city
        view.selectFirstCity();

        // Content validations
        assertTrue(view.allCardsVisible());
        assertTrue(view.validUvBadges());
        assertTrue(view.validAlertBadges());

        // Select uv day for today
        view.selectUVDayForToday();
        assertTrue(view.validUvIndexAlertDateToday());


        // Final general validations
        assertTrue(view.validateCardsTitles());
        assertTrue(view.validateCaches());

    }
}