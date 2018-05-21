package com.linkedin.automation.core.tools.files;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.linkedin.automation.core.logger.Logger;
import com.linkedin.automation.core.tools.HostMachine;
import com.linkedin.automation.core.tools.SSHManager;
import com.linkedin.automation.core.tools.commands.Command;
import com.linkedin.automation.core.tools.commands.CommandExecutor;
import net.lingala.zip4j.core.ZipFile;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * File manager which can manage files on remote machine
 */
public class RemoteFileManager extends FileManager {

    public RemoteFileManager(HostMachine hostMachine) {
        super(hostMachine);
    }

    @Override
    void copyFile(String targetFolderPath, File sourceFile, @Nonnull String targetFilename) {
        SSHManager sshManager = new SSHManager(hostMachine);
        try {
            sshManager.connect();
            sshManager.uploadFile(targetFolderPath, sourceFile, targetFilename);
            sshManager.disconnect();
        } catch (JSchException | FileNotFoundException | SftpException e) {
            e.printStackTrace();
        }
    }

    @Override
    boolean isFileExist(String folderPath, File file, String filename) {
        boolean isFileExist = false;
        SSHManager sshManager = new SSHManager(hostMachine);
        try {
            sshManager.connect();
            isFileExist = sshManager.isFileExist(folderPath, file, filename);
            sshManager.disconnect();
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
        return isFileExist;
    }

    @Override
    public void removeFile(String folderPath, @Nonnull String filename) {
        SSHManager sshManager = new SSHManager(hostMachine);
        try {
            sshManager.connect();
            sshManager.removeFile(folderPath, filename);
            sshManager.disconnect();
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        }
    }

    /**
     * Download file from remote machine
     *
     * @param targetFolder     folder on local machine
     * @param sourceFolderPath folder on remote machine
     * @param sourceFileName   name of downloaded file
     * @return {@link File} is download succeed and {@code null} if it failed
     */
    public File downloadFile(File targetFolder, String sourceFolderPath, String sourceFileName) {
        SSHManager sshManager = new SSHManager(hostMachine);
        File file = null;
        try {
            sshManager.connect();
            file = sshManager.downloadFile(targetFolder, sourceFolderPath, sourceFileName);
            sshManager.disconnect();
        } catch (JSchException | SftpException | IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Upload directory to machine
     *
     * @param targetFolderPath in which folder need to upload directory
     * @param sourceDir        which directory need to upload
     */
    public void uploadDir(String targetFolderPath, File sourceDir) {
        ZipFile zipFile = ZipManager.zipDirectory(sourceDir);
        if (zipFile == null) {
            Logger.warn("Cannot zipped directory: " + sourceDir);
            return;
        }

        File zippedDir = zipFile.getFile();
        if (!isFileExist(targetFolderPath, zippedDir, zippedDir.getName())) {
            copyFile(targetFolderPath, zippedDir, zippedDir.getName());
        }
        zippedDir.delete();

        CommandExecutor.executeCommandFromFolder(hostMachine, targetFolderPath, Command.UNZIP_FILE.getCommand(zippedDir.getName()));

        removeFile(targetFolderPath, zippedDir.getName());
    }
}
