package com.linkedin.automation.core.tools.files;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created on 01.04.2018
 */
public class FileManager {

    public enum ResultFolder {
        APPIUM_FOLDER("appium");

        private String folderName;

        ResultFolder(String folderName) {
            this.folderName = folderName;
        }

        @Override
        public String toString() {
            return this.folderName;
        }

        public File getLocalDir() {
            return getDirectory(getRootProjectDir(), this);
        }
    }

    public static boolean isFileExist(ResultFolder folder, File file, String filename) {
        File[] files = folder.getLocalDir().listFiles();
        if (files == null) {
            return false;
        }

        for (File folderFile : files) {
            if (folderFile.length() == file.length() && folderFile.getName().equals(filename)) {
                return true;
            }
        }
        return false;
    }

    public static void copyFile(ResultFolder targetFolder, File sourceFile, String targetFilename) {
        try {
            File dest = new File(getDirectory(getRootProjectDir(), targetFolder)
                    + File.separator + targetFilename);
            dest.delete();
            FileUtils.copyFile(sourceFile, dest);
        } catch (IOException e) {
            System.out.println("Error while trying to copy file...");
        }
    }

    private static File getSubProjectDir(Class<?> clazz) {
        String pathBuildClass = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        return new File(pathBuildClass.replaceFirst("\\/(build|out)\\/.*", ""));
    }

    private static File getRootProjectDir() {
        File dir = new File(System.getProperty("user.dir"));
        File subProjectDir = getSubProjectDir(FileManager.class);
        if (dir.equals(subProjectDir) || !dir.equals(subProjectDir.getParentFile())) {
            return dir.getParentFile();
        }
        return dir;
    }

    private static File getDirectory(File targetDir, ResultFolder folder) {
        File directory = new File(targetDir, folder.toString());
        if (!directory.exists() && !directory.mkdirs()) {
            System.out.println(String.format("Unable to create destination folder: '%1$s'", targetDir.toString() + folder.toString()));
            return null;
        }
        return directory;
    }
}
