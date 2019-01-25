package com.kokhanava.automation.core.driver.managers.mobile;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.kokhanava.automation.core.device.Device;
import com.kokhanava.automation.core.driver.PlatformModules;
import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.driver.config.IPlatformConfig;
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

    /**
     * Starts Appium server on IP:port address
     *
     * @param device {@link Device}
     */
    public static void startServer(Device device) {
        DesiredCapabilities desiredCapabilities = platformConfig.getDefaultCapabilitiesForDevice(device);
        HostMachine host = device.getAppiumHostMachine();

        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        //TODO think about host.getHostname()
        builder.withIPAddress("127.0.0.1");
        builder.usingPort(host.getPortInt());
        builder.withCapabilities(desiredCapabilities);
        builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);

        service = AppiumDriverLocalService.buildService(builder);
        service.start();
    }

    /**
     * Checks if server runs and stops it
     *
     * @param device {@link Device}
     */
    public static void stopServer(Device device) {
        System.out.println("Stopping appium server on " + device.getAppiumHostMachine());
        if (checkIfServerIsRunning(device.getAppiumHostMachine().getPortInt())) {
            System.out.println("Appium server already stopped");
        } else {
            service.stop();
        }
    }

    /**
     * Stops server, waits for 2 seconds and starts again
     *
     * @param device {@link Device}
     */
    public static void restartServer(Device device) {
        System.out.println("Restart Appium server");
        stopServer(device);
        Sleeper.sleepTight(2);
        startServer(device);
    }

    /**
     * Checks if server is running now
     *
     * @param port port number
     * @return true if server currently runs
     */
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

}
