package com.kokhanava.automation.core.driver.managers.web;

import com.kokhanava.automation.core.browser.BrowserManager;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.commands.CommandExecutor;
import com.kokhanava.automation.core.tools.files.FileManager;
import com.kokhanava.automation.core.tools.files.ProjectDir;
import org.springframework.util.CollectionUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.kokhanava.automation.core.tools.files.ResultFolder.DRIVERS_FOLDER;

/**
 * Created on 07.02.2019
 * Manages drivers repositories from xml configuration file
 */
public class DriverRepositoryManager {
    private static final String REPOSITORIES_CONFIGURATION_FILE = "driver-repositories.xml";

    @XmlRootElement
    private static class Repositories {
        @XmlElement(name = "driver")
        private List<DriverRepository> driverList;
    }

    /**
     * Gets list of all {@link DriverRepository} elements from xml configuration file
     */
    private static List<DriverRepository> driverList = ProjectDir.readFromResource(Repositories.class,
            REPOSITORIES_CONFIGURATION_FILE).driverList;

    /**
     * Download and unzip an appropriate driver
     * SSL problem is temporary solved via certificate validation ignorance
     *
     * @param driverName name of required driver
     * @return driver executable file
     */
    public static String getDriverExecutableFilePath(String driverName, String driverVersion) {
        DriverRepository repository = getRepositoryObjectById(driverName);
        HostMachine host = BrowserManager.getCurrentBrowser().getHost();
        FileManager fileManager = FileManager.getInstance(host);

        String directoryPath = DRIVERS_FOLDER
                + File.separator + repository.getName()
                + File.separator + repository.getVersion();
        String driverExeFileName = driverName + ".exe";

        if (!fileManager.isFileExist(DRIVERS_FOLDER, new File(driverName), driverName)) {
            Logger.debug("Start to download and unzip driver executable file");
            String zipFilePath = directoryPath + File.separator + driverName + ".zip";

            fileManager.createSubDirectories(directoryPath);
            fileManager.downloadFileFromUrl(getRepositoryURL(host, driverName, driverVersion), zipFilePath);
            fileManager.unzipFile(zipFilePath, directoryPath);
        }

        Logger.debug("Got executable file path for [" + driverExeFileName + "] with version [" + repository.getVersion() + "]");
        String filePath = fileManager.getPathToFile(new File(directoryPath), driverExeFileName);
        return Objects.requireNonNull(filePath, "Driver executable file is not found!");
    }

    /**
     * Returns URL for driver exe downloading
     *
     * @param hostMachine {@link HostMachine} instance
     * @param driverName  name of driver
     * @return url string
     */
    private static String getRepositoryURL(HostMachine hostMachine, String driverName, String driverVersion) {
        if (CollectionUtils.isEmpty(driverList)) {
            throw new RuntimeException("List of driver repositories is empty!");
        }

        String osName = CommandExecutor.getOsOfMachine(hostMachine).toString();

        var repositories = driverList.stream()
                .filter(driver -> driver.getName().equalsIgnoreCase(driverName)
                        && driver.getOs().equalsIgnoreCase(osName))
                .collect(Collectors.toList());

        if (driverVersion.isEmpty()) {
            var repository = repositories.stream()
                    .max(Comparator.comparing(DriverRepository::getVersion))
                    .orElseThrow(() -> new NullPointerException("Driver with highest version was not found"));

            return repository.getFileLocation();
        } else {
            var repository = repositories.stream()
                    .filter(driver -> driver.getVersion() == Double.parseDouble(driverVersion))
                    .findFirst()
                    .orElseThrow(() -> new NullPointerException("Driver with ID [" + driverName
                            + "] and version [" + driverVersion + "] for OS [" + osName + "] was not found"));
            return repository.getFileLocation();
        }
    }

    /**
     * Returns {@link DriverRepository} instance by its driver ID
     *
     * @param driverName name of driver
     * @return {@link DriverRepository}
     */
    private static DriverRepository getRepositoryObjectById(String driverName) {
        if (CollectionUtils.isEmpty(driverList)) {
            throw new RuntimeException("List of driver repositories is empty!");
        }

        return driverList.stream()
                .filter(driver -> driver.getName().equalsIgnoreCase(driverName))
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Driver with name [" + driverName
                        + "] was not found in configuration file"));
    }
}
