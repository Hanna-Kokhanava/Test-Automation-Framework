package com.kokhanava.automation.core.driver.managers.web;

import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.commands.CommandExecutor;
import com.kokhanava.automation.core.tools.files.ProjectDir;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * Created on 07.02.2019
 * Manages drivers repositories from xml configuration file
 */
public class DriverRepositoryManager {

    @XmlRootElement
    private static class Repositories {
        @XmlElement(name = "driver")
        private List<DriverRepository> driverList;
    }

    /**
     * Gets list of all {@link DriverRepository} elements from xml configuration file
     */
    private static List<DriverRepository> driverList = ProjectDir.readFromResource(Repositories.class,
            "driver-repositories.xml").driverList;

    /**
     * Returns url for driver downloading
     *
     * @param hostMachine {@link HostMachine} isntance
     * @param driverName  name of driver
     * @return url string
     */
    public static String getRepositoryURL(HostMachine hostMachine, String driverName) {
        if (driverList.isEmpty()) {
            throw new RuntimeException("List of driver repositories is empty!");
        }

        String osName = Objects.requireNonNull(CommandExecutor.getOsOfMachine(hostMachine),
                "Failed to define OS type of " + hostMachine.getHostname() + " machine").toString();

        //TODO think about universal OS name somewhere
        DriverRepository driverRepository = driverList.stream()
                .filter(driver -> driver.getName().equalsIgnoreCase(driverName) && driver.getOs().equalsIgnoreCase(osName))
                .findFirst()
                .orElse(null);
        return Objects.requireNonNull(driverRepository, "Driver with ID [" + driverName + "] for OS ["
                + osName + "] was not found").getFileLocation();
    }

    public static DriverRepository getRepositoryObjectById(String driverName) {
        if (driverList.isEmpty()) {
            throw new RuntimeException("List of driver repositories is empty!");
        }
        DriverRepository driverRepository = driverList.stream()
                .filter(driver -> driver.getName().equalsIgnoreCase(driverName))
                .findFirst()
                .orElse(null);
        return Objects.requireNonNull(driverRepository, "Driver with ID [" + driverName + "] was not found");
    }
}
