package com.linkedin.automation.services.ui.content.impl.home;

import com.linkedin.automation.pages.content.elements.ContentTopBar;
import com.linkedin.automation.pages.resources.NavigationItems;
import com.linkedin.automation.services.ui.handlers.AbstractBarHandler;

public class HomeTopBarHandler extends AbstractBarHandler<NavigationItems.BarItem, ContentTopBar> {
    @Override
    public boolean itemActionPerform(NavigationItems.BarItem item) {
        return isAvailableType(item) && super.itemActionPerform(item);
    }
}
