package com.kokhanava.automation.core.driver.managers.mobile;

import com.kokhanava.automation.core.device.Device;
import com.kokhanava.automation.core.tools.HostMachine;

/**
 * Created on 06.03.2019
 */
public interface IAppiumServer {

    String WD_SERVER_STATUS_ENDPOINT = "/wd/hub/status";

    /**
     * Starts appium server on {@link HostMachine}
     *
     * @param device for which {@link Device} appium server need to be started
     */
    void startServer(Device device);

    /**
     * Stops appium server on {@link HostMachine}
     *
     * @param host on which {@link HostMachine} appium server need to be started
     */
    void stopServer(HostMachine host);

    /**
     * Restarts appium server on {@link HostMachine}
     *
     * @param device for which {@link Device} appium server need to be started
     */
    void restartServer(Device device);

    /**
     * Checks status of appium server
     *
     * @return boolean status
     */
    boolean checkStatus(HostMachine hostMachine);
}
