package com.linkedin.automation.core.waiters;

import com.linkedin.automation.core.mobile_elements.interfaces.Touchable;

import static com.linkedin.automation.core.waiters.Waiter.Operators;

import static com.linkedin.automation.core.waiters.WaitFor.DEFAULT_TIMEOUT_MILLIS;

/**
 * Created on 17.04.2018
 * Contains different checking implementations
 */
public class CheckThat {
    /**
     * Checks that element exists
     *
     * @param element the {@link Touchable} element
     * @param timeout - time to wait in milliseconds
     */
    public static void exist(Touchable element, int timeout) {
        Waiter.waitForCondition(element, "isExist", Operators.EQUAL, true, timeout);
    }

    /**
     * Checks that element exists during default timeout,
     * which is set the value up in the {@link WaitFor} class)
     *
     * @param element the {@link Touchable} element
     */
    public static void exist(Touchable element) {
        exist(element, DEFAULT_TIMEOUT_MILLIS);
    }

    /**
     * Checks that element displayed
     *
     * @param element the {@link Touchable} element
     * @param timeout - time to wait in milliseconds
     */
    public static void displayed(Touchable element, int timeout) {
        Waiter.waitForCondition(element, "isDisplayed", Operators.EQUAL, true, timeout);
    }

    /**
     * Checks that element displayed during default timeout
     *
     * @param element the {@link Touchable} element
     */
    public static void displayed(Touchable element) {
        displayed(element, DEFAULT_TIMEOUT_MILLIS);
    }

    /**
     * Checks that text is equal to the expected value
     *
     * @param element  the {@link Touchable} element
     * @param expected the expected value of the element
     * @param timeout  time to wait in milliseconds
     */
    public static void textEqual(Touchable element, String expected, int timeout) {
        Waiter.waitForCondition(element, "getText", Operators.EQUAL, expected, timeout);
    }

    /**
     * Checks that text is equal to the expected value during default timeout
     *
     * @param element  the {@link Touchable} element
     * @param expected the expected value of the element
     */
    public static void textEqual(Touchable element, String expected) {
        textEqual(element, expected, DEFAULT_TIMEOUT_MILLIS);
    }

    /**
     * Check that text is not equal to the expected value
     *
     * @param element  the {@link Touchable} element
     * @param expected the expected value of the element
     * @param timeout  time to wait in milliseconds
     */
    public static void textNotEqual(Touchable element, String expected, int timeout) {
        Waiter.waitForCondition(element, "getText", Operators.NOT_EQUAL, expected, timeout);
    }

    /**
     * Check that text not equal to expected value during default timeout.
     *
     * @param element  the {@link Touchable} element
     * @param expected the expected value of the element
     */
    public static void textNotEqual(Touchable element, String expected) {
        textNotEqual(element, expected, DEFAULT_TIMEOUT_MILLIS);
    }
}
