package com.linkedin.automation.core.mobile_elements.element;

import org.openqa.selenium.WebElement;

import static com.linkedin.automation.core.application.ApplicationUtils.getAppType;

/**
 * Created on 17.04.2018
 * Specifies wrapped {@link WebElement} - Button control
 */
public class Button extends MobileElement {

    protected Button(WebElement wrappedElement) {
        super(wrappedElement);
    }

    /**
     * Returns the button text label value
     */
    public String getText() {
        String text;
        switch (getAppType(getWrappedDriver())) {
            case NATIVE_IOS_APP:
                text = getAttribute("label");
                return !text.equals("")
                        ? text
                        : getWrappedElement().getText();
            case NATIVE_ANDROID_APP:
                text = getWrappedElement().getText();
                return !text.equals("")
                        ? text
                        : getAttribute("text");
            default:
                return getAttribute("text");
        }
    }
}
