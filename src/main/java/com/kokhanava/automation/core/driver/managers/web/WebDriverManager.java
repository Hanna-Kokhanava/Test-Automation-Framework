package com.kokhanava.automation.core.driver.managers.web;

import com.kokhanava.automation.core.driver.SupportedWebPlatforms;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.files.property.PropertyLoader;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created on 21.01.2019
 */
public class WebDriverManager {

    private static final long IMPLICIT_WAIT_TIMEOUT = 5;
    private static WebDriver driver;

    /**
     * Creates driver depends on current browser type
     *
     * @param capabilities {@link DesiredCapabilities}
     */
    public static void createDriver(DesiredCapabilities capabilities) {
        String platformName = PropertyLoader.get(PropertyLoader.BrowserProperty.BROWSER_TYPE).toUpperCase();
        SupportedWebPlatforms platform = SupportedWebPlatforms.valueOf(platformName);
        MutableCapabilities options = platform.getOptions().merge(capabilities);

        if (Objects.isNull(driver)) {
            switch (platform) {
                case CHROME:
                    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                            DriverRepositoryManager.getDriverExecutableFilePath("chromedriver"));
                    driver = new ChromeDriver((ChromeOptions) options);
                    break;
                case FIREFOX:
                    System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY,
                            DriverRepositoryManager.getDriverExecutableFilePath("geckodriver"));
                    driver = new FirefoxDriver((FirefoxOptions) options);
                    break;
                case IE10:
                    //TODO set system property with driver exe path
                    driver = new InternetExplorerDriver((InternetExplorerOptions) options);
                    break;
                default:
                    throw new IllegalStateException("Cannot create appropriate driver based on configuration information");
            }

            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
        } else {
            //TODO while async is not implemented
            Logger.error("Driver has already been initialized");
            throw new IllegalStateException("Driver has already been initialized");
        }
    }

    /**
     * Returns WebDriver instance
     *
     * @return {@link WebDriver} instance or throws IllegalStateException if driver has not been initialized
     */
    public static WebDriver getDriver() {
        if (Objects.nonNull(driver)) {
            return driver;
        } else {
            Logger.error("Driver hasn't been initialized yet. createDriver() should be called first");
            throw new IllegalStateException("Driver hasn't been initialized yet. createDriver() should be called first");
        }
    }

    /**
     * Close web driver
     */
    public static void closeDriver() {
        if (Objects.nonNull(driver)) {
            driver.quit();
            driver = null;
        }
    }
}
