package com.linkedin.automation.core.mobile_elements.element;

import org.openqa.selenium.WebElement;

import static com.linkedin.automation.core.application.ApplicationUtils.getAppType;

/**
 * Created on 17.04.2018
 * Specifies wrapped {@link WebElement} - TextInput control
 */
public class TextInput extends MobileElement {

    protected TextInput(WebElement wrappedElement) {
        super(wrappedElement);
    }

    public void setValue(CharSequence keys) {
        checkExist();
        getWrappedMobileElement().setValue(keys.toString());
    }

    public void sendKeys(CharSequence keys) {
        checkExist();
        getWrappedElement().sendKeys(keys);
    }

    public void clear() {
        getWrappedElement().clear();
    }

    @Override
    public String getText() {
        switch (getAppType(getWrappedDriver())) {
            case NATIVE_ANDROID_APP:
                return getWrappedElement().getText();
            case NATIVE_IOS_APP:
                return getAttribute("value");
            default:
                if ("textarea".equals(getWrappedElement().getTagName())) {
                    return getWrappedElement().getText();
                }
                String enteredText = getAttribute("value");
                if (enteredText == null) {
                    return "";
                }
                return enteredText;
        }
    }
}
