package com.linkedin.automation.core.driver.config;

import com.google.gson.JsonObject;
import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.tools.HostMachine;
import com.linkedin.automation.core.tools.commands.Command;
import com.linkedin.automation.core.tools.commands.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.SYSTEM_PORT;
import static io.appium.java_client.remote.AutomationName.ANDROID_UIAUTOMATOR2;
import static io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME;

/**
 * Created on 19.03.2018
 */
public class AndroidPlatformConfig extends BasePlatformConfig {

    @Override
    public DesiredCapabilities getAutomationNameCapabilities(Device device) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(AUTOMATION_NAME, device.getAutomationName());
        switch (device.getAutomationName()) {
            case ANDROID_UIAUTOMATOR2: {
                caps.setCapability(SYSTEM_PORT, device.getAppiumHostMachine().getPortInt() * 10 + 1);
                break;
            }
        }
        return caps;
    }

    @Override
    public String getCommandToStartAppium(Device device) {
        HostMachine host = device.getAppiumHostMachine();
        JsonObject defaultCapabilities = getDefaultCapabilitiesForDevice(device);
        return Command.APPIUM_TEMPLATE_START_APPIUM_SERVER_ANDROID.getCommand(CommandExecutor.getOsOfMachine(host),
                host.getPort(),
                host.getPortInt() * 10,
                defaultCapabilities.toString());
    }
}

