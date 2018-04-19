package com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers;

import com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers.locatorhandlers.ICacheableLocatorHandler;
import com.linkedin.automation.page_elements.element.WrappedMobileElement;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.linkedin.automation.core.pagefactory.loader.MobileElementLoader.createWrappedMobileElement;

/**
 * Intercepts requests to the list of descendant of {@link WrappedMobileElement}
 *
 * @param <T> the type of the class of descendant
 */
public class WrappedMobileElementListProxyHandler<T extends WrappedMobileElement> implements MethodInterceptor {
    private final Class<T> typifiedElementClass;
    private final ICacheableLocatorHandler locatorHandler;

    private int previousElementListHashCode = 0;// do not use 1 - it hash code for empty list
    private List<T> typifiedElements;

    public WrappedMobileElementListProxyHandler(Class<T> typifiedElementClass, ICacheableLocatorHandler locatorHandler) {
        this.typifiedElementClass = typifiedElementClass;
        this.locatorHandler = locatorHandler;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy proxyMeth) throws Throwable {
        if ("toString".equals(method.getName())) {
            return locatorHandler.getName();
        }

        if ("revalidate".equals(method.getName())) {
            // reset cashed values in locator
            return locatorHandler.resetLocator();
        }

        List<WebElement> elementList = locatorHandler.findElements();
        if (previousElementListHashCode != elementList.hashCode()) {
            typifiedElements = collectTypifiedList(elementList);
            previousElementListHashCode = elementList.hashCode();
        }

        try {
            return method.invoke(typifiedElements, objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }

    private List<T> collectTypifiedList(List<WebElement> elementList) {
        return IntStream.range(0, elementList.size())
                .mapToObj(i -> createWrappedMobileElement(typifiedElementClass, elementList.get(i), String.format("%s [%d]", locatorHandler.getName(), i)))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
