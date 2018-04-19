package com.linkedin.automation.core.tools.files;

import com.linkedin.automation.core.tools.HostMachine;
import org.testng.ITestResult;

import java.io.File;
import java.util.Objects;

/**
 * Enum include basic result folders.
 * On local machine from root project directory
 * On remote machine from user home directory
 */
public enum ResultFolder {

    APPIUM_FOLDER("appium"),
    ROOT_FOLDER(".");

    private String folderName;

    ResultFolder(String folderName) {
        this.folderName = folderName;
    }

    @Override
    public String toString() {
        return this.folderName;
    }

    /**
     * Get file of result folder in project root directory
     *
     * @return {@link File} of folder
     */
    public File getLocalDir() {
        return getFolderInLocalDir(ProjectDir.getRootProjectDir());
    }

    /**
     * Get file of result folder in target directory.
     * If folder is not exist, create it.
     *
     * @param targetDir {@link File} directory, where folder must be
     * @return {@link File} of folder
     */
    public File getFolderInLocalDir(File targetDir) {
        File directory = new File(targetDir, this.toString());
        if (!directory.exists() && !directory.mkdirs()) {
            System.out.println(String.format("Unable to create destination folder: '%1$s'", targetDir.toString() + this.toString()));
            return null;
        }
        return directory;
    }

    /**
     * Get path to folder on machine
     * If folder is not exist, create it.
     *
     * @param hostMachine on which {@link HostMachine} need to get path
     * @return absolute path to folder
     */
    public String getPathToFolder(HostMachine hostMachine) {
        return (hostMachine != null && hostMachine.isRemote())
                ? getRemoteDirPath(hostMachine)
                : getLocalDir().getAbsolutePath();
    }

    /**
     * Get path to folder in remote.
     * If folder is not exist, create it.
     *
     * @return {@link String} path to folder
     */
    private String getRemoteDirPath(HostMachine hostMachine) {
        //TODO stab, need to implement SSHManager
        return "";
    }

    /**
     * Get result file inside {@link ResultFolder} output path
     *
     * @param result {@link ITestResult} testng result
     * @return path to result
     */
    public String getResultFilePath(ITestResult result) {
        final String className = result.getTestClass().getRealClass().getSimpleName();
        final String methodName = result.getMethod().getMethodName();
        return Objects.requireNonNull(this.getLocalDir(), "The local directory is not defined.").getAbsolutePath()
                + File.separator
                + System.currentTimeMillis()
                + "_" + className
                + "." + methodName;
    }

}