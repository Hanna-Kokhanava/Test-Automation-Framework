package com.linkedin.automation.core.tools.files;

import javax.annotation.Nullable;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
        File subProjectDir = getSubProjectDir(FileManager.class);
        if (dir.equals(subProjectDir) || !dir.equals(subProjectDir.getParentFile()))
            return dir.getParentFile();
        return dir;
    }

    /**
     * Gets defined resource as file or null if resource was not found
     *
     * @param identifier resource
     * @return file of target resource
     */
    @Nullable
    public static File getProjectResource(String identifier) {
        URL resourceURL = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(identifier),
                String.format("Not found '%s' resource", identifier));
        File resourceFile;
        try {
            resourceFile = new File(resourceURL.toURI());
        } catch (URISyntaxException e) {
            resourceFile = null;
        }
        return resourceFile;
    }

    /**
     * Read from resource
     *
     * @param resource path to XML file
     * @return the resource
     */
    public static <T> T readFromResource(Class<T> resourceClass, String resource) {
        File resourceFile = Objects.requireNonNull(ProjectDir.getProjectResource(resource),
                String.format("Not found '%s' resource", resource));

        T resources;
        try {
            System.setProperty("javax.xml.accessExternalDTD", "all");
            InputStream stream = new FileInputStream(resourceFile);
            resources = (T) JAXBContext.newInstance(resourceClass)
                    .createUnmarshaller()
                    .unmarshal(stream);
            stream.close();
        } catch (IOException | JAXBException e) {
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
