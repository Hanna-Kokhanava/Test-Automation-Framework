package com.linkedin.automation.core.pagefactory.elementFactory;

import com.linkedin.automation.core.pagefactory.decorator.MobileElementDecorator;
import com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers.BlockElementListProxyHandler;
import com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers.WebElementListProxyHandler;
import com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers.WebElementProxyHandler;
import com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers.WrappedMobileElementListProxyHandler;
import com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers.locatorhandlers.ICacheableLocatorHandler;
import com.linkedin.automation.page_elements.block.MobileBlock;
import com.linkedin.automation.page_elements.element.WrappedMobileElement;
import com.linkedin.automation.page_elements.interfaces.Validator;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import net.sf.cglib.proxy.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

import java.util.List;

/**
 * Contains factory methods for creating lazy proxies
 */
public abstract class AbstractMobileElementProxyFactory implements ElementProxyFactory {

    protected abstract ICacheableLocatorHandler createLocatorHandler(CacheableLocator locator, String name);

    /**
     * @see Proxy#BAD_OBJECT_METHOD_FILTER
     */
    private CallbackFilter BAD_OBJECT_METHOD_FILTER = method -> {
        if (method.getDeclaringClass().getName().equals("java.lang.Object")) {
            String name = method.getName();
            if (!(name.equals("hashCode") ||
                    name.equals("equals") ||
                    name.equals("toString"))) {
                return 1;
            }
        }
        return 0;
    };

    @SuppressWarnings("unchecked")
    public <T extends WebElement> T createProxyForWebElement(
            Class<T> clazz,
            CacheableLocator locator,
            String name) {

        Enhancer e = new Enhancer();
        e.setSuperclass(clazz);
        e.setInterfaces(new Class[]{WrapsElement.class, Validator.class});
        e.setCallbacks(new Callback[]{
                new WebElementProxyHandler<T>(createLocatorHandler(locator, name)),
                NoOp.INSTANCE
        });
        e.setCallbackFilter(BAD_OBJECT_METHOD_FILTER);
        return (T) e.create();
    }

    @SuppressWarnings("unchecked")
    public <T extends WebElement> List<T> createProxyForWebElementList(
            CacheableLocator locator,
            String name) {

        Enhancer e = new Enhancer();
        e.setSuperclass(List.class);
        e.setCallbacks(new Callback[]{
                new WebElementListProxyHandler<T>(locator, name),
                NoOp.INSTANCE
        });
        e.setCallbackFilter(BAD_OBJECT_METHOD_FILTER);
        return (List<T>) e.create();
    }

    @SuppressWarnings("unchecked")
    public <T extends WrappedMobileElement> List<T> createProxyForWrappedMobileElementList(
            Class<T> clazz,
            CacheableLocator locator,
            String name) {

        Enhancer e = new Enhancer();
        e.setInterfaces(new Class[]{List.class, Validator.class});
        e.setCallbacks(new Callback[]{
                new WrappedMobileElementListProxyHandler<>(clazz, createLocatorHandler(locator, name)),
                NoOp.INSTANCE
        });
        e.setCallbackFilter(BAD_OBJECT_METHOD_FILTER);
        return (List<T>) e.create();
    }

    @SuppressWarnings("unchecked")
    public <T extends MobileBlock> List<T> createProxyForBlockElementList(
            Class<T> clazz,
            CacheableLocator locator,
            String name,
            MobileElementDecorator decorator) {

        Enhancer e = new Enhancer();
        e.setSuperclass(List.class);
        e.setCallbacks(new Callback[]{
                new BlockElementListProxyHandler<>(clazz, createLocatorHandler(locator, name), decorator),
                NoOp.INSTANCE
        });
        e.setCallbackFilter(BAD_OBJECT_METHOD_FILTER);
        return (List<T>) e.create();
    }
}
