package com.linkedin.automation.core.creators;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 10.03.2018
 */
public interface IDriverCreator {
    void createDriver();

    void createDriver(DesiredCapabilities customCapabilities);

    void closeDriver();
}
