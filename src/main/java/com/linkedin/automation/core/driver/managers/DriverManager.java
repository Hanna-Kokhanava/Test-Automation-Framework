package com.linkedin.automation.core.driver.managers;

import com.linkedin.automation.core.tools.HostMachine;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created on 10.03.2018
 */
public class DriverManager {
    private static final ThreadLocal<AppiumDriver> driverPool = new ThreadLocal<>();
    private static final ThreadLocal<HostMachine> driverHost = new ThreadLocal<>();

    private DriverManager() {
    }

    public static AppiumDriver getDriver() {
        return driverPool.get();
    }

    public static AndroidDriver getAndroidDriver() {
        return (AndroidDriver) driverPool.get();
    }


    public static void createDriver(HostMachine host, DesiredCapabilities capabilities) throws Exception {
        AppiumDriver driver;
        try {
            driver = new AndroidDriver(
                    AppiumServerManager.getService().getUrl(), capabilities);
        } catch (Exception e) {
            throw e;
        }
        driverHost.set(host);
        driverPool.set(driver);
        driverPool.get().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    public static void closeDriver() {
        WebDriver driverInstance = driverPool.get();
        try {
            if (driverInstance != null) {
                driverPool.remove();
                driverHost.remove();
                driverInstance.quit();
            }
        } catch (WebDriverException e) {
            e.getStackTrace();
        }
    }
}
