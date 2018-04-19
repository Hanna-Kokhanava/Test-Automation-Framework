package com.linkedin.automation.core.tools.files;

import com.linkedin.automation.core.tools.HostMachine;

import javax.annotation.Nonnull;
import java.io.File;


//Stab for some time
public class RemoteFileManager extends FileManager {

    RemoteFileManager(HostMachine hostMachine) {
        super(hostMachine);
    }

    @Override
    void copyFile(String targetFolderPath, File sourceFile, @Nonnull String targetFilename) {

    }

    @Override
    boolean isFileExist(String folderPath, File file, String filename) {
        return false;
    }

    @Override
    public void removeFile(String folderPath, @Nonnull String filename) {

    }
}
