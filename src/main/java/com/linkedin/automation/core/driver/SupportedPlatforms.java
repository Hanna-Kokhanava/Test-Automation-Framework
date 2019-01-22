package com.linkedin.automation.core.driver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;

public enum SupportedPlatforms {

    CHROME(getChromeOptions()),

    FIREFOX(getFirefoxOptions()),

    IE10(getIEOptions());

    private MutableCapabilities options;

    SupportedPlatforms(MutableCapabilities capabilities) {
        this.options = capabilities;
    }

    public MutableCapabilities getOptions() {
        return this.options;
    }

    private static ChromeOptions getChromeOptions() {
        String currentUserName = System.getProperty("user.name");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=C:/Users/" + currentUserName + "/AppData/Local/Google/Chrome/User Data/Default");
        options.addArguments("--start-maximized");
        options.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX);
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
        return options;
    }

    private static MutableCapabilities getIEOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability(CapabilityType.BROWSER_NAME, BrowserType.IE);
        options.setCapability(CapabilityType.PLATFORM_NAME, Platform.ANY);
        return options;
    }
}
