package com.linkedin.automation.core.creators;

import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.device.DeviceManager;
import com.linkedin.automation.core.driver.capabilities.DriverCapabilities;
import com.linkedin.automation.core.driver.managers.AppiumServerManager;
import com.linkedin.automation.core.driver.managers.DriverManager;
import com.linkedin.automation.core.logger.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 10.03.2018
 */
public class AppiumDriverCreator implements IDriverCreator {

    @Override
    public Device createDriver() throws Exception {
        return createDriver(null);
    }

    @Override
    public Device createDriver(DesiredCapabilities customCapabilities) throws Exception {
        Device device = DeviceManager.getCurrentDevice();
        DesiredCapabilities capabilities = createCapabilitiesForAppium(device);

        if (null != customCapabilities) {
            capabilities.merge(customCapabilities);
        }

        Logger.debug("Creating driver...");
        try {
            DriverManager.createDriver(capabilities);
        } catch (Exception er) {
            Logger.warn("Exception happened with create driver. Look at appium log for additional information.\n");
            AppiumServerManager.restartServer(device);
            DriverManager.createDriver(capabilities);
        }
        return device;
    }

    @Override
    public void closeDriver() {
        Logger.debug("Quitting driver...");
        DriverManager.closeDriver();
        Logger.debug("Driver quited");
    }

    private DesiredCapabilities createCapabilitiesForAppium(Device device) {
        DriverCapabilities appiumCapabilities = new DriverCapabilities(device.getDeviceType());
        DesiredCapabilities caps = appiumCapabilities.createBaseCapabilities();
        caps.merge(appiumCapabilities.getPlatformNameCapabilities(device));
        return caps;
    }
}
