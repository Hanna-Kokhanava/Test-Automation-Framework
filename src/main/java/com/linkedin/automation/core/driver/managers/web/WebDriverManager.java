package com.linkedin.automation.core.driver.managers.web;

import com.linkedin.automation.core.browser.BrowserManager;
import com.linkedin.automation.core.driver.SupportedPlatforms;
import com.linkedin.automation.core.tools.files.FileManager;
import com.linkedin.automation.core.tools.files.PropertyLoader;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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

    public static void createDriver(DesiredCapabilities capabilities) {
        String platformName = PropertyLoader.get(PropertyLoader.BrowserProperty.BROWSER_TYPE).toUpperCase();
        SupportedPlatforms platform = SupportedPlatforms.valueOf(platformName);
        MutableCapabilities options = platform.getOptions().merge(capabilities);
        getDriverExecutablePath();

        if (Objects.isNull(driver)) {
            switch (platform) {
                case CHROME:
                    //TODO set system property
//                    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, getDriverExecutablePath());
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

    /**
     * Close web driver
     */
    public static void closeDriver() {
        if (Objects.nonNull(driver)) {
            driver.quit();
            driver = null;
        }
    }

    private static void getDriverExecutablePath() {
        //TODO get information from driver-repositories.xml -> filepath - driver id + version id + .zip
        FileManager.getInstance(BrowserManager.getCurrentBrowser().getHost())
                .downloadFileFromUrl("http://chromedriver.storage.googleapis.com/2.25/chromedriver_win32.zip", "drivers/chromedriver.zip");
    }
}
