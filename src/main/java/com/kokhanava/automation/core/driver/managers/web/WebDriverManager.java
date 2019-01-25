package com.kokhanava.automation.core.driver.managers.web;

import com.kokhanava.automation.core.browser.BrowserManager;
import com.kokhanava.automation.core.driver.SupportedWebPlatforms;
import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.files.FileManager;
import com.kokhanava.automation.core.tools.files.property.PropertyLoader;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.kokhanava.automation.core.tools.files.ResultFolder.DRIVERS_FOLDER;

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
                    //TODO change property value constant string to dynamic from getDriverExecutablePath()
                    System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, DRIVERS_FOLDER + File.separator + "chromedriver.exe");
                    driver = new ChromeDriver((ChromeOptions) options);
                    break;
                case FIREFOX:
                    //TODO set system property with driver exe path
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

    /**
     * Download and unzip an appropriate driver
     *
     * @param driverName name of required driver
     * @return driver executable file
     */
    //TODO Frozen for some time while problem with "https" certificates while downloading
    private static String getDriverExecutablePath(String driverName) {
        HostMachine host = BrowserManager.getCurrentBrowser().getHost();
        FileManager fileManager = FileManager.getInstance(host);

        //TODO get information from driver-repositories.xml -> filepath - driver id + version id + .zip
        String fileName = "chromedriver" + ".zip";
        String zipFilePath = DRIVERS_FOLDER + File.separator + fileName;

        if (!fileManager.isFileExist(DRIVERS_FOLDER, new File(zipFilePath), fileName)) {
            fileManager.downloadFileFromUrl("http://chromedriver.storage.googleapis.com/2.25/chromedriver_win32.zip", zipFilePath);
            fileManager.unzipFile(zipFilePath, DRIVERS_FOLDER.getPathToFolder(host));
        }

        //TODO need to implement "driver id... exe" file searching
        return DRIVERS_FOLDER + File.separator;
    }
}
