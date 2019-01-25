package com.kokhanava.automation.core.driver.config;

import com.kokhanava.automation.core.device.Device;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 05.04.2018
 */
public interface IPlatformConfig {

    DesiredCapabilities getDefaultCapabilitiesForDevice(Device device);
}
