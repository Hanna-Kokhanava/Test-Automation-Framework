package com.linkedin.automation.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created on 10.03.2018
 */
public class DriverManager {
    private static final String WD_SERVER_ROOT = "/wd/hub";
    private static final String LOCAL_ADDRESS = "http://0.0.0.0:4723";

    protected static AppiumDriver driver;
    private static final ThreadLocal<AppiumDriver> driverPool = new ThreadLocal<>();

    public static void createDriver(DesiredCapabilities capabilities) {
        try {
            driver = new AndroidDriver(new URL(LOCAL_ADDRESS + WD_SERVER_ROOT), capabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        driverPool.set(driver);
    }

    public static void closeDriver() {
        WebDriver driverInstance = driverPool.get();
        try {
            if (driverInstance != null) {
                driverPool.remove();
                driverInstance.quit();
            }
        } catch (WebDriverException e) {
            e.getStackTrace();
        }
    }
}
