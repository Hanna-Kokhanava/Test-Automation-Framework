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

    // Property keys from .properties file
    public enum Property {

        APP_PATH("app.path"),

        DEVICE_TYPE("device.type"),

        DEVICE_UDID("device.udid"),

        DEVICES_XML("devices.xml"),

        APP_NO_RESET("app.noReset"),

        APP_FULL_RESET("app.fullReset"),

        DEFAULT_TIMEOUT("default.timeout"),

        LAUNCH_TIMEOUT("launch.timeout"),

        MULTY_TREAD_DRIVER_URL("driver.url"),

        DRIVER_TYPE("driver.type"),

        DEVICE_READY_TIMEOUT("device.ready.timeout"),

        NEW_COMMAND_TIMEOUT("new.command.timeout");

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

    public static String get(Property key, String defaultValue) {
        return get(key.getKey(), defaultValue);
    }

    private static String get(String keyName, String defaultValue) {
        try {
            return PropertyLoader.get(keyName);
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }

    private static String get(String keyName) {
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

    /**
     * Returns property value from file by its key name
     *
     * @param keyName - property key
     */
    private static String getPropertyFromFile(String keyName) {
        if (testProperties == null)
            testProperties = PropertyLoader.loadPropertiesFromFile(ProjectDir.getProjectResource(TEST_PROPERTIES_PATH));
        return testProperties.getProperty(keyName);
    }

    /**
     * Loads properties from given file as property list
     *
     * @param propertyFile - file which contains properties
     */
    private static Properties loadPropertiesFromFile(File propertyFile) {
        Properties result = new Properties();
        File testPropertiesResourceFile = Objects.requireNonNull(propertyFile,
                String.format("Not found '%s' resource file", propertyFile));

        try (InputStream stream = new FileInputStream(testPropertiesResourceFile)) {
            result.load(stream);
        } catch (IOException e) {
            System.out.println("Error while reading properties from " + propertyFile.toString());
        }
        return result;
    }
}
