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
        private List<Driver> driverList;
    }

    /**
     * Gets list of all {@link Driver} elements from xml configuration file
     */
    private static final List<Driver> driverList = ProjectDir.readFromResource(Repositories.class,
            "driver-repositories.xml").driverList;

    public static String getFileLocation(HostMachine hostMachine, String driverName) {
        String os = Objects.requireNonNull(CommandExecutor.getOsOfMachine(hostMachine),
                "Failed to define OS type").toString();

        for (Driver driver : driverList) {
            if (driver.getOs().equalsIgnoreCase(os) && driver.getId().equalsIgnoreCase(driverName)) {
                return driver.getFileLocation();
            }
        }
        throw new RuntimeException("Driver with ID [" + driverName + "] for OS ["
                + Objects.requireNonNull(os) + "] was not found");
    }


}
