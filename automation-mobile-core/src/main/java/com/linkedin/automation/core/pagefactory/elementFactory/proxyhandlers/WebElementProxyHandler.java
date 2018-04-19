package com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers;

import com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers.locatorhandlers.ICacheableLocatorHandler;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Intercepts requests to {@link org.openqa.selenium.internal.WrapsElement} in
 */
public class WebElementProxyHandler<T extends WebElement> implements MethodInterceptor {

    private final ICacheableLocatorHandler locatorHandler;

    public WebElementProxyHandler(ICacheableLocatorHandler locatorHandler) {
        this.locatorHandler = locatorHandler;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy proxyMeth) throws Throwable {
        if ("toString".equals(method.getName())) {
            return locatorHandler.getName();
        }

        // this method invoke from Availability interface only
        if ("getWrappedElement".equals(method.getName())) {
            // check only valid reference to the element
            locatorHandler.resetLocator();
            return locatorHandler.findElement();
        }
        // take something action to update the WebElement, invoke from Validator interface
        if ("revalidate".equals(method.getName())) {
            // reset cashed values in locator
            return locatorHandler.resetLocator();
        }

        Object invokeResult = Void.TYPE;// invoke result flag. Do not use null as flag because invoke void method returns null.
        do {
            try {
                invokeResult = method.invoke(locatorHandler.findElement(), objects);
            } catch (InvocationTargetException ex) {
                if (null == ex.getCause() || NoSuchElementException.class.isAssignableFrom(ex.getCause().getClass())) {
                    throw ex;
                }
                // StaleElementReferenceException happened for Android only
                if (StaleElementReferenceException.class.isAssignableFrom(ex.getCause().getClass())) {
                    // element was cached and it reference is expired, need to reset cache
                    locatorHandler.resetLocator();
                }
            }
        } while (Void.TYPE == invokeResult);// the method invoke again after reset cache of locator

        return invokeResult;
    }
}
