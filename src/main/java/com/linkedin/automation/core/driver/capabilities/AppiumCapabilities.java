package com.linkedin.automation.core.driver.capabilities;

import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.device.DeviceManager;
import com.linkedin.automation.core.manager.ApplicationManager;
import com.linkedin.automation.core.tools.files.PropertyLoader;
import org.openqa.selenium.remote.DesiredCapabilities;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.*;
import static io.appium.java_client.remote.MobileCapabilityType.*;
import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_NAME;

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

        if (deviceType.os() == Device.DeviceType.ANDROID) {
            caps.setCapability(APP_PACKAGE, "com.linkedin.android");
            caps.setCapability(APP_ACTIVITY, "com.linkedin.android.authenticator.LaunchActivity");
            caps.setCapability(DEVICE_READY_TIMEOUT, Integer.parseInt(PropertyLoader.get(PropertyLoader.MobileProperty.DEVICE_READY_TIMEOUT, "120")));

            //For Android version 6.0 and higher
            caps.setCapability(AUTO_GRANT_PERMISSIONS, "true");
        }

        caps.setCapability(AUTOMATION_NAME, DeviceManager.getCurrentDevice().getAutomationName());

        caps.setCapability(FULL_RESET, Boolean.parseBoolean(PropertyLoader.get(PropertyLoader.MobileProperty.APP_FULL_RESET, "true")));
        caps.setCapability(NO_RESET, Boolean.parseBoolean(PropertyLoader.get(PropertyLoader.MobileProperty.APP_NO_RESET, "false")));

        caps.setCapability(APP, ApplicationManager.getAbsolutePath());
        caps.setCapability(DEVICE_NAME, deviceType);

        caps.setCapability(NEW_COMMAND_TIMEOUT, Integer.parseInt(PropertyLoader.get(PropertyLoader.MobileProperty.NEW_COMMAND_TIMEOUT, "1200")));
        caps.setCapability(DEVICE_READY_TIMEOUT, Integer.parseInt(PropertyLoader.get(PropertyLoader.MobileProperty.DEVICE_READY_TIMEOUT, "120")));
        return caps;
    }

    public DesiredCapabilities getPlatformNameCapabilities(Device device) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(PLATFORM_NAME, deviceType.os());
        capabilities.setCapability(PLATFORM_VERSION, device.getOsVersion().toString());
        return capabilities;
    }
}
