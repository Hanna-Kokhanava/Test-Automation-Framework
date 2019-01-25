package com.kokhanava.automation.core.creator;

import com.kokhanava.automation.core.device.Device;
import com.kokhanava.automation.core.device.DeviceManager;
import com.kokhanava.automation.core.driver.capabilities.AppiumCapabilities;
import com.kokhanava.automation.core.driver.managers.mobile.AppiumServerManager;
import com.kokhanava.automation.core.driver.managers.mobile.MobileDriverManager;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Objects;

/**
 * Implements functionality of AppiumDriver creation with capabilities and driver closing
 * Created on 10.03.2018
 */
public class AppiumDriverCreator implements IDriverCreator {

    /**
     * {@inheritDoc}
     * Creates AppiumDriver
     *
     * @return current {@link Device} instance
     */
    @Override
    public Device createDriver() throws Exception {
        return createDriver(null);
    }

    /**
     * {@inheritDoc}
     * Creates AppiumDriver
     *
     * @return current {@link Device} instance
     */
    @Override
    public Device createDriver(DesiredCapabilities customCapabilities) throws Exception {
        Device device = DeviceManager.getCurrentDevice();
        DesiredCapabilities capabilities = createCapabilitiesForAppium(device);

        if (Objects.nonNull(customCapabilities)) {
            capabilities.merge(customCapabilities);
        }

        System.out.println("Creating driver...");
        try {
            MobileDriverManager.createDriver(capabilities);
        } catch (Exception er) {
            System.out.println("Exception happened with create driver. Look at appium log for additional information.\n");
            AppiumServerManager.restartServer(device);
            MobileDriverManager.createDriver(capabilities);
        }
        return device;
    }

    /**
     * Closes AppiumDriver
     */
    @Override
    public void closeDriver() {
        MobileDriverManager.closeDriver();
    }

    /**
     * Creates base capabilities for Appium
     *
     * @param device {@link Device} instance
     * @return {@link DesiredCapabilities} instance
     */
    private DesiredCapabilities createCapabilitiesForAppium(Device device) {
        AppiumCapabilities appiumCapabilities = new AppiumCapabilities(device.getDeviceType());
        DesiredCapabilities caps = appiumCapabilities.createBaseCapabilities();
        caps.merge(appiumCapabilities.getMobilePlatformCapabilities(device));
        return caps;
    }
}
