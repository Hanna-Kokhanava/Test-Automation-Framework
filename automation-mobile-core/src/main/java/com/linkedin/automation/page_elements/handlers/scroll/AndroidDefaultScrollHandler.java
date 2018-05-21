package com.linkedin.automation.page_elements.handlers.scroll;

import com.linkedin.automation.core.device.functions.Direction;
import io.appium.java_client.pagefactory.Widget;

import java.time.Duration;

public class AndroidDefaultScrollHandler extends DefaultScrollHandler {

    public AndroidDefaultScrollHandler() {
        super(null);
    }

    public AndroidDefaultScrollHandler(Widget widget) {
        super(widget);
    }

    @Override
    public void scroll(Direction direction) {
        scrollAsTouchAction(direction.getStartX(), direction.getStartY(), direction.getEndX(), direction.getEndY(), Duration.ofSeconds(1));
    }

    @Override
    public void scroll(final double startX, final double startY, final double endX, final double endY, Duration duration) {
        scrollAsTouchAction(startX, startY, endX, endY, duration);
    }

}