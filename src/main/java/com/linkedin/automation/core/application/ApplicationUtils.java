package com.linkedin.automation.core.application;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static io.appium.java_client.remote.MobileBrowserType.*;
import static io.appium.java_client.remote.MobileCapabilityType.APP;
import static io.appium.java_client.remote.MobilePlatform.ANDROID;
import static io.appium.java_client.remote.MobilePlatform.IOS;
import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;

/**
 * Created on 17.04.2018
 */
public final class ApplicationUtils {

    /**
     * Detect application type based on driver type.
     *
     * @param driver the {@link WebDriver} instance
     * @return {@link ApplicationType} instance
     */
    public static ApplicationType getAppType(WebDriver driver) {

        if (isNativeAndroidApp(driver)) {
            return ApplicationType.NATIVE_ANDROID_APP;
        } else if (isNativeIOSApp(driver)) {
            return ApplicationType.NATIVE_IOS_APP;
        } else if (isWebApp(driver)) {
            return ApplicationType.WEB_APP;
        } else {
            throw new RuntimeException("Platform or browser is not supported: " + driver);
        }
    }

    /**
     * Checks if it's native Android app
     *
     * @param driver the {@link WebDriver} instance
     * @return true, if it's native Android app
     */
    private static boolean isNativeAndroidApp(WebDriver driver) {
        String platformName = ((AppiumDriver) driver).getPlatformName();
        String app = getCapability(driver, APP);
        return !isNullOrEmpty(app) && Objects.requireNonNull(platformName,
                "Cannot get platform name from driver capabilities").equalsIgnoreCase(ANDROID);
    }

    /**
     * Checks if it's native IOS app
     *
     * @param driver the {@link WebDriver} instance
     * @return true, if it's native IOS app
     */
    private static boolean isNativeIOSApp(WebDriver driver) {
        String platformName = ((AppiumDriver) driver).getPlatformName();
        String app = getCapability(driver, APP);
        return !isNullOrEmpty(app) && Objects.requireNonNull(platformName,
                "Cannot get platform name from driver capabilities").equalsIgnoreCase(IOS);
    }

    /**
     * Checks if it's web app
     *
     * @param driver the {@link WebDriver} instance
     * @return true, if it's web app
     */
    private static boolean isWebApp(WebDriver driver) {
        String browserName = getCapability(driver, BROWSER_NAME);
        return browserName.equalsIgnoreCase(CHROME)
                || browserName.equalsIgnoreCase(CHROMIUM)
                || browserName.equalsIgnoreCase(BROWSER);
    }

    /**
     * Gets the capability by its name
     *
     * @param driver         the {@link WebDriver} instance
     * @param capabilityName the capability name
     * @return the capability value
     */
    private static String getCapability(WebDriver driver, String capabilityName) {
        Capabilities caps = ((HasCapabilities) driver).getCapabilities();
        checkNotNull(caps);
        return (String) caps.getCapability(capabilityName);
    }
}
