package com.linkedin.automation.core.manager;

import com.linkedin.automation.core.tools.HostMachine;
import com.linkedin.automation.core.tools.files.FileManager;
import com.linkedin.automation.core.tools.files.PropertyLoader;

import java.io.File;

import static com.linkedin.automation.core.tools.files.ResultFolder.APPIUM_FOLDER;

/**
 * Created on 02.04.2018
 */
public class ApplicationManager {
    private static File appFile = new File(PropertyLoader.get(PropertyLoader.MobileProperty.APP_PATH));

    public static String getAbsolutePath() {
        File file = new File(APPIUM_FOLDER + File.separator + getFileName());
        return file.getAbsolutePath();
    }

    public static void uploadApp(HostMachine hostMachine) {
        if (!FileManager.getInstance(hostMachine).isFileExist(APPIUM_FOLDER, appFile, getFileName())) {
            FileManager.getInstance(hostMachine).copyFile(APPIUM_FOLDER, appFile, getFileName());
            System.out.println(getFileName() + " is uploaded in " + APPIUM_FOLDER + " folder on " + hostMachine.getHostname());
        } else {
            System.out.println(getFileName() + " is already exist in " + APPIUM_FOLDER + " folder on " + hostMachine.getHostname());
        }
    }


    private static String getFileName() {
        return appFile.getName().replace(" ", "");
    }
}