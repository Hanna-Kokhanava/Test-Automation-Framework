package com.linkedin.automation.pages.content.elements.impl.home;

import com.linkedin.automation.controls.Button;
import com.linkedin.automation.pages.content.elements.AbstractContentBlock;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class HomeContentBlock extends AbstractContentBlock {

    @AndroidFindBy(id = "growth_prereg_fragment_sign_in_button")
    private Button home;

    public HomeContentBlock(WebElement element) {
        super(element);
    }

    @Override
    public boolean isCorrect() {
        return false;
    }

    public Button getHome() {
        return home;
    }
}
