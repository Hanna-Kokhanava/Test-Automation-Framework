package com.linkedin.automation.controls;

import com.linkedin.automation.page_elements.block.MobileBlock;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class LoginBlock extends MobileBlock {

    @AndroidFindBy(id = "com.linkedin.android:id/growth_prereg_fragment_sign_in_button")
    private Button login;

    public Button getLogin() {
        return login;
    }

    public LoginBlock(WebElement element) {
        super(element);
    }

    @Override
    public boolean isCorrect() {
        return false;
    }
}
