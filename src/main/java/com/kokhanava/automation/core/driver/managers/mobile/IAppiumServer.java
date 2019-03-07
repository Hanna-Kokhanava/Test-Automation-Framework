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
     * @param device for which {@link Device} we need to start server
     */
    void startServer(Device device);

    /**
     * Stops appium server on {@link HostMachine}
     *
     * @param host on which {@link HostMachine} we need to stop appium server
     */
    void stopServer(HostMachine host);

    /**
     * Checks status of appium server
     *
     * @return boolean status
     */
    boolean checkStatus(HostMachine hostMachine);
}
