package com.linkedin.automation.core.tools.files;

import com.linkedin.automation.core.tools.HostMachine;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 01.04.2018
 */
public abstract class FileManager {

    private static Map<HostMachine, FileManager> managerMap = new HashMap<>();
    protected final HostMachine hostMachine;

    FileManager(HostMachine hostMachine) {
        this.hostMachine = hostMachine;
    }

    public static FileManager getInstance(HostMachine hostMachine) {
        if (!managerMap.containsKey(hostMachine)) {
            FileManager instance = hostMachine.isRemote() ? new RemoteFileManager(hostMachine) : new LocalFileManager();
            managerMap.put(hostMachine, instance);
        }
        return managerMap.get(hostMachine);
    }

    /**
     * Copy file to folder
     *
     * @param targetFolderPath to which folder file must be copied
     * @param sourceFile       {@link File} which local file must be copied
     * @param targetFilename   how copied file must be named
     */
    abstract void copyFile(String targetFolderPath, File sourceFile, @Nonnull String targetFilename);

    public void copyFile(ResultFolder targetFolder, File sourceFile, @Nonnull String targetFilename) {
        copyFile(targetFolder.getPathToFolder(hostMachine), sourceFile, targetFilename);
    }

    /**
     * Check what file exist and same on machine.
     *
     * @param folderPath in which folder need to check file
     * @param file       verifiable file must be the same as local {@link File}
     * @param filename   how verifiable file must be named
     * @return {@code true} if verifiable file have the same filename and file size
     */
    abstract boolean isFileExist(String folderPath, File file, String filename);

    public boolean isFileExist(ResultFolder targetFolder, File sourceFile, String filename) {
        return isFileExist(targetFolder.getPathToFolder(hostMachine), sourceFile, filename);
    }

    /**
     * Remove file in root directory on remote machine
     *
     * @param folderPath in which folder need to remove file
     * @param filename   which file must be removed
     */
    abstract void removeFile(String folderPath, @Nonnull String filename);

    public void removeFile(ResultFolder folder, @Nonnull String filename) {
        removeFile(folder.getPathToFolder(hostMachine), filename);
    }

    public static BufferedWriter getFileWriter(File file, OpenOption... options) throws IOException {
        if (file == null || file.isDirectory()) {
            System.out.println("Cannot append to file");
            throw new RuntimeException("File is failed");
        }
        return Files.newBufferedWriter(Paths.get(file.toURI()), Charset.forName("UTF8"), options);
    }
}
