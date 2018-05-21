package com.linkedin.automation.services.ui.handlers.scroll.constraints;

import com.linkedin.automation.core.device.DeviceManager;
import com.linkedin.automation.services.ui.handlers.scroll.ScrollType;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.internal.WrapsElement;

public class ElementSearcherFullDisplayed extends ElementSearcher {

    private final WrapsElement element;
    private final WrapsElement container;
    private final ScrollType scrollType;

    public ElementSearcherFullDisplayed(WrapsElement element, WrapsElement container, ScrollType scrollType) {
        this.element = element;
        this.container = container;
        this.scrollType = scrollType;
    }

    @Override
    public boolean check() {
        Rectangle containerRectangle = getContainerRectangle();
        Rectangle elementRectangle = getElementRectangle();

        if (DeviceManager.getCurrentDevice().isIOS()) {
            return isElementIsInsideContainer(containerRectangle, elementRectangle);
        }
        return isElementIsInsideContainer(containerRectangle, elementRectangle) &&
                !isRectanglesHaveSameFaces(containerRectangle, elementRectangle, scrollType);
    }

    private Rectangle getContainerRectangle() {
        Dimension containerSize = container.getWrappedElement().getSize();
        Point containerLocation = container.getWrappedElement().getLocation();
        return new Rectangle(containerLocation, containerSize);
    }


    private Rectangle getElementRectangle() {
        Dimension elementSize = element.getWrappedElement().getSize();
        Point elementLocation = element.getWrappedElement().getLocation();
        return new Rectangle(elementLocation, elementSize);
    }

    private boolean isRectanglesHaveSameFaces(Rectangle containerRectangle, Rectangle elementRectangle, ScrollType scrollType) {
        switch (scrollType) {
            case VERTICAL:
                return containerRectangle.getY() == elementRectangle.getY() ||
                        containerRectangle.getY() + containerRectangle.getHeight() == elementRectangle.getY() + elementRectangle.getHeight();
            case HORIZONTAL:
                return containerRectangle.getX() == elementRectangle.getX() ||
                        containerRectangle.getX() + containerRectangle.getWidth() == elementRectangle.getX() + elementRectangle.getWidth();
        }
        return true;
    }

    private boolean isElementIsInsideContainer(Rectangle containerRect, Rectangle rectangle) {
        return isRectangleContainsPoint(containerRect, rectangle.getX(), rectangle.getY()) &&
                isRectangleContainsPoint(containerRect, rectangle.getX(), rectangle.getY() + rectangle.getHeight()) &&
                isRectangleContainsPoint(containerRect, rectangle.getX() + rectangle.getWidth(), rectangle.getY()) &&
                isRectangleContainsPoint(containerRect, rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight());
    }

    private boolean isRectangleContainsPoint(Rectangle rectangle, int x, int y) {
        return rectangle.x <= x && x <= rectangle.x + rectangle.width &&
                rectangle.y <= y && y <= rectangle.y + rectangle.height;
    }

}
