package com.linkedin.automation.core.creators;

import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.device.DeviceManager;
import com.linkedin.automation.core.driver.capabilities.AppiumCapabilities;
import com.linkedin.automation.core.driver.managers.AppiumServerManager;
import com.linkedin.automation.core.driver.managers.DriverManager;
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

        System.out.println("Creating driver...");
        try {
            DriverManager.createDriver(device.getAppiumHostMachine(), capabilities);
        } catch (Exception er) {
            System.out.println("Exception happened with create driver. Look at appium log for additional information.\n");
            AppiumServerManager.restartServer(device);
            DriverManager.createDriver(device.getAppiumHostMachine(), capabilities);
        }
        return device;
    }

    @Override
    public void closeDriver() {
        DriverManager.closeDriver();
    }

    private DesiredCapabilities createCapabilitiesForAppium(Device device) {
        AppiumCapabilities appiumCapabilities = new AppiumCapabilities(device.getDeviceType());
        DesiredCapabilities caps = appiumCapabilities.createBaseCapabilities();
        caps.merge(appiumCapabilities.getPlatformNameCapabilities(device));
        return caps;
    }
}
