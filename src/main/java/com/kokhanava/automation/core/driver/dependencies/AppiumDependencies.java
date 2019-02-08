package com.kokhanava.automation.core.driver.dependencies;

import com.kokhanava.automation.core.device.Device;
import com.kokhanava.automation.core.device.DeviceManager;
import com.kokhanava.automation.core.driver.managers.mobile.AppiumServerManager;
import com.kokhanava.automation.core.application.ApplicationManager;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.files.property.PropertyLoader;

/**
 * Created on 15.03.2018
 */
public class AppiumDependencies implements IDependencies {

    /**
     * Sets current device, starts Appium server
     * Upload application on mobile device
     */
    @Override
    public void configureDependencies() {
        Logger.debug("Configure Appium dependencies");
        Device device = DeviceManager.getDevice(PropertyLoader.get(PropertyLoader.MobileProperty.DEVICE_UDID));
        DeviceManager.setCurrentDevice(device);

        if (AppiumServerManager.checkIfServerIsRunning(device.getAppiumHostMachine().getPortInt())) {
            AppiumServerManager.startServer(device);
        } else {
            Logger.warn("Appium server already was started");
        }
        ApplicationManager.uploadApp(device.getAppiumHostMachine());
    }
}
