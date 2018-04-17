package com.linkedin.automation.core.mobile_elements.element;

import org.openqa.selenium.WebElement;

/**
 * Created on 17.04.2018
 * Specifies wrapped {@link WebElement} - Image control
 */
public class Image extends MobileElement {

    protected Image(WebElement wrappedElement) {
        super(wrappedElement);
    }
}
