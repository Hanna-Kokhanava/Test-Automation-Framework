package com.kokhanava.automation.core.driver.managers.web;

import com.kokhanava.automation.core.driver.SupportedWebPlatforms;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.files.property.PropertyLoader;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.opera.OperaOptions;
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
        var platformName = PropertyLoader.get(PropertyLoader.BrowserProperty.BROWSER_TYPE).toUpperCase();
        var driverVersion = PropertyLoader.get(PropertyLoader.BrowserProperty.DRIVER_VERSION);

        SupportedWebPlatforms platform = SupportedWebPlatforms.valueOf(platformName);
        MutableCapabilities options = platform.getOptions().merge(capabilities);
        var driverExeFilePath = DriverRepositoryManager.getDriverExecutableFilePath(platform.getDriverName(), driverVersion);

        if (Objects.isNull(driver)) {
            switch (platform) {
                case CHROME:
                    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, driverExeFilePath);
                    driver = new ChromeDriver((ChromeOptions) options);
                    break;
                case FIREFOX:
                    System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, driverExeFilePath);
                    driver = new FirefoxDriver((FirefoxOptions) options);
                    break;
                case OPERA:
                    System.setProperty(OperaDriverService.OPERA_DRIVER_EXE_PROPERTY, driverExeFilePath);
                    driver = new OperaDriver((OperaOptions) options);
                    break;
                case EDGE:
                    System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, driverExeFilePath);
                    driver = new EdgeDriver((EdgeOptions) options);
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
