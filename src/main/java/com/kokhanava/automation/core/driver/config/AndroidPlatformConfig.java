package com.kokhanava.automation.core.driver.config;

import com.kokhanava.automation.core.device.Device;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_VERSION;

/**
 * Created on 19.03.2018
 */
public class AndroidPlatformConfig implements IPlatformConfig {

    /**
     * Creates specific capabilities for
     *
     * @param device {@link Device}
     * @return {@link DesiredCapabilities} instance
     */
    public DesiredCapabilities getDefaultCapabilitiesForDevice(Device device) {
        var caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.UDID, device.getDeviceUDID());
        caps.setCapability(PLATFORM_VERSION, device.getOsVersion());
        return caps;
    }
}
