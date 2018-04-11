package com.linkedin.automation.core.mobile_elements.interfaces;

/**
 * Created on 11.04.2018
 */
public interface Waitable {

    void waitForExist();

    void waitForExist(int timeout);

    void waitForNotExist();

    void waitForNotExist(int timeout);

    void waitForDisplayed();

    void waitForNotDisplayed();

    void waitForNotDisplayed(int timeout);
}
