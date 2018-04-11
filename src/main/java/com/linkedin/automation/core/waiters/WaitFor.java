package com.linkedin.automation.core.waiters;

import com.linkedin.automation.core.mobile_elements.interfaces.Touchable;
import com.linkedin.automation.core.tools.files.PropertyLoader;

/**
 * Created on 11.04.2018
 */
public class WaitFor {
    public static final int DEFAULT_TIMEOUT_MILLIS = Integer.parseInt(
            PropertyLoader.get(PropertyLoader.Property.DEFAULT_TIMEOUT, "30000"));

    public static void exist(Touchable element, int timeout) {
        try {
            Waiter.waitForCondition(element, "isExist", Waiter.Operators.EQUAL, true, timeout);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void exist(Touchable element) {
        exist(element, DEFAULT_TIMEOUT_MILLIS);
    }

    public static void notExist(Touchable element, int timeout) {
        try {
            Waiter.waitForCondition(element, "isExist", Waiter.Operators.EQUAL, false, timeout);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void notExist(Touchable element) {
        notExist(element, DEFAULT_TIMEOUT_MILLIS);
    }

    public static void displayed(Touchable element, int timeout) {
        try {
            Waiter.waitForCondition(element, "isDisplayed", Waiter.Operators.EQUAL, true, timeout);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void displayed(Touchable element) {
        displayed(element, DEFAULT_TIMEOUT_MILLIS);
    }

    public static void notDisplayed(Touchable element, int timeout) {
        try {
            Waiter.waitForCondition(element, "isDisplayed", Waiter.Operators.EQUAL, false, timeout);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void notDisplayed(Touchable element) {
        notDisplayed(element, DEFAULT_TIMEOUT_MILLIS);
    }
}
