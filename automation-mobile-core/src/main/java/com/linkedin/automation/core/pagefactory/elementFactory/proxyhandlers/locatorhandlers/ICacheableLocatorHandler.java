package com.linkedin.automation.core.pagefactory.elementFactory.proxyhandlers.locatorhandlers;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;

public interface ICacheableLocatorHandler {

    String getName();

    ElementLocator getLocator();

    WebElement findElement();

    List<WebElement> findElements();

    boolean resetLocator();
}
