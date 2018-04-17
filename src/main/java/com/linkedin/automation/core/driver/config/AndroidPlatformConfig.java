package com.linkedin.automation.core.driver.config;

import com.linkedin.automation.core.device.Device;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_VERSION;

/**
 * Created on 19.03.2018
 */
public class AndroidPlatformConfig implements IPlatformConfig {

    public DesiredCapabilities getDefaultCapabilitiesForDevice(Device device) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.UDID, device.getDeviceUDID());
        caps.setCapability(PLATFORM_VERSION, device.getOsVersion());
        return caps;
    }
}
