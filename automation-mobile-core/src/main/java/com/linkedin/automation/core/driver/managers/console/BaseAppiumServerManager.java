package com.linkedin.automation.core.driver.managers.console;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.driver.PlatformModules;
import com.linkedin.automation.core.driver.config.ICommandStartAppium;
import com.linkedin.automation.core.logger.Logger;
import com.linkedin.automation.core.tools.HostMachine;
import com.linkedin.automation.core.tools.commands.Command;
import com.linkedin.automation.core.tools.commands.CommandExecutor;
import com.linkedin.automation.core.tools.httpclients.HttpClient;
import org.openqa.selenium.server.browserlaunchers.Sleeper;

import java.util.List;

public abstract class BaseAppiumServerManager extends AbstractServerManager implements IAppiumServer {

    private Injector injector = Guice.createInjector(new PlatformModules());
    protected ICommandStartAppium commandStartAppium = injector.getInstance(ICommandStartAppium.class);

    /**
     * Stop appium server on {@link HostMachine}
     *
     * @param host on which {@link HostMachine} we need to stop appium server
     */
    public void stopServer(HostMachine host) {
        Logger.debug("Stopping appium server on " + host);
        String exitMessage = CommandExecutor.execute(host, Command.SYSTEM_TEMPLATE_KILL_PID, getPidAppiumProcess(host));
        HttpClient.closeHttpClient();
        if (!exitMessage.equals("") && !exitMessage.contains("SUCCESS"))
            Logger.error(String.format("Cannot stop Appium server on %s:%s : ", host.getHostname(), host.getPort()) + exitMessage);

        waitForServerCondition(Void -> getPidAppiumProcess(host).equals(""));

        if (!checkStatus(host)) {
            Logger.debug("Appium server stopped");
        } else {
            Logger.error(String.format("Cannot stop Appium server on %s:%s : ", host.getHostname(), host.getPort()) + exitMessage);
        }
    }

    /**
     * Restart appium server on {@link HostMachine}
     *
     * @param device for which {@link Device} we need to restart appium server
     */
    public void restartServer(Device device) {
        Logger.debug("Restart Appium server");
        stopServer(device.getAppiumHostMachine());
        Sleeper.sleepTight(2);
        startServer(device);
    }

    /**
     * Restart appium nodes for devices
     *
     * @param devices {@link Device} list
     */
    public void restartServers(List<Device> devices) {
        for (Device device : devices) {
            restartServer(device);
        }
    }

    /**
     * Check status of appium server
     *
     * @return boolean status
     */
    public boolean checkStatus(HostMachine host) {
        return checkStatus(host.getURIBuilder().setPath(WD_SERVER_STATUS));
    }

    /**
     * Get process id of appium server
     *
     * @return pid
     */
    protected String getPidAppiumProcess(HostMachine host) {
        return CommandExecutor.execute(host, Command.APPIUM_TEMPLATE_GET_PID_APPIUM, host.getPort());
    }

    /**
     * Create appium {@code nohup} command
     *
     * @param hostMachine which {@link HostMachine} we need to start appium server
     * @param command     which command we need transform to {@code nohup} command
     * @return {@code nohup} command
     */
    protected String getNoHupAppiumCommand(HostMachine hostMachine, String command) {
        return String.format(Command.APPIUM_TEMPLATE_NOHUP.getCommandTemplate(), command, hostMachine.getPort());
    }
}
