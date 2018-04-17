package com.linkedin.automation.core.mobile_elements.element;

import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.device.functions.Direction;
import com.linkedin.automation.core.driver.managers.DriverManager;
import com.linkedin.automation.core.mobile_elements.interfaces.Checkable;
import com.linkedin.automation.core.mobile_elements.interfaces.Proxifyable;
import com.linkedin.automation.core.mobile_elements.interfaces.Touchable;
import com.linkedin.automation.core.mobile_elements.interfaces.Waitable;
import com.linkedin.automation.core.waiters.CheckThat;
import com.linkedin.automation.core.waiters.WaitFor;
import org.apache.commons.lang.NotImplementedException;
import org.openqa.selenium.*;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import static com.linkedin.automation.core.application.ApplicationUtils.getAppType;

/**
 * Created on 17.04.2018
 * The base class that is used for creating blocks of elements
 */
public class MobileBlock extends HtmlElement implements Touchable, Waitable, Proxifyable, Checkable {

    @Override
    public WebDriver getWrappedDriver() {
        return ((WrapsDriver) getWrappedElement()).getWrappedDriver();
    }

    @Override
    public WebElement getNotProxiedElement() {
        WebElement wrappedElement = getWrappedElement();
        while (wrappedElement instanceof WrapsElement) {
            wrappedElement = ((WrapsElement) wrappedElement).getWrappedElement();
        }
        return wrappedElement;
    }

    @Override
    public boolean isExist() {
        try {
            getNotProxiedElement();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public boolean isDisplayed() {
        try {
            return super.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public void click() {
        getWrappedElement().click();
    }

    @Override
    public void scroll(Direction direction) {
        switch (getAppType(getWrappedDriver())) {
            case NATIVE_ANDROID_APP:
                swipe(direction.getStartX(), direction.getStartY(), direction.getEndX(), direction.getEndY(), 1);
                break;
            case NATIVE_IOS_APP:
                String id = ((RemoteWebElement) getNotProxiedElement()).getId();
                // perform scroll by using scripts with one of the methods
                // scrollUp(), scrollDown(), scrollLeft() or scrollRight()
                String scrollScript = "$.getElement('" + id + "').scroll" + direction.getDir() + "()";
                DriverManager.getDriver().executeScript(scrollScript);
                break;
            default:
                throw new NotImplementedException("No scroll implementations for default use case.");
        }
    }

    @Override
    public void scrollToVisible(Direction direction) {
        Device.TouchScreen.scrollToVisible(this, direction);
    }

    @Override
    public void swipe(double startX, double startY, double endX, double endY, double duration) {
        Device.TouchScreen.swipe(startX, startY, endX, endY, duration);
    }

    @Override
    public void waitForExist(int timeout) {
        WaitFor.exist(this, timeout);
    }

    @Override
    public void waitForExist() {
        WaitFor.exist(this);
    }

    @Override
    public void waitForNotExist(int timeout) {
        WaitFor.notExist(this, timeout);
    }

    @Override
    public void waitForNotExist() {
        WaitFor.notExist(this);
    }

    @Override
    public void waitForDisplayed(int timeout) {
        WaitFor.displayed(this, timeout);
    }

    @Override
    public void waitForDisplayed() {
        WaitFor.displayed(this);
    }

    @Override
    public void waitForNotDisplayed(int timeout) {
        WaitFor.notDisplayed(this, timeout);
    }

    @Override
    public void waitForNotDisplayed() {
        WaitFor.notDisplayed(this);
    }

    @Override
    public void checkExist() {
        CheckThat.exist(this);
    }

    @Override
    public void checkDisplayed() {
        CheckThat.displayed(this);
    }

    @Override
    public void checkTextEqual(String expected) {
        CheckThat.textEqual(this, expected);
    }

    @Override
    public void checkTextNotEqual(String expected) {
        CheckThat.textNotEqual(this, expected);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return null;
    }

    @Override
    public Rectangle getRect() {
        return null;
    }
}
