package com.kokhanava.automation.core.driver.managers.mobile;

import com.kokhanava.automation.core.device.Device;
import com.kokhanava.automation.core.device.DeviceManager;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.files.property.PropertyLoader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created on 10.03.2018
 */
public class MobileDriverManager {

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
            .findByValue(PropertyLoader.get(PropertyLoader.MobileProperty.DRIVER_TYPE, "appium"));
    private static HostMachine driverHost;

    private static final String URI_SCHEME = "http";
    private static final String WD_SERVER_ROOT = "/wd/hub";

    private static final ThreadLocal<AppiumDriver> driverPool = new ThreadLocal<>();

    /**
     * Returns driver host depends on driver type
     * For GRID - specified in properties file
     * For APPIUM - depends on device xml configuration
     *
     * @return {@link HostMachine}
     */
    public static HostMachine getDriverHost() {
        switch (getDriverType()) {
            case GRID:
                if (Objects.isNull(driverHost)) {
                    driverHost = new HostMachine(PropertyLoader.get(PropertyLoader.MobileProperty.DRIVER_URL));
                }
                return driverHost;
            case APPIUM:
                return DeviceManager.getCurrentDevice().getAppiumHostMachine();
        }
        return driverHost;
    }

    private MobileDriverManager() {
    }

    public static DriverType getDriverType() {
        return driverType;
    }

    /**
     * Returns general driver
     *
     * @return {@link AppiumDriver} instance
     */
    public static AppiumDriver getDriver() {
        return driverPool.get();
    }

    /**
     * Returns specific driver for Android
     *
     * @return {@link AndroidDriver} instance
     */
    public static AndroidDriver getAndroidDriver() {
        return (AndroidDriver) driverPool.get();
    }

    /**
     * Returns specific driver for iOS
     *
     * @return {@link IOSDriver} instance
     */
    public static IOSDriver getIOSDriver() {
        return (IOSDriver) driverPool.get();
    }

    /**
     * Creates driver depends on current device type (Android, iOS)
     *
     * @param capabilities {@link DesiredCapabilities}
     */
    public static void createDriver(DesiredCapabilities capabilities) {
        Device.DeviceType type = DeviceManager.getDeviceTypeFromConfigFile().os();
        AppiumDriver driver = null;

        try {
            switch (type) {
                case ANDROID:
                    driver = new AndroidDriver(
                            getDriverHost().getURIBuilder(URI_SCHEME).setPath(WD_SERVER_ROOT).build().toURL(), capabilities);
                    break;
                case IOS:
                    driver = new IOSDriver(
                            getDriverHost().getURIBuilder(URI_SCHEME).setPath(WD_SERVER_ROOT).build().toURL(), capabilities);
                    break;
            }
        } catch (Exception e) {
            Logger.error("Exception happened during driver creation", e);
        }

        Objects.requireNonNull(driver, "Exception happened during driver creation");
        driverPool.set(driver);
        driverPool.get().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    /**
     * Closes driver
     */
    public static void closeDriver() {
        AppiumDriver driverInstance = driverPool.get();
        // put quit() method to try/catch as shitty appium server may crash at any moment
        try {
            if (Objects.nonNull(driverInstance)) {
                driverPool.remove();
                driverInstance.quit();
            }
        } catch (WebDriverException e) {
            Logger.error("Oops, looks like the driver has quited a bit earlier", e);
        }
    }
}
