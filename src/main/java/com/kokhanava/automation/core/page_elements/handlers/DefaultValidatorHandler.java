package com.kokhanava.automation.core.page_elements.handlers;

import com.kokhanava.automation.core.page_elements.interfaces.Validator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

public class DefaultValidatorHandler implements Validator {
    private WrapsElement wrapsElement;

    public DefaultValidatorHandler(WrapsElement wrapsElement) {
        this.wrapsElement = wrapsElement;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void revalidate() {
        WebElement wrappedElement = wrapsElement.getWrappedElement();
        while (WrapsElement.class.isAssignableFrom(wrappedElement.getClass())
                && !Validator.class.isAssignableFrom(wrappedElement.getClass())) {
            wrappedElement = ((WrapsElement) wrappedElement).getWrappedElement();
        }

        ((Validator) wrappedElement).revalidate();
    }

}
