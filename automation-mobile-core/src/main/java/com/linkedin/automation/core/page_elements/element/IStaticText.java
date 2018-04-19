package com.linkedin.automation.core.page_elements.element;

public interface IStaticText {

    /**
     * For elements that contain text in another element instead of 'name' or 'value' attribute
     *
     * @return text of a child element
     */
    String getStaticText();
}