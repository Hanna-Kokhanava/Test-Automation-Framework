package com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Intercepts requests to the list of descendant of {@link WebElement}
 * @param <T> the type of the class of descendant
 */
public class WebElementListProxyHandler<T extends WebElement> implements MethodInterceptor {
    private final ElementLocator locator;
    private final String name;

    public WebElementListProxyHandler(ElementLocator locator, String name) {
        this.locator = locator;
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy proxyMeth) throws Throwable {
        if ("toString".equals(method.getName())) {
            return name;
        }

        List<T> elements = (List<T>) locator.findElements();

        try {
            return method.invoke(elements, objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}
