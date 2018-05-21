package com.linkedin.automation.controls;

import com.linkedin.automation.page_elements.element.AbstractLabel;
import org.openqa.selenium.WebElement;

public class Image extends AbstractLabel {
    public Image(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @Override
    public String getText() {
        //Will be content-desc (Android) or label (iOS)
        return null;

    }
}
