package com.linkedin.automation.core.driver;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.linkedin.automation.core.creators.mobile.AppiumDriverCreator;
import com.linkedin.automation.core.creators.IDriverCreator;
import com.linkedin.automation.core.creators.web.WebDriverCreator;
import com.linkedin.automation.core.driver.dependencies.AppiumDependencies;
import com.linkedin.automation.core.driver.dependencies.IDependencies;
import com.linkedin.automation.core.driver.dependencies.WebDependencies;
import com.linkedin.automation.core.tools.files.IProperty;
import com.linkedin.automation.core.tools.files.PropertyLoader;

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
        } else {
            bind(IProperty.class).to(PropertyLoader.BrowserProperty.class).in(Scopes.SINGLETON);
            bind(IDriverCreator.class).to(WebDriverCreator.class).in(Scopes.SINGLETON);
            bind(IDependencies.class).to(WebDependencies.class).in(Scopes.SINGLETON);
            //TODO WebDriver creator for Web
        }
    }
}
