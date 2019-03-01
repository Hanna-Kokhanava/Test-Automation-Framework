package com.kokhanava.automation.core.driver.managers.web;

import com.kokhanava.automation.core.browser.BrowserManager;
import com.kokhanava.automation.core.logger.Logger;
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
        var hostMachine = BrowserManager.getCurrentBrowser().getHost();
        var fileManager = FileManager.getInstance(hostMachine);
        var osName = hostMachine.getOs().toString();
        var driverRepository = getRepositoryObject(driverName, driverVersion, osName);

        var driverDirPath = DRIVERS_FOLDER
                + File.separator + driverRepository.getName()
                + File.separator + driverRepository.getVersion();
        var driverExeFileName = driverName + ".exe";
        var driverExeFilePath = driverDirPath + File.separator + driverExeFileName;

        if (!fileManager.isFileExist(driverDirPath, new File(driverExeFilePath), driverExeFileName)) {
            Logger.debug("Start downloading and unzipping driver executable file");
            String zipFilePath = driverDirPath + File.separator + driverName + ".zip";

            fileManager.createSubDirectories(driverDirPath);
            fileManager.downloadFileFromUrl(driverRepository.getLocation(), zipFilePath);
            fileManager.unzipFile(zipFilePath, driverDirPath);
        }

        Logger.debug("Got executable file path for [" + driverExeFileName
                + "] with version [" + driverRepository.getVersion() + "]");
        String filePath = fileManager.getPathToFile(new File(driverDirPath), driverExeFileName);
        return Objects.requireNonNull(filePath, "Driver executable file is not found!");
    }

    /**
     * Returns DriverRepository object based on its driver name, os and version
     *
     * @param driverName    name of driver
     * @param driverVersion version of driver
     * @param currentOsName OS name for current machine
     * @return {@link DriverRepository} instance
     */
    private static DriverRepository getRepositoryObject(String driverName, String driverVersion, String currentOsName) {
        if (CollectionUtils.isEmpty(driverList)) {
            throw new RuntimeException("List of driver repositories is empty!");
        }

        List<DriverRepository> allRepositories = driverList.stream()
                .filter(driver -> driver.getName().equalsIgnoreCase(driverName))
                .filter(driver -> driver.getOs().equalsIgnoreCase(currentOsName))
                .collect(Collectors.toList());

        DriverRepository repository = driverVersion.isBlank()
                ? getRepositoryWithHighestVersion(allRepositories)
                : getRepositoryByVersion(allRepositories, driverVersion);

        return Objects.requireNonNull(repository, "Driver with ID [" + driverName
                + "] and version [" + driverVersion + "] for OS [" + currentOsName + "] was not found in ["
                + REPOSITORIES_CONFIGURATION_FILE + "] configuration file");
    }

    /**
     * Returns DriverRepository object by its version or else null
     *
     * @param allRepositories list of all repositories with specified name and os
     * @param driverVersion   driver version
     * @return {@link DriverRepository} instance
     */
    private static DriverRepository getRepositoryByVersion(List<DriverRepository> allRepositories, String driverVersion) {
        Logger.debug("Looking for driver with [" + driverVersion + "] version");
        return allRepositories.stream()
                .filter(driver -> driver.getVersion().equals(driverVersion))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns DriverRepository object with a highest driver version
     *
     * @param allRepositories list of all repositories with specified name and os
     * @return {@link DriverRepository} instance
     */
    private static DriverRepository getRepositoryWithHighestVersion(List<DriverRepository> allRepositories) {
        Logger.warn("Driver version was not specified explicitly, looking for driver with highest version");
        return allRepositories.stream()
                .max(Comparator.comparing(DriverRepository::getVersion))
                .orElse(null);
    }
}
