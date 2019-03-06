package com.kokhanava.automation.core.driver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaOptions;

/**
 * Contains all supported web platforms with its options and driver names
 */
public enum SupportedWebPlatforms {
    //Driver name need to coincide with driver-repositories.xml configuration file

    CHROME("chromedriver", getChromeOptions()),

    FIREFOX("geckodriver", getFirefoxOptions()),

    OPERA("operadriver", getOperaOptions()),

    EDGE("edgedriver", getEdgeOptions());

    private MutableCapabilities options;
    private String driverName;

    SupportedWebPlatforms(String driverName, MutableCapabilities capabilities) {
        this.driverName = driverName;
        this.options = capabilities;
    }

    public MutableCapabilities getOptions() {
        return this.options;
    }

    public String getDriverName() {
        return driverName;
    }

    private static ChromeOptions getChromeOptions() {
        String currentUserName = System.getProperty("user.name");
        ChromeOptions options = new ChromeOptions();
        //TODO change depending on OS
        options.addArguments("user-data-dir=C:/Users/" + currentUserName + "/AppData/Local/Google/Chrome/User Data/Default");
        options.addArguments("--start-maximized");
        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("marionette", false);
        //TODO stub for further options
        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        //TODO stub for further options
        return options;
    }

    private static OperaOptions getOperaOptions() {
        OperaOptions options = new OperaOptions();
        //TODO stub for further options
        //TODO options.setBinary()
        return options;
    }
}
