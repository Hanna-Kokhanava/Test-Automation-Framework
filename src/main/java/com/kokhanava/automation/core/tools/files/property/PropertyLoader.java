package com.kokhanava.automation.core.tools.files.property;

import com.google.inject.Inject;
import com.kokhanava.automation.core.logger.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created on 02.04.2018
 */
public final class PropertyLoader {

    /**
     * General properties for launching tests
     */
    public enum GeneralProperty {
        AUTOMATION_TYPE("automation.type");

        private final String propKey;

        GeneralProperty(String key) {
            propKey = key;
        }

        public String getKey() {
            return propKey;
        }
    }

    /**
     * Browser properties keys from web.properties file
     */
    public enum BrowserProperty implements IProperty {
        BROWSER_TYPE("browser.type"),

        BROWSERS_XML("browsers.xml");

        private final String propKey;

        @Inject
        BrowserProperty(String key) {
            propKey = key;
        }

        @Override
        public String getKey() {
            return propKey;
        }
    }

    /**
     * Mobile properties keys from mobile.properties file
     */
    public enum MobileProperty implements IProperty {

        APP_PATH("app.path"),

        APP_NAME("app.name"),

        DEVICE_TYPE("device.type"),

        DEVICE_UDID("device.udid"),

        DEVICES_XML("devices.xml"),

        APP_NO_RESET("app.noReset"),

        APP_FULL_RESET("app.fullReset"),

        DEFAULT_TIMEOUT("default.timeout"),

        LAUNCH_TIMEOUT("launch.timeout"),

        DRIVER_URL("driver.url"),

        DRIVER_TYPE("driver.type"),

        DEVICE_READY_TIMEOUT("device.ready.timeout"),

        NEW_COMMAND_TIMEOUT("new.command.timeout"),

        SCROLL_TIMEOUT("scroll.timeout");

        private final String propKey;

        @Inject
        MobileProperty(String key) {
            propKey = key;
        }

        @Override
        public String getKey() {
            return propKey;
        }
    }

    private static final String GENERAL_TEST_PROPERTIES_PATH = "general.properties";
    private static final String MOBILE_TEST_PROPERTIES_PATH = "mobile.properties";
    private static final String WEB_TEST_PROPERTIES_PATH = "web.properties";

    private static Properties generalProperties;
    private static Properties testProperties;

    public PropertyLoader() {
    }

    /**
     * Gets test property, otherwise use default value
     *
     * @param property     the property key
     * @param defaultValue the default value
     * @return property value
     */
    public static String get(IProperty property, String defaultValue) {
        try {
            return PropertyLoader.get(property);
        } catch (NullPointerException e) {
            Logger.warn("Property [" + property.getKey() + "] doesn't exist. " +
                    "Default value [" + defaultValue + "] will be taken");
            return defaultValue;
        }
    }

    /**
     * Gets test property using the first available source in the following order
     * - Environment variable
     * - System property
     * - Custom test property from file
     *
     * @param property the property key
     * @return the property value
     */
    public static String get(IProperty property) {
        String envVarValue = System.getenv(property.getKey());
        if (!isNullOrEmpty(envVarValue)) {
            return envVarValue;
        }

        String sysPropValue = System.getProperty(property.getKey());
        if (!isNullOrEmpty(sysPropValue)) {
            return sysPropValue;
        }

        String propFromFile;
        // If property belongs to MobileProperty interface
        if (property.getClass().equals(MobileProperty.class)) {
            propFromFile = PropertyLoader.getPropertyFromFile(property.getKey(), MOBILE_TEST_PROPERTIES_PATH);
        } else {
            propFromFile = PropertyLoader.getPropertyFromFile(property.getKey(), WEB_TEST_PROPERTIES_PATH);
        }
        Objects.requireNonNull(propFromFile, "Unable to resolve [" + propFromFile + "] property value");
        return propFromFile;
    }

    /**
     * Returns general property value
     *
     * @param generalProperty {@link GeneralProperty} property
     * @return property value
     */
    public static String getGeneralTestProperty(GeneralProperty generalProperty) {
        if (Objects.isNull(generalProperties)) {
            generalProperties = loadPropertiesFromFile(GENERAL_TEST_PROPERTIES_PATH);
        }
        Objects.requireNonNull(generalProperties, "Unable to get general properties from file");

        String propertyValue = generalProperties.getProperty(generalProperty.getKey());
        Objects.requireNonNull(propertyValue, "Unable to resolve [" + propertyValue + "] property value");
        Logger.debug("General test property value for [" + generalProperty + "] key is [" + propertyValue + "]");
        return propertyValue;
    }

    /**
     * Returns property value from file by its key name
     *
     * @param keyName  property key name
     * @param fileName properties file name
     * @return property value
     */
    private static String getPropertyFromFile(String keyName, String fileName) {
        if (Objects.isNull(testProperties)) {
            testProperties = loadPropertiesFromFile(fileName);
        }
        Objects.requireNonNull(testProperties, "Unable to get test properties from file");
        return testProperties.getProperty(keyName);
    }

    /**
     * Loads all properties from given file
     *
     * @param path the path to properties file in classpath
     * @return the properties
     */
    @Nullable
    private static Properties loadPropertiesFromFile(String path) {
        Properties properties = new Properties();

        try (InputStream stream = PropertyLoader.class.getClassLoader().getResourceAsStream(path)) {
            if (Objects.nonNull(stream)) {
                properties.load(stream);
            } else {
                Logger.error("File with path [" + path + "] could not be found");
            }
        } catch (IOException e) {
            Logger.error("Error while reading properties from path [" + path + "]", e);
        }

        return properties.isEmpty() ? null : properties;
    }
}
