package com.linkedin.automation.core.driver.creators;

import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.device.DeviceManager;
import com.linkedin.automation.core.application.ApplicationManager;
import com.linkedin.automation.core.tools.files.PropertyLoader;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 02.04.2018
 */
public class DriverCapabilities {
    private final Device.DeviceType deviceType;

    public DriverCapabilities(Device.DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public DesiredCapabilities createBaseCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();

        if (deviceType.os() == Device.DeviceType.ANDROID) {
            caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.linkedin.android");
            caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.linkedin.android.authenticator.LaunchActivity");
            caps.setCapability(AndroidMobileCapabilityType.DEVICE_READY_TIMEOUT, Integer.parseInt(PropertyLoader.get(PropertyLoader.Property.DEVICE_READY_TIMEOUT, "120")));

            //For Android version 6.0 and higher
            caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, "true");
        }

        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, deviceType.os());
        caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, DeviceManager.getCurrentDevice().getAutomationName());

        caps.setCapability(MobileCapabilityType.FULL_RESET, Boolean.parseBoolean(PropertyLoader.get(PropertyLoader.Property.APP_FULL_RESET, "true")));
        caps.setCapability(MobileCapabilityType.NO_RESET, Boolean.parseBoolean(PropertyLoader.get(PropertyLoader.Property.APP_NO_RESET, "false")));

        caps.setCapability(MobileCapabilityType.APP, ApplicationManager.getAbsolutePath());
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceType);

        caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, Integer.parseInt(PropertyLoader.get(PropertyLoader.Property.NEW_COMMAND_TIMEOUT, "1200")));


        if (deviceType.os() == Device.DeviceType.ANDROID) {
            caps.setCapability(AndroidMobileCapabilityType.DEVICE_READY_TIMEOUT, Integer.parseInt(PropertyLoader.get(PropertyLoader.Property.DEVICE_READY_TIMEOUT, "120")));
        } else {
            caps.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, Integer.parseInt(PropertyLoader.get(PropertyLoader.Property.LAUNCH_TIMEOUT, "120000")));
        }

        return caps;
    }

    public DesiredCapabilities getPlatformNameCapabilities(Device device) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, deviceType.os());
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getOsVersion().toString());
        return capabilities;
    }
}
