package com.linkedin.automation.core.mobile_elements.interfaces;

public interface Checkable {

    void checkExist();

    void checkDisplayed();

    void checkTextEqual(String expected);

    void checkTextNotEqual(String expected);
}
