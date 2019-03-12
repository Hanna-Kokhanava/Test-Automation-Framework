package com.kokhanava.automation.core.driver.config;

import com.kokhanava.automation.core.device.Device;

/**
 * Created on 06.03.2019
 */
public interface ICommandStartAppiumServer {
    /**
     * Returns command to start appium
     * Command depends on {@link Device.DeviceType} from config properties
     *
     * @param device {@link Device} instance for current session
     * @return command to start appium server
     */
    String getStartAppiumCommand(Device device);
}
