package com.linkedin.automation.page_elements.interfaces;

/**
 * Interface contains all methods required to check availability of element
 */
public interface Availability {
    /**
     * Checks that reference to the mobile element is present
     *
     * @return true that reference present
     */
    boolean isExist();

    /**
     * Checks that mobile element is present and visible
     *
     * @return true that element present
     */
    boolean isDisplayed();

}
