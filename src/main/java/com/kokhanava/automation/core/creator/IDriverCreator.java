package com.kokhanava.automation.core.creator;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 10.03.2018
 */
public interface IDriverCreator<T> {

    /**
     * Creates driver without custom capabilities
     *
     * @return {@link T} instance of entity (Browser, Device) depending on the testing type (web, mobile)
     */
    T createDriver() throws Exception;

    /**
     * Creates driver with provided custom {@link DesiredCapabilities} capabilities
     * Merges base capabilities with custom (if exist)
     *
     * @param customCapabilities {@link DesiredCapabilities}
     * @return {@link T} instance of entity (Browser, Device) depending on the testing type (web, mobile)
     */
    T createDriver(DesiredCapabilities customCapabilities) throws Exception;

    /**
     * Closes driver
     */
    void closeDriver();
}
