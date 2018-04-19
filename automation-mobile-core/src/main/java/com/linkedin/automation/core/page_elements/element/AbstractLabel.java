package com.linkedin.automation.core.page_elements.element;

import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;

public abstract class AbstractLabel extends WrappedMobileElement implements ILabel {

    /**
     * Specifies wrapped {@link MobileElement}.
     *
     * @param wrappedElement {@code WebElement} to wrap.
     */
    public AbstractLabel(WebElement wrappedElement) {
        super(wrappedElement);
    }

}
