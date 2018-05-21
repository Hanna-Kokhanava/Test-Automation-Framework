package com.linkedin.automation.core.driver.managers.appiumservice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.driver.PlatformModules;
import com.linkedin.automation.core.driver.config.IPlatformConfig;
import com.linkedin.automation.core.logger.Logger;
import com.linkedin.automation.core.tools.HostMachine;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.server.browserlaunchers.Sleeper;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created on 01.04.2018
 */
public class AppiumDriverLocalServiceManager {
    private static AppiumDriverLocalService service = null;

    private static Injector injector = Guice.createInjector(new PlatformModules());
    private static IPlatformConfig platformConfig = injector.getInstance(IPlatformConfig.class);

    public static void startServer(Device device) {
        Logger.info("Try to start Appium server");
        service = AppiumDriverLocalService.buildService(configureBuilder(device));
        service.start();
    }

    /**
     * Sets arguments to {@link AppiumServiceBuilder} instance
     *
     * @param device {@link Device} instance
     * @return {@link AppiumServiceBuilder} instance
     */
    private static AppiumServiceBuilder configureBuilder(Device device) {
        DesiredCapabilities defaultCapabilities = platformConfig.getDefaultDesiredCapabilitiesForDevice(device);
        HostMachine host = device.getAppiumHostMachine();

        return new AppiumServiceBuilder()
                .withIPAddress(device.getAppiumHostMachine().getNumericHostname())
                .usingPort(host.getPortInt())
                .withCapabilities(defaultCapabilities)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
                .withLogFile(new File("logs" + device.getAppiumHostMachine().getPortInt() + ".log"));
    }

    public static void stopServer(Device device) {
        Logger.info("Stopping appium server on " + device.getAppiumHostMachine().getHostname() + ":"
                + device.getAppiumHostMachine().getPortInt());
        service.stop();
    }

    public static void restartServer(Device device) {
        Logger.info("Try to restart Appium server");
        stopServer(device);
        Sleeper.sleepTight(2);
        startServer(device);
    }

    public static boolean checkIfServerIsRunning(int port) {
        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.close();
        } catch (IOException e) {
            //If control comes here, then it means that the port is in use
            isServerRunning = true;
        }
        return isServerRunning;
    }

    public static AppiumDriverLocalService getService() {
        return service;
    }
}
