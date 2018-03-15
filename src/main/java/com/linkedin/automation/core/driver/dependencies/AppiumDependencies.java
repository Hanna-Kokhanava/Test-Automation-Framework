package com.linkedin.automation.core.driver.dependencies;

import com.google.inject.Inject;
import com.linkedin.automation.core.appium.IAppiumServer;

/**
 * Created on 15.03.2018
 */
public class AppiumDependencies implements IDependencies {
    @Inject
    private IAppiumServer appiumServer;

    @Override
    public void configureDependencies() {
        if (!appiumServer.checkStatus()) {
            appiumServer.startServer();
        } else {
            System.out.println("Appium server was already started");
        }
    }
}
