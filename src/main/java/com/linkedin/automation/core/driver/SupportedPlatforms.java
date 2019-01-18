package com.linkedin.automation.core.driver;

import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public enum SupportedPlatforms {
    chrome(new DesiredCapabilities() {        {
            setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
            setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
        }
    });

    private DesiredCapabilities desiredCapabilities;

    SupportedPlatforms(DesiredCapabilities desiredCapabilities) {
        this.desiredCapabilities = desiredCapabilities;
    }

    public DesiredCapabilities getDesiredCapabilities() {
        return this.desiredCapabilities;
    }

    public ChromeOptions getChromeOptions() {
        String currentUserName = System.getProperty("user.name");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:/Users/" + currentUserName + "/AppData/Local/Google/Chrome/User Data/Default");
        options.addArguments("--start-maximized");
        return options;
    }
}
