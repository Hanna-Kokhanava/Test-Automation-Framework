package com.linkedin.automation.core.tools.files;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

import java.io.File;

/**
 * Class which manage zip files
 */
public class ZipManager {

    /**
     * Zipped directory and put it to same directory
     *
     * @param dir {@link File} which local directory must be zipped
     * @return {@link ZipFile} zipped directory
     */
    public static ZipFile zipDirectory(File dir) {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(dir.getParent() + File.separator + dir.getName() + ".zip");
            if (zipFile.getFile().exists() && zipFile.getFile().canRead())
                return zipFile;

            zipFile.createZipFileFromFolder(dir, new ZipParameters(), false, 0);
        } catch (ZipException e) {
            e.printStackTrace();
        }
        return zipFile;
    }
}
