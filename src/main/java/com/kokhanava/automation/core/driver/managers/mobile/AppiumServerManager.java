package com.kokhanava.automation.core.driver.managers.mobile;

import com.kokhanava.automation.core.device.Device;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.commands.CommandExecutor;

import static com.kokhanava.automation.core.tools.files.ResultFolder.APPIUM_FOLDER;

/**
 * Created on 01.04.2018
 */
public class AppiumServerManager extends BaseAppiumServerManager {

    /**
     * Starts Appium server on IP:port address
     *
     * @param device {@link Device}
     */
    @Override
    public void startServer(Device device) {
        Logger.debug("Start Appium server on " + device.getAppiumHostMachine().getHostname());
        HostMachine host = device.getAppiumHostMachine();

        CommandExecutor.executeCommandFromFolder(host, APPIUM_FOLDER, commandStartAppium.getStartAppiumCommand(device));

    }
}
