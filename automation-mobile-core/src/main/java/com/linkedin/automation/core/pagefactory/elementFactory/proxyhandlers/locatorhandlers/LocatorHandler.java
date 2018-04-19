package com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers.locatorhandlers;

import io.appium.java_client.pagefactory.locator.CacheableLocator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.Field;
import java.util.List;

public class LocatorHandler implements ICacheableLocatorHandler {
    private CacheableLocator locator;
    private String name;
    private int previousElementHashCode = 0;
    private int previousElementListHashCode = 0;// do not use 1 - it hash code for empty list
    private final Dimension collapsedDimension = new Dimension(0, 0);

    public LocatorHandler(CacheableLocator locator, String name) {
        setLocator(locator);
        setName(name);
    }

    public void setLocator(CacheableLocator locator) {
        this.locator = locator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ElementLocator getLocator() {
        return locator;
    }

    public String getName() {
        return name;
    }

    public WebElement findElement() {
        WebElement element = getLocator().findElement();

        if (!locator.isLookUpCached()) {
            return element;
        }

        if (previousElementHashCode != element.hashCode()) {
            previousElementHashCode = element.hashCode();
            return element;
        }

        // if element not valid try to reset locator
        if (!isElementValid(element) && resetLocator()) {
            // if locator reset cache (method resetLocator() returned true) try to find elements again
            element = getLocator().findElement();
            previousElementHashCode = element.hashCode();
        }
        return element;
    }

    public List<WebElement> findElements() {
        List<WebElement> elementList = getLocator().findElements();

        if (!locator.isLookUpCached()) {
            return elementList;
        }

        if (previousElementListHashCode != elementList.hashCode()) {
            previousElementListHashCode = elementList.hashCode();
            return elementList;
        }

        boolean isResetLocatorNeed = elementList.isEmpty() || !isElementValid(elementList.get(0));

        // if collection of elements is empty or not valid try to reset locator
        if (isResetLocatorNeed && resetLocator()) {
            // if locator reset cache (method resetLocator() returned true) try to find elements again
            elementList = getLocator().findElements();
            previousElementListHashCode = elementList.hashCode();
        }
        return elementList;
    }

    private boolean isElementValid(WebElement element) {
        try {
            // check that element is not collapsed (for iOS)
            return !element.getSize().equals(collapsedDimension);
        } catch (Exception ex) { // for Android and issue with NPE inside RemoteWebElement.getSize()
            return false;
        }
    }

    public boolean resetLocator() {
        Class locatorClass = getLocator().getClass();
        if (!CacheableLocator.class.isAssignableFrom(locatorClass) || !((CacheableLocator) getLocator()).isLookUpCached()) {
            return false;
        }

        try {
            Field cashElementField = locatorClass.getDeclaredField("cachedElement");
            Field cashListField = locatorClass.getDeclaredField("cachedElementList");
            cashElementField.setAccessible(true);
            cashListField.setAccessible(true);
            cashElementField.set(getLocator(), null);
            cashListField.set(getLocator(), null);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}

