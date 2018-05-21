package com.linkedin.automation.services.ui;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.linkedin.automation.services.ui.listeners.PageEventNotifier;

public abstract class PageProvider<PageType> {
    @Inject
    private Provider<PageType> pageProvider;
    @Inject
    private Provider<PageEventNotifier> pageEventNotifierProvider;

    protected PageType getPage() {
        return pageProvider.get();
    }

    protected PageEventNotifier getPageEventNotifier() {
        return pageEventNotifierProvider.get();
    }
}
