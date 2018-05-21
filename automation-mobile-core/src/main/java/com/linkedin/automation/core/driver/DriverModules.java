package com.linkedin.automation.core.driver;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.linkedin.automation.core.driver.creators.AppiumDriverCreator;
import com.linkedin.automation.core.driver.creators.IDriverCreator;
import com.linkedin.automation.core.driver.dependencies.AppiumDependencies;
import com.linkedin.automation.core.driver.dependencies.IDependencies;
import com.linkedin.automation.core.driver.managers.console.AppiumServerManager;
import com.linkedin.automation.core.driver.managers.console.IAppiumServer;

/**
 * Created on 15.03.2018
 */
public class DriverModules extends AbstractModule {
    @Override
    protected void configure() {
        bind(IDriverCreator.class).to(AppiumDriverCreator.class).in(Scopes.SINGLETON);
        bind(IDependencies.class).to(AppiumDependencies.class).in(Scopes.SINGLETON);
        bind(IAppiumServer.class).to(AppiumServerManager.class).in(Scopes.SINGLETON);
    }
}
