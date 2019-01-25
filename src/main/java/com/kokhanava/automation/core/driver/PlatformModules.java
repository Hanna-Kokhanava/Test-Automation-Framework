package com.kokhanava.automation.core.driver;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.kokhanava.automation.core.driver.config.AndroidPlatformConfig;
import com.kokhanava.automation.core.driver.config.IPlatformConfig;

/**
 * Created on 20.03.2018
 * Will be used to bind interface methods implementation to the specific platform implementation
 */
public class PlatformModules extends AbstractModule {
    @Override
    protected void configure() {
        bind(IPlatformConfig.class).to(AndroidPlatformConfig.class).in(Scopes.SINGLETON);
        //TODO need to separate logic for different types of automation
    }
}
