package com.linkedin.automation.services.ui;

/**
 * Class provides access to the element of page
 */
public interface PageElementProvider<ElementType> {
    /**
     * Returns element of page
     *
     * @return page element
     */
    ElementType getPageElement();
}
