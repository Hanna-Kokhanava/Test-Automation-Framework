package com.linkedin.automation.core.creators;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

import static io.appium.java_client.remote.MobileCapabilityType.APP;

/**
 * Created on 10.03.2018
 */
public class DriverCapabilities {

    //TODO need to move all these properties in test.property file and implement PropertyReader
    private static final String APP_PATH = "C:\\Users\\Ania\\Documents\\apps\\LinkedIn.apk";
    private static final String PACKAGE_NAME = "com.linkedin.android";
    private static final String START_ACTIVITY = "com.linkedin.android.authenticator.LaunchActivity";
    private static final String PLATFORM_NAME = "Android";
    private static final String PLATFORM_VERSION = "7.0";
    private static final String DEVICE_NAME = "Xiaomi";
    private static final String FULL_RESET = "true";
    private static final String NO_RESET = "false";

    public static DesiredCapabilities createCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();
        File app = new File(APP_PATH);

        //TODO fixed in Appium 1.8-beta https://github.com/appium/appium/issues/9987
//        caps.setCapability(MobileCapabilityType.LOCALE, "en_US");
//        caps.setCapability(MobileCapabilityType.LANGUAGE, "en");

        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, PACKAGE_NAME);
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, START_ACTIVITY);
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, PLATFORM_VERSION);
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME);
        caps.setCapability(MobileCapabilityType.FULL_RESET, FULL_RESET);
        caps.setCapability(MobileCapabilityType.NO_RESET, NO_RESET);
        caps.setCapability(APP, app.getAbsolutePath());

        return caps;
    }
}
