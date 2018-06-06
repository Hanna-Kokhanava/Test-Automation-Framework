package com.linkedin.automation.pages;

import com.linkedin.automation.pages.content.elements.impl.login.LoginContentBlock;
import com.linkedin.automation.pages.content.elements.impl.login.SignInCredentialsBlock;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends DefaultMobilePage {

    @AndroidFindBy(id = "growth_prereg_fragment_view_pager")
    private LoginContentBlock loginContentBlock;

    @AndroidFindBy(id = "growth_login_fragment_container")
    private SignInCredentialsBlock signInCredentialsBlock;

    public LoginContentBlock getLoginContentBlock() {
        return loginContentBlock;
    }

    public SignInCredentialsBlock getSignInCredentialsBlock() {
        return signInCredentialsBlock;
    }
}
