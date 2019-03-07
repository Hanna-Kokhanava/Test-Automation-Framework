package com.kokhanava.automation.core.driver.managers.mobile;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kokhanava.automation.core.device.Device;
import com.kokhanava.automation.core.driver.PlatformModules;
import com.kokhanava.automation.core.driver.config.ICommandStartAppiumServer;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.commands.CommandExecutor;

import static com.kokhanava.automation.core.tools.files.ResultFolder.APPIUM_FOLDER;

/**
 * Created on 01.04.2018
 */
public class AppiumServerManager extends BaseAppiumServerManager {

    private Injector injector = Guice.createInjector(new PlatformModules());
    private ICommandStartAppiumServer commandStartAppium = injector.getInstance(ICommandStartAppiumServer.class);

    /**
     * Starts Appium server on IP:port address
     *
     * @param device {@link Device}
     */
    @Override
    public void startServer(Device device) {
        Logger.debug("Start Appium server on [" + device.getAppiumHostMachine().getHostname() + "]");
        HostMachine hostMachine = device.getAppiumHostMachine();
        CommandExecutor.executeCommandFromFolder(hostMachine, APPIUM_FOLDER, commandStartAppium.getStartAppiumCommand(device));
        waitForServerStarts(hostMachine, (Void) -> checkStatus(hostMachine));
    }
}
