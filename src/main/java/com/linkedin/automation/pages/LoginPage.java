package com.linkedin.automation.pages;

import com.linkedin.automation.controls.LoginBlock;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends DefaultMobilePage {

    @AndroidFindBy(id = "com.linkedin.android:id/growth_prereg_fragment_view_pager")
    private LoginBlock login;

    public LoginBlock getLogin() {
        return login;
    }
}
