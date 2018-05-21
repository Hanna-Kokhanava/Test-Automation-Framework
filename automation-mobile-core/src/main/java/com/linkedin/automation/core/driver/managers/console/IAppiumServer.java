package com.linkedin.automation.core.driver.managers.console;

import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.tools.HostMachine;

import java.util.List;

public interface IAppiumServer {

    String WD_SERVER_STATUS = "/wd/hub/status";

    /**
     * Check status of appium server
     *
     * @return boolean status
     */
    boolean checkStatus(HostMachine hostMachine);

    /**
     * Start appium server on {@link HostMachine}
     *
     * @param device for which {@link Device} we need to start server
     */
    void startServer(Device device);

    /**
     * Stop appium server on {@link HostMachine}
     *
     * @param host on which {@link HostMachine} we need to stop appium server
     */
    void stopServer(HostMachine host);

    /**
     * Restart appium server on {@link HostMachine}
     *
     * @param device for which {@link Device} we need to restart appium server
     */
    void restartServer(Device device);

    /**
     * Restart appium servers for devices
     *
     * @param devices {@link Device} list
     */
    void restartServers(List<Device> devices);
}
