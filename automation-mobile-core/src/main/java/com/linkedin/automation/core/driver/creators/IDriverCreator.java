package com.linkedin.automation.core.driver.creators;

import com.linkedin.automation.core.device.Device;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created on 10.03.2018
 */
public interface IDriverCreator {

    Device createDriver() throws Exception;

    Device createDriver(DesiredCapabilities customCapabilities) throws Exception;

    void closeDriver();
}
