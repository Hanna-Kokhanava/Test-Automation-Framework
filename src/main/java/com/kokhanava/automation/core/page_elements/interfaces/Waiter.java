package com.kokhanava.automation.core.page_elements.interfaces;

import java.time.Duration;

/**
 * Created on 11.04.2018
 * Interface contains all methods required to wait for some event.
 */
public interface Waiter {

    /**
     * Wait for element be exist
     *
     * @param timeout - time to wait
     */
    void waitForExist(Duration timeout);

    /**
     * Wait during default timeout for element be exist
     */
    void waitForExist();

    /**
     * Wait for element be non-exist
     *
     * @param timeout - time to wait
     */
    void waitForNotExist(Duration timeout);

    /**
     * Wait during default timeout for element be non-exist
     */
    void waitForNotExist();

    /**
     * Wait for element be displayed
     *
     * @param timeout - time to wait
     */
    void waitForDisplayed(Duration timeout);

    /**
     * Wait during default timeout for element be displayed
     */
    void waitForDisplayed();

    /**
     * Wait for element be not displayed
     *
     * @param timeout - time to wait
     */
    void waitForNotDisplayed(Duration timeout);

    /**
     * Wait during default timeout for element be not displayed
     */
    void waitForNotDisplayed();
}
