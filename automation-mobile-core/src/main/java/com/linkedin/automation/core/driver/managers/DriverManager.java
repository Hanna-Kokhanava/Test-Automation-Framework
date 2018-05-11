package com.linkedin.automation.core.driver.managers;

import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.device.DeviceManager;
import com.linkedin.automation.core.logger.Logger;
import com.linkedin.automation.core.tools.HostMachine;
import com.linkedin.automation.core.tools.files.PropertyLoader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

/**
 * Created on 10.03.2018
 */
public class DriverManager {

    public enum DriverType {
        APPIUM("appium"),
        GRID("grid");

        private String driverType;

        DriverType(String driverType) {
            this.driverType = driverType;
        }

        @Override
        public String toString() {
            return driverType;
        }

        public static DriverType findByValue(String value) {
            for (DriverType driverType : DriverType.values()) {
                if (driverType.toString().equals(value)) {
                    return driverType;
                }
            }
            return null;
        }
    }

    private static final DriverType driverType = DriverType
            .findByValue(PropertyLoader.get(PropertyLoader.Property.DRIVER_TYPE, "appium"));
    private static HostMachine driverHost;

    private static final String URI_SCHEME = "http";
    private static final String WD_SERVER_ROOT = "/wd/hub";

    private static final ThreadLocal<AppiumDriver> driverPool = new ThreadLocal<>();

    public static HostMachine getDriverHost() {
        switch (getDriverType()) {
            case GRID:
                if (driverHost == null) {
                    driverHost = new HostMachine(PropertyLoader.get(PropertyLoader.Property.MULTY_TREAD_DRIVER_URL));
                }
                return driverHost;
            case APPIUM:
                return DeviceManager.getCurrentDevice().getAppiumHostMachine();
        }
        return driverHost;
    }


    private DriverManager() {
    }

    public static DriverType getDriverType() {
        return driverType;
    }

    public static AppiumDriver getDriver() {
        return driverPool.get();
    }

    public static AndroidDriver getAndroidDriver() {
        return (AndroidDriver) driverPool.get();
    }

    public static IOSDriver getIOSDriver() {
        return (IOSDriver) driverPool.get();
    }


    public static void createDriver(DesiredCapabilities capabilities) throws Exception {
        AppiumDriver driver;
        try {
            if (DeviceManager.getDeviceTypeFromConfigFile().os() == Device.DeviceType.ANDROID) {
                driver = new AndroidDriver(
                        getDriverHost().getURIBuilder(URI_SCHEME).setPath(WD_SERVER_ROOT).build().toURL(), capabilities);
            } else {
                driver = new IOSDriver(
                        getDriverHost().getURIBuilder(URI_SCHEME).setPath(WD_SERVER_ROOT).build().toURL(), capabilities);
            }
        } catch (Exception e) {
            Logger.warn("Exception happened with create driver:\n" + e.getMessage());
            throw e;
        }
        driverPool.set(driver);
        driverPool.get().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    public static void closeDriver() {
        WebDriver driverInstance = driverPool.get();
        try {
            if (driverInstance != null) {
                driverPool.remove();
                driverInstance.quit();
            }
        } catch (WebDriverException e) {
            Logger.error("Oops, looks like the driver has quited a bit earlier", e);
        }
    }
}
