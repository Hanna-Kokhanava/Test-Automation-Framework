package com.linkedin.automation.testscripts;

import com.google.inject.Inject;
import com.linkedin.automation.services.ui.content.impl.home.HomeService;
import com.linkedin.automation.services.ui.content.impl.login.LoginService;
import com.linkedin.automation.unit.BaseCase;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created on 10.03.2018
 */
public class IncorrectCredentialsTest extends BaseCase {
    @Inject
    private HomeService homeService;
    @Inject
    private LoginService loginService;

    @Test(description = "Example test")
    public void login() {
        assertTrue(loginService.isPageLoadedCorrectly(), "Page is loaded incorrectly");
        loginService.clickSignInButton();
        loginService.loginAsUser("asd", "asd");
    }
}
