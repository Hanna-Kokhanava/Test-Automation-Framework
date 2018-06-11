package com.linkedin.automation.services.ui.content.impl.home;

import com.linkedin.automation.pages.HomePage;
import com.linkedin.automation.pages.content.elements.ContentTopBar;
import com.linkedin.automation.pages.content.elements.impl.home.HomeContentBlock;
import com.linkedin.automation.pages.resources.NavigationItems;
import com.linkedin.automation.services.ui.content.AbstractContentPageService;

public class HomeService extends AbstractContentPageService<ContentTopBar, HomeTopBarHandler,
        HomeContentBlock, HomePage, HomeContentHandler> {

    public boolean checkTopBar() {
        return getNavigationBarHandler().isItemActive(NavigationItems.BarItem.HOME)
                && getNavigationBarHandler().isBarPresents();
    }

    public boolean checkHomeContent() {
        return getContentHandler().isHomePageLoaded();
    }

}
