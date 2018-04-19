package com.linkedin.automation.core.page_elements.interfaces;

/**
 * Is used for element size changing events
 */
public interface Resizeable {
    /**
     * Reduces the size of elements
     */
    void pinch();

    /**
     * Increases the size of elements
     */
    void zoom();
}
