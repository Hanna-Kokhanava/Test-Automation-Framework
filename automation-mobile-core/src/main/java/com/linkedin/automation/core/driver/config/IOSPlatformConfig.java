package com.linkedin.automation.core.driver.config;

import com.google.gson.JsonObject;
import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.tools.HostMachine;
import com.linkedin.automation.core.tools.commands.Command;
import com.linkedin.automation.core.tools.commands.CommandExecutor;
import com.linkedin.automation.core.tools.files.PropertyLoader;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

import static io.appium.java_client.remote.AutomationName.IOS_XCUI_TEST;
import static io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME;

public class IOSPlatformConfig extends BasePlatformConfig {

    @Override
    public DesiredCapabilities getAutomationNameCapabilities(Device device) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(AUTOMATION_NAME, device.getAutomationName());
        switch (device.getAutomationName()) {
            case IOS_XCUI_TEST: {
                caps.setCapability(IOSMobileCapabilityType.WDA_LOCAL_PORT, device.getAppiumHostMachine().getPortInt() * 10);
                caps.setCapability(IOSMobileCapabilityType.UPDATE_WDA_BUNDLEID, "io.appium.WebDriverAgentRunner");
                caps.merge(new DesiredCapabilities(getProvisioningProfileConfiguration()));
            }
            break;
        }

        return caps;
    }

    private Map<String, String> getProvisioningProfileConfiguration() {
        Map<String, String> config = new HashMap<>();

        config.put(IOSMobileCapabilityType.XCODE_ORG_ID,
                PropertyLoader.get(PropertyLoader.Property.XCODE_ORG_ID, ""));
        config.put(IOSMobileCapabilityType.XCODE_SIGNING_ID,
                PropertyLoader.get(PropertyLoader.Property.XCODE_SIGNING_ID, "iPhone Developer"));
        return config;
    }

    @Override
    public String getCommandToStartAppium(Device device) {
        HostMachine host = device.getAppiumHostMachine();
        String tmpFlag = "tmp-" + host.getPort();
        JsonObject defaultCapabilities = getDefaultCapabilitiesForDevice(device);

        return Command.APPIUM_TEMPLATE_START_APPIUM_SERVER_IOS.
                getCommand(CommandExecutor.getOsOfMachine(host),
                        host.getPort(),
                        tmpFlag,
                        defaultCapabilities.toString());
    }
}



