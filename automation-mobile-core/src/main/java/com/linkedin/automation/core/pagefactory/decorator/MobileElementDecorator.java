package com.linkedin.automation.core.pagefactory.decorator;

import com.linkedin.automation.core.pagefactory.MobileElementUtils;
import com.linkedin.automation.core.pagefactory.OverrideWidgetReader;
import com.linkedin.automation.core.pagefactory.elementFactory.ElementProxyFactory;
import com.linkedin.automation.core.pagefactory.elementFactory.MobileElementProxyFactory;
import com.linkedin.automation.page_elements.block.MobileBlock;
import com.linkedin.automation.page_elements.element.WrappedMobileElement;
import io.appium.java_client.HasSessionDetails;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumElementLocatorFactory;
import io.appium.java_client.pagefactory.DefaultElementByBuilder;
import io.appium.java_client.pagefactory.TimeOutDuration;
import io.appium.java_client.pagefactory.WidgetByBuilder;
import io.appium.java_client.pagefactory.locator.CacheableElementLocatorFactory;
import io.appium.java_client.pagefactory.locator.CacheableLocator;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.linkedin.automation.core.pagefactory.MobileElementUtils.*;
import static com.linkedin.automation.core.pagefactory.loader.MobileElementLoader.createBlockElement;
import static com.linkedin.automation.core.pagefactory.loader.MobileElementLoader.createWrappedMobileElement;
import static io.appium.java_client.pagefactory.utils.WebDriverUnpackUtility.unpackWebDriverFromSearchContext;
import static ru.yandex.qatools.htmlelements.utils.HtmlElementUtils.getGenericParameterClass;

/**
 * Decorator which is used to decorate fields of blocks and page objects
 * Decorate the following fields :
 * {@link WebElement}
 * List of {@link WebElement}'s
 * Block of elements {@link MobileBlock} and list of blocks
 * Wrapped element {@link WrappedMobileElement} and list of wrapped elements
 */
public class MobileElementDecorator implements FieldDecorator {

    private static long DEFAULT_IMPLICITLY_WAIT_TIMEOUT = 1;
    private static TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;

    private final WebDriver originalDriver;
    private final HasSessionDetails hasSessionDetails;
    private final TimeOutDuration timeOutDuration;

    private final String platform;
    private final String automation;

    private ElementProxyFactory elementProxyFactory;
    private CacheableElementLocatorFactory elementLocatorFactory;
    private CacheableElementLocatorFactory blockLocatorFactory;
    private SearchContext context;

    public MobileElementDecorator(SearchContext context) {
        this(context, DEFAULT_IMPLICITLY_WAIT_TIMEOUT, DEFAULT_TIMEUNIT);
    }

    public MobileElementDecorator(SearchContext context, long implicitlyWaitTimeOut,
                                  TimeUnit timeUnit) {
        this(context, new TimeOutDuration(implicitlyWaitTimeOut, timeUnit));
    }

    public MobileElementDecorator(SearchContext context, TimeOutDuration timeOutDuration) {
        this(context, timeOutDuration, new MobileElementProxyFactory());
    }

    /**
     * Create field decorator
     *
     * @param context             is an instance of {@link org.openqa.selenium.SearchContext}
     *                            It may be the instance of {@link org.openqa.selenium.WebDriver}
     *                            or {@link org.openqa.selenium.WebElement} or
     *                            {@link io.appium.java_client.pagefactory.Widget} or some other user's
     *                            extension/implementation.
     * @param timeOutDuration     is a desired duration of the waiting for an element presence.
     * @param elementProxyFactory element proxy factory for creating lazy proxies
     */
    public MobileElementDecorator(SearchContext context, TimeOutDuration timeOutDuration, ElementProxyFactory elementProxyFactory) {
        this.originalDriver = unpackWebDriverFromSearchContext(context);
        this.timeOutDuration = timeOutDuration;
        this.elementProxyFactory = elementProxyFactory;
        if (originalDriver == null
                || !HasSessionDetails.class.isAssignableFrom(originalDriver.getClass())) {
            hasSessionDetails = null;
            platform = null;
            automation = null;
        } else {
            hasSessionDetails = HasSessionDetails.class.cast(originalDriver);
            platform = hasSessionDetails.getPlatformName();
            automation = hasSessionDetails.getAutomationName();
        }

        setContext(context);
    }

    public SearchContext getContext() {
        return context;
    }

    /**
     * Set search context and reinit locators factories
     *
     * @param context The context to use when finding the elements
     */
    public void setContext(SearchContext context) {
        this.context = context;

        elementLocatorFactory = new AppiumElementLocatorFactory(context, timeOutDuration,
                new DefaultElementByBuilder(platform, automation));
        blockLocatorFactory = new AppiumElementLocatorFactory(context, timeOutDuration,
                new WidgetByBuilder(platform, automation));
    }

    /**
     * @param loader class loader is ignored by current implementation
     * @param field  is {@link java.lang.reflect.Field} of page object which is supposed to be
     *               decorated.
     * @return a field value or null.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object decorate(ClassLoader loader, Field field) {
        try {
            if (isWrappedMobileElement(field.getType())) {
                return decorateWrappedMobileElement(field);
            }
            if (isBlockElement(field.getType())) {
                return decorateBlockElement(field);
            }
            if (isWebElement(field.getType()) && !field.getName().equals("wrappedElement")) {
                return decorateWebElement((Class<WebElement>) field.getType(), field,
                        elementLocatorFactory.createLocator(field));
            }
            if (isListOf(field, MobileElementUtils::isWrappedMobileElement)) {
                return decorateWrappedMobileElementList(field);
            }
            if (isListOf(field, MobileElementUtils::isBlockElement)) {
                return decorateBlockElementList(field);
            }
            if (isListOf(field, MobileElementUtils::isWebElement)) {
                return decorateWebElementList(field);
            }
            return null;
        } catch (ClassCastException ignore) {
            return null;
        }

    }

    private <T extends WebElement> T decorateWebElement(Class<T> clazz, Field field, CacheableLocator locator) {
        return elementProxyFactory.createProxyForWebElement(clazz, locator, getElementName(field));
    }

    @SuppressWarnings("unchecked")
    private <T extends WrappedMobileElement> T decorateWrappedMobileElement(Field field) {
        MobileElement elementToWrap = decorateWebElement(MobileElement.class, field,
                elementLocatorFactory.createLocator(field));

        return createWrappedMobileElement((Class<T>) field.getType(), elementToWrap, getElementName(field));
    }

    @SuppressWarnings("unchecked")
    private <T extends MobileBlock> T decorateBlockElement(Field field) {
        MobileElement elementToWrap = decorateWebElement(MobileElement.class, field,
                blockLocatorFactory.createLocator(field));

        Class<? extends MobileBlock> convenientWidgetClass = OverrideWidgetReader.getMobileNativeWidgetClass(
                (Class<T>) field.getType(), field, platform, automation);

        return createBlockElement((Class<T>) convenientWidgetClass, elementToWrap, getElementName(field), this);
    }

    private <T extends WebElement> List<T> decorateWebElementList(Field field) {
        CacheableLocator locator = elementLocatorFactory.createLocator(field);

        return elementProxyFactory.createProxyForWebElementList(locator, getElementName(field));
    }

    @SuppressWarnings("unchecked")
    private <T extends WrappedMobileElement> List<T> decorateWrappedMobileElementList(Field field) {
        Class<T> elementClass = (Class<T>) getGenericParameterClass(field);
        CacheableLocator locator = elementLocatorFactory.createLocator(field);

        return elementProxyFactory.createProxyForWrappedMobileElementList(elementClass, locator, getElementName(field));
    }

    @SuppressWarnings("unchecked")
    private <T extends MobileBlock> List<T> decorateBlockElementList(Field field) {
        CacheableLocator locator = blockLocatorFactory.createLocator(field);

        Class<T> elementClass = (Class<T>) getGenericParameterClass(field);
        Class<? extends MobileBlock> convenientWidgetClass = OverrideWidgetReader.getMobileNativeWidgetClass(
                elementClass, field, platform, automation);

        return elementProxyFactory.createProxyForBlockElementList((Class<T>) convenientWidgetClass, locator, getElementName(field), this);
    }
}
