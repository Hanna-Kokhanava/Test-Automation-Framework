package com.linkedin.automation.pages.content.elements.impl.login;

import com.linkedin.automation.controls.Button;
import com.linkedin.automation.controls.Image;
import com.linkedin.automation.page_elements.block.MobileBlock;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class LoginContentBlock extends MobileBlock {

    @AndroidFindBy(id = "prereg_intro_logo")
    private Image introLogo;

    @AndroidFindBy(id = "growth_prereg_fragment_join_button")
    private Button joinNowButton;

    @AndroidFindBy(id = "growth_prereg_fragment_join_with_google_button")
    private Button joinWithGoogleButton;

    @AndroidFindBy(id = "growth_prereg_fragment_sign_in_button")
    private Button signInButton;

    public LoginContentBlock(WebElement element) {
        super(element);
    }

    public Image getIntroLogo() {
        return introLogo;
    }

    public Button getJoinNowButton() {
        return joinNowButton;
    }

    public Button getJoinWithGoogleButton() {
        return joinWithGoogleButton;
    }

    public Button getSignInButton() {
        return signInButton;
    }

    @Override
    public boolean isCorrect() {
        return true;
    }
}
