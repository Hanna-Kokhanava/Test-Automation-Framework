package com.linkedin.automation.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.linkedin.automation.pages.HomePage;
import com.linkedin.automation.services.ui.content.impl.home.HomeService;

public class ServicesConfigModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(HomePage.class).in(Scopes.SINGLETON);
        bind(HomeService.class).in(Scopes.SINGLETON);
    }
}
