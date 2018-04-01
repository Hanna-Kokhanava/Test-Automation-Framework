package com.linkedin.automation.core.tools.files;

import java.io.File;

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

    private static File getSubProjectDir(Class<?> clazz) {
        String pathBuildClass = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        return new File(pathBuildClass.replaceFirst("\\/(build|out)\\/.*", ""));
    }

    private static File getRootProjectDir() {
        File dir = new File(System.getProperty("user.dir"));
        File subProjectDir = getSubProjectDir(FileManager.class);
        if (dir.equals(subProjectDir) || !dir.equals(subProjectDir.getParentFile()))
            return dir.getParentFile();
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
