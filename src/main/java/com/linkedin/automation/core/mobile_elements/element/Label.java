package com.linkedin.automation.core.mobile_elements.element;

import org.openqa.selenium.WebElement;

import static com.linkedin.automation.core.application.ApplicationUtils.getAppType;

/**
 * Created on 17.04.2018
 * Specifies wrapped {@link WebElement} - Label control
 */
public class Label extends MobileElement {

    protected Label(WebElement wrappedElement) {
        super(wrappedElement);
    }

    public String getText() {
        switch (getAppType(getWrappedDriver())) {
            case NATIVE_IOS_APP:
                String text = getAttribute("label");
                return !text.equals("")
                        ? text
                        : getAttribute("name");
            case NATIVE_ANDROID_APP:
                return getWrappedElement().getText();
            default:
                return getWrappedElement().getText();
        }
    }
}
