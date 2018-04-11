package com.linkedin.automation.core.mobile_elements.interfaces;

import com.linkedin.automation.core.device.functions.Direction;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * Created on 11.04.2018
 */
public interface Touchable extends WrapsDriver {
    @Override
    WebDriver getWrappedDriver();

    String getAttribute(String name);

    String getText();

    Dimension getSize();

    void click();

    void scroll(Direction direction);

    void swipe(double startX, double startY, double endX, double endY, double duration);

    boolean isExist();

    boolean isDisplayed();

    boolean isEnabled();

    boolean isSelected();
}
