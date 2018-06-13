package com.linkedin.automation.testscripts;

import com.google.inject.Inject;
import com.linkedin.automation.pages.resources.NavigationItems;
import com.linkedin.automation.services.ui.content.impl.home.HomeService;
import com.linkedin.automation.unit.BaseCase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CheckHomePageTest extends BaseCase {
    @Inject
    private HomeService homeService;

    @Test(description = "Check HomePage")
    public void checkHomePage() {
        assertTrue(homeService.selectItem(NavigationItems.BarItem.HOME), "Tab item was not selected");
        assertTrue(homeService.checkTopBar(), "Top Bar is loaded incorrectly");
        assertTrue(homeService.checkHomeContent(), "Home content is loaded incorrectly");
    }

    @Test(description = "Check all tabs switching", dependsOnMethods = {"checkHomePage"})
    public void checkAllTabsSwitching() {
        assertTrue(homeService.selectItem(NavigationItems.BarItem.NETWORK), "Tab item was not selected");
        assertTrue(homeService.selectItem(NavigationItems.BarItem.MESSAGING), "Tab item was not selected");
        assertTrue(homeService.selectItem(NavigationItems.BarItem.NOTIFICATIONS), "Tab item was not selected");
        assertTrue(homeService.selectItem(NavigationItems.BarItem.JOBS), "Tab item was not selected");
        assertTrue(homeService.selectItem(NavigationItems.BarItem.HOME), "Tab item was not selected");
    }
}
