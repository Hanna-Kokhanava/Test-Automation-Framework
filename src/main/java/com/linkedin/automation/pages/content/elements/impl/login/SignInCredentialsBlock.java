package com.linkedin.automation.pages.content.elements.impl.login;

import com.linkedin.automation.controls.Button;
import com.linkedin.automation.page_elements.block.MobileBlock;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.htmlelements.element.TextInput;

public class SignInCredentialsBlock extends MobileBlock {

    @AndroidFindBy(id = "growth_login_join_fragment_email_address")
    private TextInput emailAddressInput;

    @AndroidFindBy(id = "growth_login_join_fragment_password")
    private TextInput passwordInput;

    @AndroidFindBy(id = "growth_login_fragment_sign_in")
    private Button signInButton;

    @AndroidFindBy(id = "growth_login_fragment_forgot_password")
    private Button forgotPasswordButton;

    @AndroidFindBy(id = "growth_login_fragment_join")
    private Button joinNowButton;

    public SignInCredentialsBlock(WebElement element) {
        super(element);
    }

    @Override
    public boolean isCorrect() {
        return true;
    }

    public TextInput getEmailAddressInput() {
        return emailAddressInput;
    }

    public TextInput getPasswordInput() {
        return passwordInput;
    }

    public Button getSignInButton() {
        return signInButton;
    }

    public Button getForgotPasswordButton() {
        return forgotPasswordButton;
    }

    public Button getJoinNowButton() {
        return joinNowButton;
    }
}
