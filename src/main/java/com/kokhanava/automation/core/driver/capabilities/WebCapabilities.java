package com.kokhanava.automation.core.driver.capabilities;

import com.kokhanava.automation.core.browser.Browser;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *  Creates capabilities for Appium driver based on properties in configuration files
 * Created on 22.01.2019
 */
public class WebCapabilities {

    private final Browser browser;

    public WebCapabilities(Browser browser) {
        this.browser = browser;
    }

    /**
     * Creates base capabilities for web driver
     *
     * @return {@link DesiredCapabilities} capabilities
     */
    public DesiredCapabilities createBaseCapabilities() {
        var caps = new DesiredCapabilities();
        caps.setCapability(CapabilityType.BROWSER_NAME, browser.getBrowserName());
        caps.setCapability(CapabilityType.PLATFORM_NAME, browser.getPlatformName());
        return caps;
    }
}
