package com.linkedin.automation.page_elements.element;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;

public abstract class AbstractButton extends WrappedMobileElement implements IButton {

    /**
     * Specifies wrapped {@link MobileElement}.
     *
     * @param wrappedElement {@code WebElement} to wrap.
     */
    public AbstractButton(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @Override
    public void click() {
        getWrappedElement().click();
    }

}
