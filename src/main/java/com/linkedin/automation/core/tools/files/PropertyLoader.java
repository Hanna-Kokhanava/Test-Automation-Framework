package com.linkedin.automation.core.tools.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created on 02.04.2018
 */
public final class PropertyLoader {
    public enum Property {

        APP_PATH("app.path"),

        DEVICE_TYPE("device.type"),

        DEVICE_UDID("device.udid"),

        DEVICES_XML("devices.xml");

        private final String propKey;

        Property(String key) {
            propKey = key;
        }

        public String getKey() {
            return propKey;
        }

        @Override
        public String toString() {
            return propKey;
        }
    }

    private static final String TEST_PROPERTIES_PATH = "test.properties";
    private static Properties testProperties;

    private PropertyLoader() {
    }

    public static String get(Property key) {
        return get(key.getKey());
    }

    public static String get(String keyName) {
        String envVarValue = System.getenv(keyName);
        if (!isNullOrEmpty(envVarValue))
            return envVarValue;

        String sysPropValue = System.getProperty(keyName);
        if (!isNullOrEmpty(sysPropValue))
            return sysPropValue;

        String propFromFile = PropertyLoader.getPropertyFromFile(keyName);
        if (!isNullOrEmpty(propFromFile))
            return propFromFile;

        throw new NullPointerException("Unable to resolve '" + keyName + "' property value");
    }

    public static String get(Property key, String defaultValue) {
        return get(key.getKey(), defaultValue);
    }

    /**
     * Gets test property, use default value if property not found
     *
     * @param keyName          the property key
     * @param defaultValue the default value
     * @return the property value
     * @see #get(Property)
     */
    public static String get(String keyName, String defaultValue) {
        try {
            return PropertyLoader.get(keyName);
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }

    /**
     * Gets the test property value.
     *
     * @param keyName the property key
     * @return the test property value
     */
    private static String getPropertyFromFile(String keyName) {
        if (testProperties == null)
            testProperties = PropertyLoader.loadPropertiesFromFile(ProjectDir.getProjectResource(TEST_PROPERTIES_PATH));
        return testProperties.getProperty(keyName);
    }

    /**
     * Load all properties from file.
     *
     * @param propertyFile the path to properties file in classpath
     * @return the properties
     */
    private static Properties loadPropertiesFromFile(File propertyFile) {
        Properties result = new Properties();
        File testPropertiesResourceFile = Objects.requireNonNull(propertyFile,
                String.format("Not found '%s' resource", propertyFile));

        try (InputStream stream = new FileInputStream(testPropertiesResourceFile)) {
            result.load(stream);
        } catch (IOException e) {
            System.out.println("Error while reading properties from " + propertyFile.toString());
        }
        return result;
    }
}
