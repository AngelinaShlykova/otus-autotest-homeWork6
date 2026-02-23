package factory;

import exceptions.BrowserTypeNotSupportedException;
import factory.settings.ChromeDriverSettings;
import factory.settings.ISettings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public class WebDriverFactory {

    public static WebDriver create(String browser) {
        return create(browser, null);
    }

    public static WebDriver create(String browser, Object options) {
        if (browser == null || browser.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }

        String browserName = browser.trim().toLowerCase();

        return switch (browserName) {
            case "chrome" -> createChromeDriver(options);
            case "firefox" -> createFirefoxDriver(options);
            case "edge" -> createEdgeDriver(options);
            case "safari" -> createSafariDriver();
            default -> throw new BrowserTypeNotSupportedException(browser);
        };
    }

    private static WebDriver createChromeDriver(Object options) {
        String browserVersion = System.getProperty("browser.version", "116");

        WebDriverManager.chromedriver()
                .browserVersion(browserVersion)
                .setup();

        if (options instanceof ChromeOptions chromeOptions) {
            return new ChromeDriver(chromeOptions);
        } else {
            ISettings settings = new ChromeDriverSettings();
            return new ChromeDriver((ChromeOptions) settings.settings());
        }
    }

    private static WebDriver createFirefoxDriver(Object options) {
        WebDriverManager.firefoxdriver().setup();

        if (options instanceof FirefoxOptions firefoxOptions) {
            return new FirefoxDriver(firefoxOptions);
        }
        return new FirefoxDriver();
    }

    private static WebDriver createEdgeDriver(Object options) {
        WebDriverManager.edgedriver().setup();

        if (options instanceof EdgeOptions edgeOptions) {
            return new EdgeDriver(edgeOptions);
        }
        return new EdgeDriver();
    }

    private static WebDriver createSafariDriver() {
        return new SafariDriver();
    }
}
