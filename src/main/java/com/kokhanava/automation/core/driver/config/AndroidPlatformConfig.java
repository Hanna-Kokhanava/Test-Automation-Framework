package com.kokhanava.automation.core.driver.config;

import com.google.gson.JsonObject;
import com.kokhanava.automation.core.device.Device;
import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.OS;
import com.kokhanava.automation.core.tools.commands.Command;
import com.kokhanava.automation.core.tools.commands.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.SYSTEM_PORT;
import static io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME;

/**
 * Created on 19.03.2018
 */
public class AndroidPlatformConfig extends BaseMobilePlatformConfig {

    @Override
    public DesiredCapabilities getPlatformDependentCapabilities(Device device) {
        var caps = new DesiredCapabilities();
        caps.setCapability(AUTOMATION_NAME, device.getAutomationName());
        caps.setCapability(SYSTEM_PORT, device.getAppiumHostMachine().getPortInt() * 10 + 1);
        return caps;
    }

    @Override
    public String getStartAppiumCommand(Device device) {
        HostMachine host = device.getAppiumHostMachine();
        JsonObject defaultCapabilities = createDefaultJsonDeviceCapabilities(device);

        String stringCapabilities = defaultCapabilities.toString();
        if (host.hasOS(OS.WINDOWS)) {
            //Need to escape double quotes - https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/default-capabilities-arg.md
            stringCapabilities = stringCapabilities.replaceAll("\"", "\\\\\"");
        }

        return Command.APPIUM_START_SERVER_ANDROID.getCommand(CommandExecutor.getOsOfMachine(host),
                host.getPort(),
                host.getPortInt() * 10,
                host.getPort(),
                stringCapabilities);
    }
}
