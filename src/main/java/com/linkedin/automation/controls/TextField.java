package com.linkedin.automation.controls;

import com.linkedin.automation.page_elements.element.AbstractTextField;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.WebElement;

public class TextField extends AbstractTextField {

    public TextField(WebElement wrappedElement) {
        super(wrappedElement);
    }

    @Override
    public void clear() {
        MobileElement element = (MobileElement) getWrappedElement();
        element.clear();
    }

    @Override
    public void setValue(String text) {
        MobileElement element = (MobileElement) getWrappedElement();
        element.clear();
        element.setValue(text);
    }

    @Override
    public void setFieldFocus() {
        getWrappedElement().click();
    }

    @Override
    public String getText() {
        return getWrappedElement().getText();
    }

    @Override
    public boolean isEnabled() {
        return true;// todo: implements this method!!!
    }
}
