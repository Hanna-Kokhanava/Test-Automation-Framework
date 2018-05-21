package com.linkedin.automation.core.driver.managers.console;

import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.logger.Logger;
import com.linkedin.automation.core.tools.HostMachine;
import com.linkedin.automation.core.tools.commands.CommandExecutor;

import static com.linkedin.automation.core.tools.files.ResultFolder.APPIUM_FOLDER;

/**
 * The Appium server that can be started in-test.
 */
public class AppiumServerManager extends BaseAppiumServerManager {

    /**
     * Start appium server on {@link HostMachine}
     *
     * @param device for which {@link Device} we need to start server
     */
    @Override
    public void startServer(Device device) {
        HostMachine host = device.getAppiumHostMachine();
        Logger.debug("Starting appium server on " + host);

        CommandExecutor.executeCommandFromFolder(host, APPIUM_FOLDER, getNoHupAppiumCommand(host, commandStartAppium.getCommandToStartAppium(device)));

        waitForServerStarts(host, (Void) -> checkStatus(host));
    }
}
