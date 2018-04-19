package com.linkedin.automation.core.driver.dependencies;

import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.device.DeviceManager;
import com.linkedin.automation.core.driver.managers.AppiumServerManager;
import com.linkedin.automation.core.tools.files.PropertyLoader;
import com.linkedin.automation.core.manager.ApplicationManager;

/**
 * Created on 15.03.2018
 */
public class AppiumDependencies implements IDependencies {

    @Override
    public void configureDependencies() {
        Device device = DeviceManager.getDevice(PropertyLoader.get(PropertyLoader.Property.DEVICE_UDID));
        DeviceManager.setCurrentDevice(device);

        if (!AppiumServerManager.checkIfServerIsRunning(device.getAppiumHostMachine().getPortInt())) {
            AppiumServerManager.startServer(device);
        } else {
            System.out.println("Appium server already was started");
        }
        ApplicationManager.uploadApp(device.getAppiumHostMachine());
    }
}
