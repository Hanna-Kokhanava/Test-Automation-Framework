package com.linkedin.automation.core.driver.config;

import com.linkedin.automation.core.device.Device;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 05.04.2018
 */
public interface IPlatformConfig {

    DesiredCapabilities getDefaultDesiredCapabilitiesForDevice(Device device);
}
