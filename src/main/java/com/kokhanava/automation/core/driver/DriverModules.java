package com.kokhanava.automation.core.driver;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.kokhanava.automation.core.creator.IDriverCreator;
import com.kokhanava.automation.core.creator.AppiumDriverCreator;
import com.kokhanava.automation.core.driver.dependencies.WebDependencies;
import com.kokhanava.automation.core.driver.managers.mobile.AppiumServerManager;
import com.kokhanava.automation.core.driver.managers.mobile.IAppiumServer;
import com.kokhanava.automation.core.tools.files.property.IProperty;
import com.kokhanava.automation.core.tools.files.property.PropertyLoader;
import com.kokhanava.automation.core.creator.WebDriverCreator;
import com.kokhanava.automation.core.driver.dependencies.AppiumDependencies;
import com.kokhanava.automation.core.driver.dependencies.IDependencies;

/**
 * Created on 15.03.2018
 */
public class DriverModules extends AbstractModule {

    @Override
    protected void configure() {
        if (PropertyLoader.getGeneralTestProperty(PropertyLoader.GeneralProperty.AUTOMATION_TYPE).equalsIgnoreCase("mobile")) {
            bind(IProperty.class).to(PropertyLoader.MobileProperty.class).in(Scopes.SINGLETON);
            bind(IDriverCreator.class).to(AppiumDriverCreator.class).in(Scopes.SINGLETON);
            bind(IDependencies.class).to(AppiumDependencies.class).in(Scopes.SINGLETON);
            bind(IAppiumServer.class).to(AppiumServerManager.class).in(Scopes.SINGLETON);
        } else {
            bind(IProperty.class).to(PropertyLoader.BrowserProperty.class).in(Scopes.SINGLETON);
            bind(IDriverCreator.class).to(WebDriverCreator.class).in(Scopes.SINGLETON);
            bind(IDependencies.class).to(WebDependencies.class).in(Scopes.SINGLETON);
        }
    }
}
