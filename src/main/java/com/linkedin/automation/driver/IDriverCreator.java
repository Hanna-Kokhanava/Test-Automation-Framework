package com.linkedin.automation.driver;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 10.03.2018
 */
public interface IDriverCreator {
    void createDriver(String deviceLanguage);

    void createDriver(String deviceLanguage, DesiredCapabilities customCapabilities);

    void closeDriver();
}
