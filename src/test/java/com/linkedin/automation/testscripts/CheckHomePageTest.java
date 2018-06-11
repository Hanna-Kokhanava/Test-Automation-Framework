package com.linkedin.automation.testscripts;

import com.google.inject.Inject;
import com.linkedin.automation.services.ui.content.impl.home.HomeService;
import com.linkedin.automation.unit.BaseCase;
import org.testng.annotations.Test;

public class CheckHomePageTest extends BaseCase {
    @Inject
    private HomeService homeService;

    @Test(description = "CheckHomePage")
    public void checkHomePage() {
        homeService.checkTopBar();
        homeService.checkHomeContent();
    }
}
