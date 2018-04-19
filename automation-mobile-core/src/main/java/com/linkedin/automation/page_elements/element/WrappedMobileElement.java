package com.linkedin.automation.page_elements.element;

import com.linkedin.automation.page_elements.handlers.DefaultAvailabilityHandler;
import com.linkedin.automation.page_elements.handlers.DefaultValidatorHandler;
import com.linkedin.automation.page_elements.handlers.DefaultWaiter;
import com.linkedin.automation.page_elements.interfaces.Availability;
import com.linkedin.automation.page_elements.interfaces.Named;
import com.linkedin.automation.page_elements.interfaces.Validator;
import com.linkedin.automation.page_elements.interfaces.Waiter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;

import java.time.Duration;

/**
 * Created on 11.04.2018
 * Adds mobile-specific features & gestures, such as swipe, touch, etc.
 */
public abstract class WrappedMobileElement implements WrapsElement, WrapsDriver, Waiter, Availability, Named, Validator {

    private final WebElement wrappedElement;
    private Waiter waiter;
    private Availability availabilityHandler;
    private Validator validatorHandler;
    private String name;

    /**
     * Specifies wrapped {@link WebElement}.
     *
     * @param wrappedElement {@code {@link WebElement}} to wrap.
     */
    protected WrappedMobileElement(WebElement wrappedElement) {
        this.wrappedElement = wrappedElement;
        setWaiter(new DefaultWaiter(this));
        setAvailabilityHandler(new DefaultAvailabilityHandler(this));
        setValidatorHandler(new DefaultValidatorHandler(this));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Sets a name of an element. This method is used by initialization mechanism and is not intended
     * to be used directly.
     *
     * @param name Name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    public final Waiter getWaiter() {
        return waiter;
    }

    public final void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    public final Availability getAvailabilityHandler() {
        return availabilityHandler;
    }

    public final void setAvailabilityHandler(Availability availabilityHandler) {
        this.availabilityHandler = availabilityHandler;
    }

    public Validator getValidatorHandler() {
        return validatorHandler;
    }

    public void setValidatorHandler(Validator validatorHandler) {
        this.validatorHandler = validatorHandler;
    }

    @Override
    public final WebElement getWrappedElement() {
        return wrappedElement;
    }

    @Override
    public final AppiumDriver getWrappedDriver() {
        return (AppiumDriver) WebDriverUnpackUtility.unpackWebDriverFromSearchContext(getWrappedElement());
    }

    //-------- Waiter --------

    @Override
    public final void waitForExist(Duration timeout) {
        getWaiter().waitForExist(timeout);
    }

    @Override
    public final void waitForExist() {
        getWaiter().waitForExist();
    }

    @Override
    public final void waitForNotExist(Duration timeout) {
        getWaiter().waitForNotExist(timeout);
    }

    @Override
    public final void waitForNotExist() {
        getWaiter().waitForNotExist();
    }

    @Override
    public final void waitForDisplayed(Duration timeout) {
        getWaiter().waitForDisplayed(timeout);
    }

    @Override
    public final void waitForDisplayed() {
        getWaiter().waitForDisplayed();
    }

    @Override
    public final void waitForNotDisplayed(Duration timeout) {
        getWaiter().waitForNotDisplayed(timeout);
    }

    @Override
    public final void waitForNotDisplayed() {
        getWaiter().waitForNotDisplayed();
    }

    //----Availability------

    @Override
    public boolean isExist() {
        return getAvailabilityHandler().isExist();
    }

    @Override
    public boolean isDisplayed() {
        return getAvailabilityHandler().isDisplayed();
    }

    //-----Validator--------

    @Override
    public boolean isValid() {
        return getValidatorHandler().isValid();
    }

    @Override
    public void revalidate() {
        getValidatorHandler().revalidate();
    }
}
