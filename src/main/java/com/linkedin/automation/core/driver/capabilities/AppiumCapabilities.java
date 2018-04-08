package com.linkedin.automation.core.driver.capabilities;

import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.manager.ApplicationManager;
import com.linkedin.automation.core.tools.files.PropertyLoader;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static io.appium.java_client.remote.AutomationName.ANDROID_UIAUTOMATOR2;
import static io.appium.java_client.remote.MobileCapabilityType.*;

/**
 * Created on 02.04.2018
 */
public class AppiumCapabilities {
    private final Device.DeviceType deviceType;

    public AppiumCapabilities(Device.DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public DesiredCapabilities createBaseCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability(NEW_COMMAND_TIMEOUT, Integer.parseInt(PropertyLoader.get(PropertyLoader.Property.NEW_COMMAND_TIMEOUT, "1200")));
        caps.setCapability(DEVICE_NAME, deviceType);
        caps.setCapability(AUTOMATION_NAME, "Appium");
        caps.setCapability(AUTOMATION_NAME, ANDROID_UIAUTOMATOR2);

        caps.setCapability(FULL_RESET, Boolean.parseBoolean(PropertyLoader.get(PropertyLoader.Property.APP_FULL_RESET, "true")));
        caps.setCapability(NO_RESET, Boolean.parseBoolean(PropertyLoader.get(PropertyLoader.Property.APP_NO_RESET, "false")));
        caps.setCapability(APP, ApplicationManager.getAbsolutePath());

        caps.setCapability(AndroidMobileCapabilityType.DEVICE_READY_TIMEOUT, Integer.parseInt(PropertyLoader.get(PropertyLoader.Property.DEVICE_READY_TIMEOUT, "120")));
        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.thirtydegreesray.openhub");
        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.thirtydegreesray.openhub.ui.activity.SplashActivity");

//        caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.linkedin.android");
//        caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.linkedin.android.authenticator.LaunchActivity");
        return caps;
    }

    public DesiredCapabilities getPlatformNameCapabilities(Device device) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PLATFORM_NAME, device.getDeviceType().os());
        capabilities.setCapability(PLATFORM_VERSION, device.getOsVersion().toString());
        return capabilities;
    }
}
