package com.linkedin.automation.page_elements.handlers.scroll;

import com.linkedin.automation.core.device.functions.Direction;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.remote.RemoteWebElement;

import java.time.Duration;
import java.util.HashMap;

public class IOSDefaultScrollHandler extends DefaultScrollHandler {

    public IOSDefaultScrollHandler() {
        this(null);
    }

    public IOSDefaultScrollHandler(Widget widget) {
        super(widget);
    }

    @Override
    public void scroll(Direction direction) {
        HashMap<String, String> scrollObject = new HashMap<>();
        scrollObject.put("direction", direction.getDir().toLowerCase());
        scrollObject.put("element", ((RemoteWebElement) getWidget().getWrappedElement()).getId());
        ((AppiumDriver) getWidget().getWrappedDriver()).executeScript("mobile: scroll", scrollObject);
    }

    @Override
    public void scroll(double startX, double startY, double endX, double endY, Duration duration) {
        double xOffset = endX - startX;
        double yOffset = endY - startY;
        scrollAsTouchAction(startX, startY, xOffset, yOffset, duration);
    }
}