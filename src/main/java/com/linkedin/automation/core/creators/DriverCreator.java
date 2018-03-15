package com.linkedin.automation.core.creators;

import com.linkedin.automation.core.driver.DriverManager;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 10.03.2018
 */
public class DriverCreator implements IDriverCreator {

    @Override
    public void createDriver() {
        createDriver(null);
    }

    @Override
    public void createDriver(DesiredCapabilities customCapabilities) {
        DesiredCapabilities capabilities = DriverCapabilities.createCapabilities();
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
