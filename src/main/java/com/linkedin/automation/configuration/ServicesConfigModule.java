package com.linkedin.automation.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.linkedin.automation.pages.LoginPage;

public class ServicesConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(LoginPage.class).in(Scopes.SINGLETON);
    }
}
