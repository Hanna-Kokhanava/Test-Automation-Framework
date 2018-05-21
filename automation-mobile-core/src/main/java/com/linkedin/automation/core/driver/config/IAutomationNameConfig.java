package com.linkedin.automation.core.driver.config;

import com.linkedin.automation.core.device.Device;
import org.openqa.selenium.remote.DesiredCapabilities;

public interface IAutomationNameConfig {

    DesiredCapabilities getAutomationNameCapabilities(Device device);

}
