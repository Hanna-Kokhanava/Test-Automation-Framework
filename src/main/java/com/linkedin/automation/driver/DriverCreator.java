package com.linkedin.automation.driver;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 10.03.2018
 */
public class DriverCreator implements IDriverCreator {

    @Override
    public void createDriver(String deviceLanguage) {
        createDriver(deviceLanguage, null);
    }

    @Override
    public void createDriver(String deviceLanguage, DesiredCapabilities customCapabilities) {
        DesiredCapabilities capabilities = DriverCapabilities.createCapabilities(deviceLanguage);
        if (null != customCapabilities) {
            capabilities.merge(customCapabilities);
        }

        DriverManager.createDriver(capabilities);
    }

    @Override
    public void closeDriver() {
        DriverManager.closeDriver();
    }
}
