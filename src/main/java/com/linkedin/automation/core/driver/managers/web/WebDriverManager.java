package com.linkedin.automation.core.driver.managers.web;

import com.linkedin.automation.core.driver.SupportedPlatforms;
import com.linkedin.automation.core.tools.files.PropertyLoader;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created on 21.01.2019
 */
public class WebDriverManager {

    private static final long IMPLICIT_WAIT_TIMEOUT = 5;
    private static WebDriver driver;

    public static void createDriver() {
        String platformName = PropertyLoader.get(PropertyLoader.BrowserProperty.BROWSER_TYPE).toUpperCase();
        SupportedPlatforms platform = SupportedPlatforms.valueOf(platformName);
        MutableCapabilities options = platform.getOptions();

        if (Objects.isNull(driver)) {
            switch (platform) {
                case CHROME:

                    driver = new ChromeDriver((ChromeOptions) options);
                    break;
                case FIREFOX:
                    driver = new FirefoxDriver((FirefoxOptions) options);
                    break;
                case IE10:
                    driver = new InternetExplorerDriver((InternetExplorerOptions) options);
                    break;
                default:
                    throw new IllegalStateException("Cannot create appropriate driver based on configuration information");
            }
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
        } else {
            //TODO while async is not implemented
            throw new IllegalStateException("Driver has already been initialized");
        }
    }

    /**
     * Close driver
     */
    public static void closeDriver() {
        if (Objects.nonNull(driver)) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Returns WebDriver instance
     *
     * @return {@link WebDriver} instance or throws IllegalStateException if driver has not been initialized
     */
    public static WebDriver getDriver() {
        if (driver != null) {
            return driver;
        } else {
            throw new IllegalStateException("Driver hasn't been initialized yet. createDriver() should be called first");
        }
    }
}
