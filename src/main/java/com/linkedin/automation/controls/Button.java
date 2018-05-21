package com.linkedin.automation.controls;

import com.linkedin.automation.page_elements.element.AbstractButton;
import com.linkedin.automation.page_elements.element.ILabel;
import org.openqa.selenium.WebElement;

public class Button extends AbstractButton implements ILabel {

    public Button(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @Override
    public void click() {
        getWrappedElement().click();
    }

    @Override
    public String getText() {
        return getWrappedElement().getText();
    }
}
