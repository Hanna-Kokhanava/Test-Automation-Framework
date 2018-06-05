package com.linkedin.automation.testscripts;

import com.google.inject.Inject;
import com.linkedin.automation.services.ui.content.impl.home.HomeService;
import com.linkedin.automation.unit.BaseCase;
import org.testng.annotations.Test;

/**
 * Created on 10.03.2018
 */
public class LaunchTest extends BaseCase {
    @Inject
    private HomeService homeService;

    @Test(description = "Example test")
    public void login() {
        System.out.println("Test was launched");
        homeService.check();
    }
}
