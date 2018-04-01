package com.linkedin.automation.core.driver;

import com.google.inject.AbstractModule;

/**
 * Created on 20.03.2018
 * Will be used to bind interface methods implementation to the specific platform implementation
 */
public class PlatformModules extends AbstractModule {
    @Override
    protected void configure() {
        //Bind interface implementation according to the platform type
    }
}
