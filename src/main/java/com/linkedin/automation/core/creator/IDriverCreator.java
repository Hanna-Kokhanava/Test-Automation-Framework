package com.linkedin.automation.core.creator;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 10.03.2018
 */
public interface IDriverCreator <T> {
    T createDriver() throws Exception;

    T createDriver(DesiredCapabilities customCapabilities) throws Exception;

    void closeDriver();
}
