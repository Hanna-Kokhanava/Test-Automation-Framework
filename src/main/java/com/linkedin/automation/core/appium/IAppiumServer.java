package com.linkedin.automation.core.appium;

/**
 * Created on 15.03.2018
 */
public interface IAppiumServer {
    String WD_SERVER_STATUS = "/wd/hub/status";

    boolean checkStatus();

    void startServer();

    void stopServer();

    void restartServer();
}
