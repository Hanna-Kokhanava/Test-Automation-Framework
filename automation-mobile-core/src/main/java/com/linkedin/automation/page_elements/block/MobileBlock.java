package com.linkedin.automation.page_elements.block;

import com.linkedin.automation.core.device.functions.Direction;
import com.linkedin.automation.page_elements.handlers.AbstractResizeableHandler;
import com.linkedin.automation.page_elements.handlers.DefaultAvailabilityHandler;
import com.linkedin.automation.page_elements.handlers.DefaultValidatorHandler;
import com.linkedin.automation.page_elements.handlers.DefaultWaiter;
import com.linkedin.automation.page_elements.interfaces.*;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Created on 17.04.2018
 * The base class that is used for creating blocks of elements
 */
public abstract class MobileBlock
        <ScrollableType extends Scrollable>
        extends Widget
        implements IMobileBlock, Named, Validator {

    private String name;

    private Waiter waiter;
    private Availability availabilityHandler;
    private Resizeable resizeableHandler;
    protected ScrollableType scrollHandler;
    protected Validator validatorHandler;

    public MobileBlock(WebElement element) {
        super(element);
        availabilityHandler = new DefaultAvailabilityHandler(this);
        waiter = new DefaultWaiter(availabilityHandler);
        validatorHandler = new DefaultValidatorHandler(this);
        resizeableHandler = new AbstractResizeableHandler() {
        }; //TODO it is stub
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

    protected Waiter getWaiter() {
        return waiter;
    }

    public void setWaiter(Waiter waiter) {
        this.waiter = waiter;
    }

    protected Availability getAvailabilityHandler() {
        return availabilityHandler;
    }

    public void setAvailability(Availability availabilityHandler) {
        this.availabilityHandler = availabilityHandler;
    }

    protected Resizeable getResizeableHandler() {
        return resizeableHandler;
    }

    public void setResizeableHandler(Resizeable resizeableHandler) {
        this.resizeableHandler = resizeableHandler;
    }

    public ScrollableType getScrollHandler() {
        return scrollHandler;
    }

    public void setScrollHandler(ScrollableType scrollHandler) {
        this.scrollHandler = scrollHandler;
    }

    public Validator getValidatorHandler() {
        return validatorHandler;
    }

    public void setValidatorHandler(Validator validatorHandler) {
        this.validatorHandler = validatorHandler;
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

    //--------------Scrollable------------------

    @Override
    public void scroll(Direction direction) {
        // if scrollHandler is null  then it is no scrollable element
        if (null != getScrollHandler()) {
            getScrollHandler().scroll(direction);
        }
    }

    @Override
    public void scroll(double startX, double startY, double endX, double endY, Duration duration) {
        // if scrollHandler is null  then it is no scrollable element
        if (null != getScrollHandler()) {
            getScrollHandler().scroll(startX, startY, endX, endY, duration);
        }
    }

    //--------------Resizeable--------------------

    @Override
    public void pinch() {
        getResizeableHandler().pinch();
    }

    @Override
    public void zoom() {
        getResizeableHandler().zoom();
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
