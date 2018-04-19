package com.linkedin.automation.core.tools.files;

import org.apache.commons.io.FileUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

/**
 * File manager which can manage files on local machine
 */
public class LocalFileManager extends FileManager {

    public LocalFileManager() {
        super(null);
    }

    /**
     * Copy file to target directory
     *
     * @param sourceFile     {@link File} which local file must be copied
     * @param targetFilename how copied file must be named
     */
    @Override
    public void copyFile(String targetFolderPath, File sourceFile, @Nonnull String targetFilename) {
        try {
            File dest = new File(targetFolderPath + File.separator + targetFilename);
            dest.delete();
            FileUtils.copyFile(sourceFile, dest);
        } catch (IOException e) {
            System.out.println("Error while trying to copy file...");
        }
    }

    @Override
    public boolean isFileExist(String folderPath, File file, String filename) {
        File[] files = new File(folderPath).listFiles();
        if (files == null)
            return false;

        for (File folderFile : files) {
            if (folderFile.length() == file.length() && folderFile.getName().equals(filename))
                return true;
        }
        return false;
    }

    @Override
    void removeFile(String folderPath, @Nonnull String filename) {
        File file = new File(folderPath + File.separator + filename);
        file.delete();
    }

}