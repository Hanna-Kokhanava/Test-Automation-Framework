package com.linkedin.automation.core.creator.web;

import com.linkedin.automation.core.browser.Browser;
import com.linkedin.automation.core.browser.BrowserManager;
import com.linkedin.automation.core.creator.IDriverCreator;
import com.linkedin.automation.core.driver.capabilities.WebCapabilities;
import com.linkedin.automation.core.driver.managers.web.WebDriverManager;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Objects;

/**
 * Created on 21.01.2019
 */
public class WebDriverCreator implements IDriverCreator {

    @Override
    public Browser createDriver() {
        return createDriver(null);
    }

    @Override
    public Browser createDriver(DesiredCapabilities customCapabilities) {
        System.out.println("Creating driver...");
        Browser browser = BrowserManager.getCurrentBrowser();

        // Capabilities based on xml configuration
        DesiredCapabilities capabilities = new WebCapabilities(browser).createBaseCapabilities();
        if (Objects.nonNull(customCapabilities)) {
            capabilities.merge(customCapabilities);
        }
        WebDriverManager.createDriver(capabilities);
        return browser;
    }

    @Override
    public void closeDriver() {
        WebDriverManager.closeDriver();
    }
}
