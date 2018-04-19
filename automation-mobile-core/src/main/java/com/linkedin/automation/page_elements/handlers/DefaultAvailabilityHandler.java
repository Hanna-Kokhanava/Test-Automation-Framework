package com.linkedin.automation.page_elements.handlers;

import com.linkedin.automation.page_elements.interfaces.Availability;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

public class DefaultAvailabilityHandler implements Availability {
    private WrapsElement wrapsElement;

    public DefaultAvailabilityHandler(WrapsElement wrapsElement) {
        this.wrapsElement = wrapsElement;
    }

    @Override
    public boolean isExist() {
        try {
            WebElement wrappedElement = wrapsElement.getWrappedElement();
            while (WrapsElement.class.isAssignableFrom(wrappedElement.getClass())) {
                wrappedElement = ((WrapsElement) wrappedElement).getWrappedElement();
            }
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    @Override
    public boolean isDisplayed() {
        try {
            return wrapsElement.getWrappedElement().isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}