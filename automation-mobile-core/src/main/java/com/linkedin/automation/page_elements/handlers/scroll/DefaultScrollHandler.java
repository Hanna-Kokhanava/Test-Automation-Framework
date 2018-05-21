package com.linkedin.automation.page_elements.handlers.scroll;

import com.linkedin.automation.page_elements.interfaces.Scrollable;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.Widget;
import org.openqa.selenium.Dimension;

import java.time.Duration;

public abstract class DefaultScrollHandler implements Scrollable {
    private Widget widget;

    public DefaultScrollHandler(Widget widget) {
        this.widget = widget;
    }


    protected void scrollAsTouchAction(double touchX, double touchY, double moveToX, double moveToY, Duration duration) {
        if (null == getWidget()) {
            return;
        }

        Dimension size = getWidget().getWrappedElement().getSize();
        int convertTouchX = Double.valueOf(touchX <= 1.0 ? touchX * size.width : touchX).intValue();
        int convertTouchY = Double.valueOf(touchY <= 1.0 ? touchY * size.height : touchY).intValue();
        int convertMoveToX = Double.valueOf(moveToX <= 1.0 ? moveToX * size.width : moveToX).intValue();
        int convertMoveToY = Double.valueOf(moveToY <= 1.0 ? moveToY * size.height : moveToY).intValue();

        scrollAsTouchAction(convertTouchX, convertTouchY, convertMoveToX, convertMoveToY, duration);
    }

    protected void scrollAsTouchAction(int touchX, int touchY, int moveToX, int moveToY, Duration duration) {
        if (null == getWidget()) {
            return;
        }

        TouchAction touchAction = new TouchAction((MobileDriver) getWidget().getWrappedDriver());
        // appium converts press-wait-moveto-release to a swipe action
        touchAction.press(getWidget().getWrappedElement(), touchX, touchY)
                .waitAction(duration)
                .moveTo(moveToX, moveToY)
                .release()
                .perform();
    }

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }
}