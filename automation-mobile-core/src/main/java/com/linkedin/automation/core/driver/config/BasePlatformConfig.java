package com.linkedin.automation.core.driver.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.linkedin.automation.core.device.Device;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_VERSION;

public abstract class BasePlatformConfig implements ICommandStartAppium, IAutomationNameConfig, IPlatformConfig {

    public DesiredCapabilities getDefaultDesiredCapabilitiesForDevice(Device device) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.UDID, device.getDeviceUDID());
        caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, device.getOsVersion());
        return caps;
    }

    /**
     * Create default capabilities for -cp appium flag
     *
     * @param device for which {@link Device} we need start appium server
     * @return {@link JsonObject} default capabilities
     */
    JsonObject getDefaultCapabilitiesForDevice(Device device) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.UDID, device.getDeviceUDID());
        caps.setCapability(PLATFORM_VERSION, device.getOsVersion());
        caps.merge(getAutomationNameCapabilities(device));

        return new Gson().toJsonTree(caps.asMap()).getAsJsonObject();
    }

    @Override
    public String getCommandToStartAppiumForGrid(Device device, String pathToNodeJson) {
        String command = getCommandToStartAppium(device);
        command += String.format(" --nodeconfig %s ", pathToNodeJson);
        return command;
    }
}
