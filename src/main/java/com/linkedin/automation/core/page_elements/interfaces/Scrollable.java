package com.linkedin.automation.core.page_elements.interfaces;

import com.linkedin.automation.core.device.functions.Direction;

import java.time.Duration;

/**
 * Is used for element scrolling
 */
public interface Scrollable {

    /**
     * Scrolls element in some direction
     */
    void scroll(Direction direction);

    /**
     * Scrolls element from start location to the end location over the defined time
     *
     * @param startX   X of start position
     * @param startY   Y of start position
     * @param endX     X of end position
     * @param endY     Y of end position
     * @param duration duration of action
     */
    void scroll(double startX, double startY, double endX, double endY, Duration duration);
}
