package com.linkedin.automation.core.driver.managers.mobile;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.driver.PlatformModules;
import com.linkedin.automation.core.driver.config.IPlatformConfig;
import com.linkedin.automation.core.tools.HostMachine;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.server.browserlaunchers.Sleeper;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created on 01.04.2018
 */
public class AppiumServerManager {
    private static AppiumDriverLocalService service = null;

    private static Injector injector = Guice.createInjector(new PlatformModules());
    private static IPlatformConfig platformConfig = injector.getInstance(IPlatformConfig.class);

    public static void startServer(Device device) {
        DesiredCapabilities desiredCapabilities = platformConfig.getDefaultCapabilitiesForDevice(device);
        HostMachine host = device.getAppiumHostMachine();

        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1");
        builder.usingPort(host.getPortInt());
        builder.withCapabilities(desiredCapabilities);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);

        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }

    public static void stopServer(Device device) {
        System.out.println("Stopping appium server on " + device.getAppiumHostMachine());
        if (!checkIfServerIsRunning(device.getAppiumHostMachine().getPortInt())) {
            System.out.println("Appium server already stopped");
        } else {
            service.stop();
        }
    }

    public static void restartServer(Device device) {
        System.out.println("Restart Appium server");
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
