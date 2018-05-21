package com.linkedin.automation.controls;

import com.linkedin.automation.page_elements.element.AbstractLabel;
import org.openqa.selenium.WebElement;

public class Label extends AbstractLabel {

    public Label(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @Override
    public String getText() {
        return getWrappedElement().getText();
    }
}
