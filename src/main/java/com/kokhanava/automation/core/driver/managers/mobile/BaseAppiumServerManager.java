package com.kokhanava.automation.core.driver.managers.mobile;

import com.kokhanava.automation.core.device.Device;
import com.kokhanava.automation.core.driver.managers.AbstractServerManager;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.commands.Command;
import com.kokhanava.automation.core.tools.commands.CommandExecutor;
import com.kokhanava.automation.core.tools.httpclients.HttpClient;
import org.openqa.selenium.server.browserlaunchers.Sleeper;

/**
 * Created on 06.03.2019
 */
public abstract class BaseAppiumServerManager extends AbstractServerManager implements IAppiumServer {

    /**
     * Checks status of appium server
     *
     * @param hostMachine {@link HostMachine} instance
     * @return current status of server, running - true/false
     */
    public boolean checkStatus(HostMachine hostMachine) {
        return checkStatus(hostMachine.getURIBuilder().setPath(WD_SERVER_STATUS_ENDPOINT));
    }

    /**
     * Stops Appium server on {@link HostMachine}
     *
     * @param host on which {@link HostMachine} appium server need to be started
     */
    public void stopServer(HostMachine host) {
        Logger.debug("Stopping Appium server on " + host);
        String pidAppiumProcess = getPidAppiumProcess(host);
        String exitMessage = killAppiumProcess(host, pidAppiumProcess);

        HttpClient.closeHttpClient();

        if (!exitMessage.isBlank() && !exitMessage.contains("SUCCESS")) {
            Logger.error(String.format("Cannot stop Appium server on %s:%s : ", host.getHostname(), host.getPort()));
        }

        waitForServerCondition(Void -> getPidAppiumProcess(host).equals(""));

        if (!checkStatus(host)) {
            Logger.debug("Appium server was stopped");
        } else {
            Logger.error(String.format("Cannot stop Appium server on %s:%s : ", host.getHostname(), host.getPort()));
        }
    }

    /**
     * Restarts Appium server on {@link HostMachine}
     *
     * @param device {@link Device} for which appium server need to be restarted
     */
    public void restartServer(Device device) {
        Logger.debug("Restart Appium server");
        stopServer(device.getAppiumHostMachine());
        Sleeper.sleepTight(2000);
        startServer(device);
    }

    /**
     * Returns PID of appium server process running on a particular port
     *
     * @param host {@link HostMachine} instance
     * @return PID number
     */
    private String getPidAppiumProcess(HostMachine host) {
        return CommandExecutor.execute(host, Command.APPIUM_GET_PID, host.getPort());
    }

    /**
     * Kills appium process by its PID
     *
     * @param host {@link HostMachine} instance
     * @param pid  appium PID
     * @return command output
     */
    private String killAppiumProcess(HostMachine host, String pid) {
        return CommandExecutor.execute(host, Command.SYSTEM_KILL_PROCESS_BY_PID, pid);
    }
}
