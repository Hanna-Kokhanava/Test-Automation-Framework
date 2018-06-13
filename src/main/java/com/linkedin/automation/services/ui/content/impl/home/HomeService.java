package com.linkedin.automation.services.ui.content.impl.home;

import com.linkedin.automation.core.logger.Logger;
import com.linkedin.automation.pages.HomePage;
import com.linkedin.automation.pages.content.elements.ContentTopBar;
import com.linkedin.automation.pages.content.elements.impl.home.HomeContentBlock;
import com.linkedin.automation.pages.resources.NavigationItems;
import com.linkedin.automation.services.ui.content.AbstractContentPageService;

public class HomeService extends AbstractContentPageService<ContentTopBar, HomeTopBarHandler,
        HomeContentBlock, HomePage, HomeContentHandler> {

    public boolean checkTopBar() {
        Logger.info("Check TopBar visibility and default tab");
        return getNavigationBarHandler().isBarPresents()
                && getNavigationBarHandler().isItemActive(NavigationItems.BarItem.HOME);
    }

    public boolean checkHomeContent() {
        Logger.info("Check Home page first feed visibility");
        return getContentHandler().isHomePageLoaded();
    }

    public boolean selectItem(NavigationItems.BarItem item) {
        Logger.info("Select bar item");
        return getNavigationBarHandler().itemActionPerform(item);
    }

}
