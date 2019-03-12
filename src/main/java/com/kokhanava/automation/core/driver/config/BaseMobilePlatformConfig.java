package com.kokhanava.automation.core.driver.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kokhanava.automation.core.device.Device;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_VERSION;

/**
 * Created on 06.03.2019
 */
public abstract class BaseMobilePlatformConfig implements ICommandStartAppiumServer, IMobilePlatformConfig {

    /**
     * Creates default capabilities in JSON format for appium command
     *
     * @param device {@link Device} instance
     * @return {@link JsonObject} default capabilities
     */
    JsonObject createDefaultJsonDeviceCapabilities(Device device) {
        var capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.UDID, device.getDeviceUDID());
        capabilities.setCapability(PLATFORM_VERSION, device.getOsVersion());
        capabilities.merge(getPlatformDependentCapabilities(device));

        return new Gson().toJsonTree(capabilities.asMap()).getAsJsonObject();
    }
}
