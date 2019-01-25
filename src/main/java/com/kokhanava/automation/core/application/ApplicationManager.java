package com.kokhanava.automation.core.application;

import com.kokhanava.automation.core.tools.HostMachine;
import com.kokhanava.automation.core.tools.files.FileManager;
import com.kokhanava.automation.core.tools.files.property.PropertyLoader;

import java.io.File;

import static com.kokhanava.automation.core.tools.files.ResultFolder.APPIUM_FOLDER;

/**
 * Created on 02.04.2018
 */
public class ApplicationManager {
    private static File appFile = new File(PropertyLoader.get(PropertyLoader.MobileProperty.APP_PATH));

    /**
     * Returns absolute path to app file
     * Placed in Appium folder in root
     *
     * @return absolute path to app file
     */
    public static String getAbsolutePath() {
        File file = new File(APPIUM_FOLDER + File.separator + getFileName());
        return file.getAbsolutePath();
    }

    /**
     * Uploads app file (.apk or .ipa) to the Appium project folder
     *
     * @param hostMachine {@link HostMachine}
     */
    public static void uploadApp(HostMachine hostMachine) {
        if (!FileManager.getInstance(hostMachine).isFileExist(APPIUM_FOLDER, appFile, getFileName())) {
            FileManager.getInstance(hostMachine).copyFile(APPIUM_FOLDER, appFile, getFileName());
            System.out.println(getFileName() + " is uploaded in " + APPIUM_FOLDER + " folder on " + hostMachine.getHostname());
        } else {
            System.out.println(getFileName() + " is already exist in " + APPIUM_FOLDER + " folder on " + hostMachine.getHostname());
        }
    }

    /**
     * Removed all spaces in the file name
     */
    private static String getFileName() {
        return appFile.getName().replace(" ", "");
    }
}
