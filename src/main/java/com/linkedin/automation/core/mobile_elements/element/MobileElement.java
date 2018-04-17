package com.linkedin.automation.core.mobile_elements.element;

import com.google.common.collect.Lists;
import com.linkedin.automation.core.device.Device;
import com.linkedin.automation.core.device.functions.Direction;
import com.linkedin.automation.core.mobile_elements.interfaces.Checkable;
import com.linkedin.automation.core.mobile_elements.interfaces.Proxifyable;
import com.linkedin.automation.core.mobile_elements.interfaces.Touchable;
import com.linkedin.automation.core.mobile_elements.interfaces.Waitable;
import com.linkedin.automation.core.waiters.CheckThat;
import com.linkedin.automation.core.waiters.WaitFor;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.*;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 11.04.2018
 * Adds mobile-specific features & gestures, such as swipe, touch, etc.
 */
public class MobileElement extends TypifiedElement implements Touchable, Waitable, Checkable, Proxifyable {

    protected MobileElement(WebElement wrappedElement) {
        super(wrappedElement);
    }

    public static MobileElement create(WebElement webElement) {
        return new MobileElement(webElement);
    }

    private static List<MobileElement> createList(List<org.openqa.selenium.WebElement> webElements) {
        List<MobileElement> mobileElements = Lists.newArrayList();
        mobileElements.addAll(webElements.stream()
                .map(MobileElement::new)
                .collect(Collectors.toList())
        );
        return mobileElements;
    }

    /**
     * Find mobile element
     *
     * @param by the search strategy
     * @return founded mobile element
     */
    public MobileElement findMobileElement(By by) {
        return create(by.findElement(getNotProxiedElement()));
    }

    /**
     * Find mobile elements.
     *
     * @param by the search strategy
     * @return the list of mobile elements
     */
    public List<MobileElement> findMobileElements(By by) {
        List<org.openqa.selenium.WebElement> weList = by.findElements(getNotProxiedElement());
        return createList(weList);
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
    public WebDriver getWrappedDriver() {
        return ((WrapsDriver) getWrappedElement()).getWrappedDriver();
    }

    public io.appium.java_client.MobileElement getWrappedMobileElement() {
        return (io.appium.java_client.MobileElement) getWrappedElement();
    }

    @Override
    public String getAttribute(String name) {
        return getWrappedElement().getAttribute(name);
    }

    @Override
    public String getText() {
        String text = getAttribute("text");
        return !text.equals("") ? text : getWrappedElement().getText();
    }

    @Override
    public Dimension getSize() {
        return getWrappedElement().getSize();
    }

    @Override
    public void click() {
        getWrappedElement().click();
    }

    @Override
    public void scroll(Direction direction) {
        swipe(direction.getStartX(), direction.getStartY(), direction.getEndX(), direction.getEndY(), 1);
    }

    @Override
    public void scrollToVisible(Direction direction) {
        Device.TouchScreen.scrollToVisible(this, direction);
    }


    @Override
    public void swipe(double startX, double startY, double endX, double endY, double duration) {
        if (startX < 1.0 && startY < 1.0
                && endX < 1.0 && endY < 1.0) {
            Dimension size = getSize();
            startX *= size.width;
            startY *= size.height;
            endX *= size.width;
            endY *= size.height;
        }

        TouchAction touchAction = new TouchAction((MobileDriver) getWrappedDriver());
        touchAction.press(getWrappedElement(), (int) startX, (int) startY)
                .waitAction((Duration.ofMillis((int) duration * 1000)))
                .moveTo(getWrappedElement(), (int) endX, (int) endY)
                .release()
                .perform();
    }

    @Override
    public boolean isExist() {
        try {
            getNotProxiedElement();
            return true;
        } catch (WebDriverException e) {
            return false;
        }
    }

    @Override
    public boolean isDisplayed() {
        try {
            return super.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void waitForExist() {
        WaitFor.exist(this);
    }

    @Override
    public void waitForExist(int timeout) {
        WaitFor.exist(this, timeout);
    }

    public void waitForNotExist() {
        WaitFor.notExist(this);
    }


    @Override
    public void waitForNotExist(int timeout) {
        WaitFor.notExist(this, timeout);
    }

    @Override
    public void waitForDisplayed() {
        WaitFor.displayed(this);
    }

    @Override
    public void waitForDisplayed(int timeout) {
        WaitFor.displayed(this, timeout);
    }

    @Override
    public void waitForNotDisplayed() {
        WaitFor.notDisplayed(this);
    }

    @Override
    public void waitForNotDisplayed(int timeout) {
        WaitFor.notDisplayed(this, timeout);
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
    public void checkTextEqual(String expectedValue) {
        CheckThat.textEqual(this, expectedValue);
    }

    @Override
    public void checkTextNotEqual(String expectedValue) {
        CheckThat.textNotEqual(this, expectedValue);
    }
}
