package com.kokhanava.automation.core.driver.managers.web;

import com.kokhanava.automation.core.browser.BrowserManager;
import com.kokhanava.automation.core.logger.Logger;
import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.commands.CommandExecutor;
import com.kokhanava.automation.core.tools.files.FileManager;
import com.kokhanava.automation.core.tools.files.ProjectDir;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;
import java.util.Objects;

import static com.kokhanava.automation.core.tools.files.ResultFolder.DRIVERS_FOLDER;

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
     * Download and unzip an appropriate driver
     * SSL problem is temporary solved via certificate validation ignorance
     *
     * @param driverName name of required driver
     * @return driver executable file
     */
    public static String getDriverExecutableFilePath(String driverName) {
        Logger.debug("Getting executable file path for [" + driverName + "]");
        HostMachine host = BrowserManager.getCurrentBrowser().getHost();
        FileManager fileManager = FileManager.getInstance(host);

        DriverRepository repository = getRepositoryObjectById(driverName);
        String driverFileName = repository.getName() + repository.getVersion();
        String zipFileDriverName = driverFileName + ".zip";
        String zipFilePath = DRIVERS_FOLDER + File.separator + zipFileDriverName;

        if (!fileManager.isFileExist(DRIVERS_FOLDER, new File(zipFilePath), zipFileDriverName)) {
            Logger.debug("Start to download and unzip driver executable file");
            String driverRepositoryUrl = getRepositoryURL(host, driverName);
            fileManager.downloadFileFromUrl(driverRepositoryUrl, zipFilePath);
            fileManager.unzipFile(zipFilePath, DRIVERS_FOLDER.getPathToFolder(host));
        }

        return DRIVERS_FOLDER + File.separator + driverFileName + ".exe";
    }

    public static void manageDriverExeFilesName() {
        //TODO implement name set up - "driver + version"
        //TODO not in unzip method to don't mix logic
    }

    /**
     * Returns URL for driver exe downloading
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

    /**
     * Returns {@link DriverRepository} instance by its driver ID
     *
     * @param driverName name of driver
     * @return {@link DriverRepository}
     */
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
