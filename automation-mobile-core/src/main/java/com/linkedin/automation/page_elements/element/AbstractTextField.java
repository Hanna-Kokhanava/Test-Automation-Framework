package com.linkedin.automation.page_elements.element;

import org.openqa.selenium.WebElement;

/**
 * @author Siarhei_Ziablitsau
 */

public abstract class AbstractTextField extends WrappedMobileElement implements ITextField {

    public AbstractTextField(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @Override
    public void sendKeys(CharSequence... keys) {
        getWrappedElement().sendKeys(keys);
    }

    /**
     * Checks that field available to text entry
     *
     * @return availability to text entry
     */
    public abstract boolean isEnabled();
}
