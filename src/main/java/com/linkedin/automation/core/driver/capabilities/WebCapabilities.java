package com.linkedin.automation.core.driver.capabilities;

import com.linkedin.automation.core.browser.Browser;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 22.01.2019
 */
public class WebCapabilities {

    private final Browser browser;

    public WebCapabilities(Browser browser) {
        this.browser = browser;
    }

    public DesiredCapabilities createBaseCapabilities() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(CapabilityType.BROWSER_NAME, browser.getBrowserName());
        caps.setCapability(CapabilityType.PLATFORM_NAME, browser.getPlatformName());
        return caps;
    }
}
