package com.kokhanava.automation.core.driver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;

/**
 * Contains all supported web platforms with its options
 */
public enum SupportedWebPlatforms {

    CHROME(getChromeOptions()),

    FIREFOX(getFirefoxOptions()),

    IE10(getIEOptions());

    private MutableCapabilities options;

    SupportedWebPlatforms(MutableCapabilities capabilities) {
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
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        //TODO stub for further options
        return options;
    }

    private static MutableCapabilities getIEOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        //TODO stub for further options
        return options;
    }
}