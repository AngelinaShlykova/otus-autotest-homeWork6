package base;

import factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.time.Duration;

public abstract class AbsBaseTest {

    protected WebDriver driver = null;

    @BeforeEach
    public void init() {
        String browser = System.getProperty("browser.type", "chrome");
        String baseUrl = System.getProperty("base.url", "https://otus.home.kartushin.su");
        driver = WebDriverFactory.create(browser);
    }

    @AfterEach
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
