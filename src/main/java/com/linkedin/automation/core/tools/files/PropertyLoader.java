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

    public enum GeneralProperty implements IProperty {
        AUTOMATION_TYPE("automation.type");

        private final String propKey;

        GeneralProperty(String key) {
            propKey = key;
        }

        public String getKey() {
            return propKey;
        }
    }

    //BrowserPropery keys from browser.properties

    public enum BrowserProperty implements IProperty {
        BROWSER_TYPE("browser.type");

        private final String propKey;

        BrowserProperty(String key) {
            propKey = key;
        }

        public String getKey() {
            return propKey;
        }
    }

    // MobileProperty keys from mobile.properties file
    public enum MobileProperty implements IProperty {

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

        NEW_COMMAND_TIMEOUT("new.command.timeout"),

        SCROLL_TIMEOUT("scroll.timeout");

        private final String propKey;

        MobileProperty(String key) {
            propKey = key;
        }

        public String getKey() {
            return propKey;
        }
    }

    private static final String MOBILE_TEST_PROPERTIES_PATH = "mobile.properties";
    private static final String BROWSER_TEST_PROPERTIES_PATH = "browser.properties";
    private static Properties testProperties;

    private PropertyLoader() {
    }

    /**
     * Gets test property, otherwise use default value
     *
     * @param key          the property key
     * @param defaultValue the default value
     * @return property value
     */
    public static String get(MobileProperty key, String defaultValue) {
        try {
            return PropertyLoader.get(key);
        } catch (NullPointerException e) {
            return defaultValue;
        }
    }

    /**
     * Gets test property using the first available source
     *
     * @param key the property key
     * @return the property value
     */
    public static String get(MobileProperty key) {
        String envVarValue = System.getenv(key.getKey());
        if (!isNullOrEmpty(envVarValue))
            return envVarValue;

        String sysPropValue = System.getProperty(key.getKey());
        if (!isNullOrEmpty(sysPropValue))
            return sysPropValue;

        String propFromFile = PropertyLoader.getPropertyFromFile(key);
        if (!isNullOrEmpty(propFromFile))
            return propFromFile;

        throw new NullPointerException("Unable to resolve '" + key + "' property value");
    }

    /**
     * Returns property value from file by its key name
     *
     * @param keyName - property key
     */
    private static String getPropertyFromFile(MobileProperty keyName) {
        if (testProperties == null)
            testProperties = PropertyLoader.loadPropertiesFromFile(ProjectDir.getProjectResource(MOBILE_TEST_PROPERTIES_PATH));
        return testProperties.getProperty(keyName.getKey());
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
