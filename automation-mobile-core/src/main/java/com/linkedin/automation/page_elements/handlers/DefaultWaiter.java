package com.linkedin.automation.page_elements.handlers;

import com.linkedin.automation.core.tools.files.PropertyLoader;
import com.linkedin.automation.page_elements.interfaces.Availability;
import com.linkedin.automation.page_elements.interfaces.Waiter;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static com.linkedin.automation.page_elements.handlers.DefaultWaiter.ComparisonOperator.EQUAL;

public class DefaultWaiter implements Waiter {
    public enum ComparisonOperator {EQUAL, NOT_EQUAL, LESS, LESS_EQUAL, GREATER, GREATER_EQUAL}

    public static final Duration DEFAULT_TIMEOUT = Duration.ofMillis(Integer.parseInt(
            PropertyLoader.get(PropertyLoader.Property.DEFAULT_TIMEOUT, "30000")));

    private Availability availabilityElement;
    private Object[] params;
    private Object value;
    private String methodName;
    private ComparisonOperator operator;


    public DefaultWaiter(Availability availabilityElement) {
        this.availabilityElement = availabilityElement;
    }

    private boolean applyMethod(Availability element) {
        Class<?> partypes[] = new Class[params.length];
        Object retMeth;
        Boolean retVal = false;

        for (int i = 0; i < params.length; i++) {
            partypes[i] = (params[i]).getClass();
        }

        try {
            Method method = element.getClass().getMethod(methodName, partypes);
            retMeth = value.getClass().cast(method.invoke(availabilityElement, params));
        } catch (Exception e) {
            Throwable cause = null != e.getCause() ? e.getCause() : e;
            System.out.println("Error in Waiter: " + cause.getMessage());
            cause.printStackTrace();
            return false;
        }

        switch (operator) {
            case EQUAL:
                retVal = value.equals(retMeth);
                break;
            case NOT_EQUAL:
                retVal = !value.equals(retMeth);
                break;
            case LESS:
                retVal = (compare(retMeth, value) < 0);
                break;
            case LESS_EQUAL:
                retVal = (compare(retMeth, value) <= 0);
                break;
            case GREATER:
                retVal = (compare(retMeth, value) > 0);
                break;
            case GREATER_EQUAL:
                retVal = (compare(retMeth, value) >= 0);
                break;
            default:
                System.out.println("Unsupported operator: " + operator);
                break;
        }

        return retVal;
    }

    @SuppressWarnings("unchecked")
    private int compare(Object c1, Object c2) {
        try {
            return ((Comparable<Object>) c1).compareTo(c2);
        } catch (ClassCastException e) {
            System.out.println("Cannot compare objects of type " + c1.getClass().getSimpleName());
            throw e;
        }
    }

    /**
     * Wrapper method to be called from methods of {@link Object} that implement
     * waiting for something by calling method {@link #methodName} of this
     * Object during specified timeout. Wait is considered successful when
     * return value of {@link #methodName} compared to {@link #value} using
     * operator {@link #operator} evaluates to true; if timeout expires before
     * that, exception is thrown.
     *
     * @param methodName - name of the method that performs the analysis
     * @param operator   - comparison operator, supported values are EQUAL and NOTEQUAL
     * @param value      - value to compare methodName's return value to
     * @param timeout    - timeout value
     * @param params     - parameters that need to be passed to methodName
     */
    private void waitForCondition(String methodName, ComparisonOperator operator, Object value, Duration timeout, Object... params) {
        this.params = params;
        this.value = value;
        this.methodName = methodName;
        this.operator = operator;

        Wait<Availability> wait = new FluentWait<>(availabilityElement)
                .withTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .withMessage("Timed out after " + timeout.getSeconds() + " seconds waiting for object [object='" + availabilityElement
                        + "', method='" + methodName + "', operator='" + operator + "', expected value='" + value + "']");

        wait.until(this::applyMethod);
    }

    //---- Impl -----

    @Override
    public void waitForExist(Duration timeout) {
        try {
            waitForCondition("isExist", EQUAL, true, timeout);
        } catch (WebDriverException wde) {
            logWaiterException(wde);
        }
    }

    @Override
    public void waitForExist() {
        waitForExist(DEFAULT_TIMEOUT);
    }

    @Override
    public void waitForNotExist(Duration timeout) {
        try {
            waitForCondition("isExist", EQUAL, false, timeout);
        } catch (WebDriverException wde) {
            logWaiterException(wde);
        }
    }

    @Override
    public void waitForNotExist() {
        waitForNotExist(DEFAULT_TIMEOUT);
    }

    @Override
    public void waitForDisplayed(Duration timeout) {
        try {
            waitForCondition("isDisplayed", EQUAL, true, timeout);
        } catch (WebDriverException wde) {
            logWaiterException(wde);
        }
    }

    @Override
    public void waitForDisplayed() {
        waitForDisplayed(DEFAULT_TIMEOUT);
    }

    @Override
    public void waitForNotDisplayed(Duration timeout) {
        try {
            waitForCondition("isDisplayed", EQUAL, false, timeout);
        } catch (WebDriverException wde) {
            logWaiterException(wde);
        }
    }

    @Override
    public void waitForNotDisplayed() {
        waitForNotDisplayed(DEFAULT_TIMEOUT);
    }

    private void logWaiterException(WebDriverException wde) {
        if (wde instanceof TimeoutException) {
            System.out.println(wde.getMessage().split("\n")[0]);
        } else {
            System.out.println(wde.getMessage());
        }
    }

}
