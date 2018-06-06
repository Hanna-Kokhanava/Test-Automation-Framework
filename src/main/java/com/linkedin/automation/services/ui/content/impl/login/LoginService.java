package com.linkedin.automation.services.ui.content.impl.login;

import com.linkedin.automation.core.logger.Logger;
import com.linkedin.automation.pages.LoginPage;
import com.linkedin.automation.services.ui.PageProvider;

public class LoginService extends PageProvider<LoginPage> {

    public void printEmail(String emailValue) {
        Logger.info("Print user email");
        getPage().getSignInCredentialsBlock().getEmailAddressInput().sendKeys(emailValue);
    }

    public void printPassword(String passwordValue) {
        Logger.info("Print user password");
        getPage().getSignInCredentialsBlock().getPasswordInput().sendKeys(passwordValue);
    }

    public void clickCredentialsSignInButton() {
        Logger.info("Print user password");
        getPage().getSignInCredentialsBlock().getSignInButton().click();
    }

    public void clickSignInButton() {
        Logger.info("Print user password");
        getPage().getLoginContentBlock().getSignInButton().click();
    }

    public void loginAsUser(String emailValue, String passwordValue) {
        printEmail(emailValue);
        printPassword(passwordValue);
        clickCredentialsSignInButton();
    }

    public boolean isPageLoadedCorrectly() {
        Logger.info("Check if page has all components visible");
        return getPage().getLoginContentBlock().getIntroLogo().isDisplayed()
                && getPage().getLoginContentBlock().getSignInButton().isDisplayed()
                && getPage().getLoginContentBlock().getJoinNowButton().isDisplayed()
                && getPage().getLoginContentBlock().getJoinWithGoogleButton().isDisplayed();
    }
}
