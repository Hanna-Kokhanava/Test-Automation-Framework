package com.linkedin.automation.core.mobile_elements.interfaces;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

public interface Proxifyable extends WrapsElement {

    WebElement getNotProxiedElement();
}