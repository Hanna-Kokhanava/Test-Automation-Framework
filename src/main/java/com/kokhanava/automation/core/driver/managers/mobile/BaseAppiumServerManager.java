package com.kokhanava.automation.core.driver.managers.mobile;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kokhanava.automation.core.driver.PlatformModules;
import com.kokhanava.automation.core.driver.config.ICommandStartAppiumServer;
import com.kokhanava.automation.core.driver.managers.AbstractServerManager;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.commands.Command;
import com.kokhanava.automation.core.tools.commands.CommandExecutor;
import com.kokhanava.automation.core.tools.httpclients.HttpClient;

/**
 * Created on 06.03.2019
 */
public abstract class BaseAppiumServerManager extends AbstractServerManager implements IAppiumServer {

    private Injector injector = Guice.createInjector(new PlatformModules());
    protected ICommandStartAppiumServer commandStartAppium = injector.getInstance(ICommandStartAppiumServer.class);

    public void stopServer(HostMachine host) {
        String exitMessage = CommandExecutor.execute(host, Command.SYSTEM_TEMPLATE_KILL_PID, getPidAppiumProcess(host));
        HttpClient.closeHttpClient();

        if (!exitMessage.isBlank() && !exitMessage.contains("SUCCESS")) {
            Logger.error(String.format("Cannot stop Appium server on %s:%s : ", host.getHostname(), host.getPort()));
        }

        if (!checkStatus(host)) {
            Logger.debug("Appium server was stopped");
        } else {
            Logger.error(String.format("Cannot stop Appium server on %s:%s : ", host.getHostname(), host.getPort()));
        }
    }

    /**
     * Checks status of appium server
     *
     * @param hostMachine {@link HostMachine} instance
     * @return current status of server - true/false
     */
    public boolean checkStatus(HostMachine hostMachine) {
        Logger.debug("Check Appium server status on " + hostMachine);
        return checkStatus(hostMachine.getURIBuilder().setPath(WD_SERVER_STATUS));
    }

    /**
     * Returns PID of appium server
     *
     * @param host {@link HostMachine} instance
     * @return command execution output
     */
    private String getPidAppiumProcess(HostMachine host) {
        return CommandExecutor.execute(host, Command.APPIUM_GET_PID, host.getPort());
    }
}
