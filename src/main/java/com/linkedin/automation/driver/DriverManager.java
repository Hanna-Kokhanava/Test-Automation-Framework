package com.linkedin.automation.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.MobileCapabilityType.*;

/**
 * Created on 10.03.2018
 */
public class DriverManager {
    //TODO need to move all these properties in test.property file and implement PropertyReader
    private static final String APP_PATH = "C:\\Users\\Ania\\Documents\\apps\\LinkedIn.apk";
    private static final String PACKAGE_NAME = "com.linkedin.android";
    private static final String START_ACTIVITY = "com.linkedin.android.authenticator.LaunchActivity";
    private static final String PLATFORM_NAME = "Android";
    private static final String PLATFORM_VERSION = "7.0";
    private static final String DEVICE_NAME = "Xiaomi";
    private static final String FULL_RESET = "true";
    private static final String NO_RESET = "false";
    private static final String WD_SERVER_ROOT = "/wd/hub";
    private static final String LOCAL_ADDRESS = "http://0.0.0.0:4723";

    private static AndroidDriver androidDriver;

    public static void createDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();
        File app = new File(APP_PATH);

        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, PACKAGE_NAME);
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, START_ACTIVITY);
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        caps.setCapability(MobileCapabilityType.FULL_RESET, FULL_RESET);
        caps.setCapability(MobileCapabilityType.NO_RESET, NO_RESET);
        caps.setCapability(APP, app.getAbsolutePath());

        try {
            androidDriver = new AndroidDriver(new URL(LOCAL_ADDRESS + WD_SERVER_ROOT), caps);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
