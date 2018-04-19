package com.linkedin.automation.core.pagefactory.elementFactory;

import com.linkedin.automation.core.pagefactory.decorator.MobileElementDecorator;
import com.linkedin.automation.page_elements.block.MobileBlock;
import com.linkedin.automation.page_elements.element.WrappedMobileElement;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface ElementProxyFactory {

    <T extends WebElement> T createProxyForWebElement(
            Class<T> clazz,
            CacheableLocator locator,
            String name);


    <T extends WebElement> List<T> createProxyForWebElementList(
            CacheableLocator locator,
            String name);

    <T extends WrappedMobileElement> List<T> createProxyForWrappedMobileElementList(
            Class<T> clazz,
            CacheableLocator locator,
            String name);


    <T extends MobileBlock> List<T> createProxyForBlockElementList(
            Class<T> clazz,
            CacheableLocator locator,
            String name,
            MobileElementDecorator decorator);
}
