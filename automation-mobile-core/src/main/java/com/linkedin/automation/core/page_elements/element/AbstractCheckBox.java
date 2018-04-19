package com.linkedin.automation.core.page_elements.element;

import org.openqa.selenium.WebElement;


public abstract class AbstractCheckBox extends WrappedMobileElement implements ICheckBox {
    public AbstractCheckBox(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @Override
    public void check() {
        if (!isChecked()) getWrappedElement().click();
    }

    @Override
    public void uncheck() {
        if (isChecked()) getWrappedElement().click();
    }
}
