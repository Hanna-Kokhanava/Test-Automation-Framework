package com.kokhanava.automation.core.creator;

import com.kokhanava.automation.core.browser.Browser;
import com.kokhanava.automation.core.browser.BrowserManager;
import com.kokhanava.automation.core.driver.capabilities.WebCapabilities;
import com.kokhanava.automation.core.driver.managers.web.WebDriverManager;
import com.kokhanava.automation.core.logger.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Objects;

/**
 * Implements functionality of WebDriver creation with capabilities and driver closing
 * Created on 21.01.2019
 */
public class WebDriverCreator implements IDriverCreator {

    /**
     * {@inheritDoc}
     * Creates WebDriver
     *
     * @return {@link Browser} instance
     */
    @Override
    public Browser createDriver() {
        return createDriver(null);
    }

    /**
     * {@inheritDoc}
     * Creates WebDriver
     *
     * @return current {@link Browser} instance
     */
    @Override
    public Browser createDriver(DesiredCapabilities customCapabilities) {
        Browser browser = BrowserManager.getCurrentBrowser();
        DesiredCapabilities capabilities = createCapabilitiesForWeb(browser);

        if (Objects.nonNull(customCapabilities)) {
            capabilities.merge(customCapabilities);
        }

        Logger.debug("Creating driver for [" + browser.getBrowserName() + "] browser on [" +
                browser.getHost().getHostname() + "] host");
        WebDriverManager.createDriver(capabilities);
        return browser;
    }

    /**
     * Closes WebDriver
     */
    @Override
    public void closeDriver() {
        Logger.debug("Quitting driver");
        WebDriverManager.closeDriver();
    }

    /**
     * Creates base capabilities for WebDriver
     *
     * @param browser {@link Browser} instance
     * @return {@link DesiredCapabilities} instance
     */
    private DesiredCapabilities createCapabilitiesForWeb(Browser browser) {
        Logger.debug("Create base capabilities for [" + browser.getBrowserName() + "] browser on [" +
                browser.getHost().getHostname() + "] host");
        WebCapabilities webCapabilities = new WebCapabilities(browser);
        return webCapabilities.createBaseCapabilities();
    }
}
