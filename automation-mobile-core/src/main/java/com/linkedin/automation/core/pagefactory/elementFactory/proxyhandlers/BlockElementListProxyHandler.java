package com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers;

import com.linkedin.automation.core.pagefactory.decorator.MobileElementDecorator;
import com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers.locatorhandlers.ICacheableLocatorHandler;
import com.linkedin.automation.page_elements.block.MobileBlock;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.linkedin.automation.core.pagefactory.loader.MobileElementLoader.createBlockElement;

/**
 * Intercepts requests to the list of descendant of {@link MobileBlock}
 *
 * @param <T> the type of the class of descendant
 */
public class BlockElementListProxyHandler<T extends MobileBlock> implements MethodInterceptor {
    private final Class<T> blockElementClass;
    private final ICacheableLocatorHandler locatorHandler;
    private final MobileElementDecorator fieldDecorator;

    public BlockElementListProxyHandler(Class<T> blockElementClass, ICacheableLocatorHandler locatorHandler, MobileElementDecorator decorator) {
        this.blockElementClass = blockElementClass;
        this.locatorHandler = locatorHandler;
        this.fieldDecorator = decorator;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy proxyMeth) throws Throwable {
        if ("toString".equals(method.getName())) {
            return locatorHandler.getName();
        }

        List<T> blockElements = new ArrayList<>();
        List<WebElement> elements = locatorHandler.findElements();
        int elementNumber = 0;

        for (WebElement element : elements) {
            String blockElementName = String.format("%s [%d]", locatorHandler.getName(), elementNumber);

            T blockElement = createBlockElement(blockElementClass, element, blockElementName, fieldDecorator);

            blockElements.add(blockElement);
            elementNumber++;
        }

        try {
            return method.invoke(blockElements, objects);
        } catch (InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}
