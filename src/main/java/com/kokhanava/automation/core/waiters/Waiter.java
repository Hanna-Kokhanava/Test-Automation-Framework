package com.kokhanava.automation.core.waiters;

import com.kokhanava.automation.core.logger.Logger;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Created on 11.04.2018
 */
public class Waiter {

    public enum Operators {EQUAL, NOT_EQUAL, LESS, GREATER}

    private static Object[] params;
    private static Object value;
    private static String methodName;
    private static Operators operator;
    private static final Object waiteForConditionSyncObject = new Object();

    private static boolean applyMethod(Object element) {
        Class<?> partypes[] = new Class[Waiter.params.length];
        Object retMeth;
        Boolean retVal = false;

        for (int i = 0; i < Waiter.params.length; i++) {
            partypes[i] = (Waiter.params[i]).getClass();
        }

        try {
            Method meth = element.getClass().getMethod(Waiter.methodName, partypes);
            retMeth = Waiter.value.getClass().cast(meth.invoke(element, Waiter.params));
        } catch (Exception e) {
            Throwable cause = null != e.getCause() ? e.getCause() : e;
            Logger.error("Error in Waiter: " + cause.getMessage());
            cause.printStackTrace();
            return false;
        }

        switch (Waiter.operator) {
            case EQUAL:
                retVal = Waiter.value.equals(retMeth);
                break;
            case NOT_EQUAL:
                retVal = !Waiter.value.equals(retMeth);
                break;
            case LESS:
                retVal = (compare(retMeth, Waiter.value) < 0);
                break;
            case GREATER:
                retVal = (compare(retMeth, Waiter.value) > 0);
                break;
            default:
                Logger.error("Unsupported operator: " + Waiter.operator);
                break;
        }
        return retVal;
    }

    @SuppressWarnings("unchecked")
    private static int compare(Object c1, Object c2) {
        try {
            return ((Comparable<Object>) c1).compareTo(c2);
        } catch (ClassCastException e) {
            Logger.error("Cannot compare objects of type " + c1.getClass().getSimpleName());
            throw e;
        }
    }

    public static void waitForCondition(Object object, String methodName,
                                        Operators operator, Object value, int timeout, Object... params) {
        synchronized (waiteForConditionSyncObject) {
            Waiter.params = params;
            Waiter.value = value;
            Waiter.methodName = methodName;
            Waiter.operator = operator;

            Wait<Object> wait = new FluentWait<>(object)
                    .withTimeout(timeout, TimeUnit.MILLISECONDS)
                    .pollingEvery(1, TimeUnit.SECONDS)
                    .withMessage("Timed out after " + (timeout / 1000) + " seconds waiting for object [object='" + object
                            + "', method='" + methodName + "', operator='" + operator + "', expected value='" + value + "']");

            wait.until(Waiter::applyMethod);
        }
    }
}
