package com.linkedin.automation.core.driver.config;

import com.linkedin.automation.core.device.Device;

public interface ICommandStartAppium {

    /**
     * Get command to start appium, depends on {@link Device.DeviceType} from config properties
     *
     * @param device for which {@link Device} we need to start appium
     * @return {@link String} command
     */
    String getCommandToStartAppium(Device device);

    /**
     * Get command to start appium with node config for {@link Device}
     *
     * @param device         for which {@link Device} we need create command
     * @param pathToNodeJson relative path to node config
     * @return {@link String} command
     */
    String getCommandToStartAppiumForGrid(Device device, String pathToNodeJson);

}
