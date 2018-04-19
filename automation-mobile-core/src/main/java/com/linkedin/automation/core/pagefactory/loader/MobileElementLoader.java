package com.linkedin.automation.core.pagefactory.loader;

import com.linkedin.automation.core.pagefactory.decorator.MobileElementDecorator;
import com.linkedin.automation.page_elements.block.MobileBlock;
import com.linkedin.automation.page_elements.element.WrappedMobileElement;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Contains methods for blocks of elements initialization and elements initialization.
 */
public class MobileElementLoader {

    public static <T extends WrappedMobileElement> T createWrappedMobileElement(Class<T> elementClass, WebElement elementToWrap,
                                                                                String name) {
        try {
            T instance = elementClass.getConstructor(WebElement.class).newInstance(elementToWrap);
            instance.setName(name);
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends MobileBlock> T createBlockElement(Class<T> elementClass, WebElement elementToWrap,
                                                               String name, MobileElementDecorator decorator) {
        try {
            T instance = elementClass.getConstructor(WebElement.class).newInstance(elementToWrap);
            instance.setName(name);
            // Recursively initialize elements of the block
            SearchContext searchContext = decorator.getContext();
            decorator.setContext(elementToWrap);
            PageFactory.initElements(decorator, instance);
            decorator.setContext(searchContext);
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
