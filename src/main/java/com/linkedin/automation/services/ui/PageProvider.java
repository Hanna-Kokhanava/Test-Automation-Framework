package com.linkedin.automation.services.ui;

import com.google.inject.Inject;
import com.google.inject.Provider;

public abstract class PageProvider<PageType> {
    @Inject
    private Provider<PageType> pageProvider;

    protected PageType getPage() {
        return pageProvider.get();
    }
}
