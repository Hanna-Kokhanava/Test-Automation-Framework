package com.kokhanava.automation.core.driver.capabilities;

import com.kokhanava.automation.core.application.AppDetails;
import com.kokhanava.automation.core.device.Device;
import com.kokhanava.automation.core.device.DeviceManager;
import com.kokhanava.automation.core.application.ApplicationManager;
import com.kokhanava.automation.core.tools.files.property.PropertyLoader;
import org.openqa.selenium.remote.DesiredCapabilities;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.*;
import static io.appium.java_client.remote.MobileCapabilityType.*;
import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_NAME;

/**
 * Creates capabilities for Appium driver based on properties in configuration files
 * Created on 02.04.2018
 */
public class AppiumCapabilities {

    private final Device.DeviceType deviceType;

    public AppiumCapabilities(Device.DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * Creates base capabilities for mobile devices
     *
     * @return {@link DesiredCapabilities} capabilities
     */
    public DesiredCapabilities createBaseCapabilities() {
        var caps = new DesiredCapabilities();

        if (deviceType.os() == Device.DeviceType.ANDROID) {
            caps.setCapability(APP_PACKAGE, AppDetails.getAppPackage());
            caps.setCapability(APP_ACTIVITY, AppDetails.getAppActivity());

            //For Android version 6.0 and higher
            caps.setCapability(AUTO_GRANT_PERMISSIONS, "true");
        }

        caps.setCapability(AUTOMATION_NAME, DeviceManager.getCurrentDevice().getAutomationName());
        caps.setCapability(APP, ApplicationManager.getAbsolutePath());
        caps.setCapability(DEVICE_NAME, deviceType);

        caps.setCapability(FULL_RESET, Boolean.parseBoolean(PropertyLoader.get(PropertyLoader.MobileProperty.APP_FULL_RESET, "true")));
        caps.setCapability(NO_RESET, Boolean.parseBoolean(PropertyLoader.get(PropertyLoader.MobileProperty.APP_NO_RESET, "false")));

        caps.setCapability(NEW_COMMAND_TIMEOUT, Integer.parseInt(PropertyLoader.get(PropertyLoader.MobileProperty.NEW_COMMAND_TIMEOUT, "1200")));
        caps.setCapability(DEVICE_READY_TIMEOUT, Integer.parseInt(PropertyLoader.get(PropertyLoader.MobileProperty.DEVICE_READY_TIMEOUT, "120")));
        return caps;
    }

    /**
     * Creates platform details capabilities
     *
     * @param device {@link Device} instance
     * @return {@link DesiredCapabilities} instance
     */
    public DesiredCapabilities getMobilePlatformCapabilities(Device device) {
        var capabilities = new DesiredCapabilities();
        capabilities.setCapability(PLATFORM_NAME, deviceType.os());
        capabilities.setCapability(PLATFORM_VERSION, device.getOsVersion().toString());
        return capabilities;
    }
}
