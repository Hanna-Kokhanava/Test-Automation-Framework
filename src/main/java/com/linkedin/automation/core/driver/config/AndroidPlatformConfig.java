package com.linkedin.automation.core.driver.config;

import com.linkedin.automation.core.device.Device;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 19.03.2018
 */
public class AndroidPlatformConfig implements IPlatformConfig {

    public DesiredCapabilities getDefaultCapabilitiesForDevice(Device device) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.UDID, device.getDeviceUDID());
        caps.merge(getAutomationNameCapabilities());
        return caps;
    }

    private DesiredCapabilities getAutomationNameCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setCapability(AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
//        caps.setCapability(SYSTEM_PORT, 4881 * 10 + 1);
        return caps;
    }
}
