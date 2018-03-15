package com.linkedin.automation.core.driver;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.linkedin.automation.core.appium.AppiumServer;
import com.linkedin.automation.core.appium.IAppiumServer;
import com.linkedin.automation.core.creators.DriverCreator;
import com.linkedin.automation.core.creators.IDriverCreator;
import com.linkedin.automation.core.driver.dependencies.AppiumDependencies;
import com.linkedin.automation.core.driver.dependencies.IDependencies;

/**
 * Created on 15.03.2018
 */
public class DriverModules extends AbstractModule {
    @Override
    protected void configure() {
        bind(IDriverCreator.class).to(DriverCreator.class).in(Scopes.SINGLETON);
        bind(IAppiumServer.class).to(AppiumServer.class).in(Scopes.SINGLETON);
        bind(IDependencies.class).to(AppiumDependencies.class).in(Scopes.SINGLETON);
    }
}
