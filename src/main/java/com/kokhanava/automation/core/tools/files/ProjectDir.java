package com.kokhanava.automation.core.tools.files;

import com.kokhanava.automation.core.logger.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

/**
 * Created on 02.04.2018
 */
public class ProjectDir {

    /**
     * Get root project directory
     *
     * @return {@link File} directory of project
     */
    public static File getRootProjectDir() {
        File dir = new File(System.getProperty("user.dir"));
        //TODO will be necessary when there will be submodule for core
//        File subProjectDir = getSubProjectDir(FileManager.class);
//        if (dir.equals(subProjectDir) || !dir.equals(subProjectDir.getParentFile()))
//            return dir.getParentFile();
        return dir;
    }

    /**
     * Gets defined resource as file or null if resource was not found
     *
     * @param identifier resource
     * @return file of target resource
     */
    public static File getProjectResource(String identifier) {
        URL resourceURL = ClassLoader.getSystemClassLoader().getResource(identifier);
        Objects.requireNonNull(resourceURL, String.format("Not found '%s' resource", identifier));

        File resourceFile = null;
        try {
            resourceFile = new File(resourceURL.toURI());
        } catch (URISyntaxException e) {
            Logger.error("URL is not formatted strictly and cannot be converted to a URI", e);
        }
        return Objects.requireNonNull(resourceFile, String.format("Cannot create file instance of '%s'", identifier));
    }

    /**
     * Read from resource
     *
     * @param resource path to XML file
     * @return the resource
     */
    public static <T> T readFromResource(Class<T> resourceClass, String resource) {
        File resourceFile = ProjectDir.getProjectResource(resource);
        T resources;
        try (var stream = new FileInputStream(resourceFile)) {
            System.setProperty("javax.xml.accessExternalDTD", "all");
            resources = (T) JAXBContext.newInstance(resourceClass)
                    .createUnmarshaller()
                    .unmarshal(stream);
        } catch (IOException | JAXBException e) {
            Logger.error("Exception occurred during XML resource file reading", e);
            throw new RuntimeException(e);
        }
        return resources;
    }

    /**
     * Get sub project directory where class located
     *
     * @param clazz class whose project dir need to return
     * @return {@link File} directory of sub project
     */
    private static File getSubProjectDir(Class<?> clazz) {
        String pathBuildClass = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        return new File(pathBuildClass.replaceFirst("\\/(build|out)\\/.*", ""));
    }
}
