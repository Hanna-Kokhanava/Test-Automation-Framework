package com.linkedin.automation.core.logger;

import org.apache.logging.log4j.LogManager;

public final class Logger {
    private static final String DEFAULT_LOGGER_NAME = "com.linkedin.automation";
    private static final ThreadLocal<org.apache.logging.log4j.Logger> LOGGER = ThreadLocal.withInitial(() -> LogManager.getLogger(DEFAULT_LOGGER_NAME));

    private Logger() {
        throw new AssertionError("Suppress default constructor for noninstantiability");
    }

    public static void info(String message) {
        LOGGER.get().info(message);
    }

    public static void info(String message, Throwable error) {
        LOGGER.get().info(message, error);
    }

    public static void debug(String message) {
        LOGGER.get().debug(message);
    }

    public static void debug(String message, Throwable error) {
        LOGGER.get().debug(message, error);
    }

    public static void warn(String message) {
        LOGGER.get().warn(message);
    }

    public static void warn(String message, Object... object) {
        LOGGER.get().warn(message, object);
    }

    public static void error(String message) {
        LOGGER.get().error(message);
    }

    public static void error(String message, Throwable error) {
        LOGGER.get().error(message, error);
    }

}
