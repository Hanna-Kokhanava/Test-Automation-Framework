package com.linkedin.automation.services.ui.handlers.scroll.constraints;

import com.linkedin.automation.page_elements.interfaces.Availability;

public class ElementSearcherDisplayed extends ElementSearcher {

    private final Availability element;

    public ElementSearcherDisplayed(Availability element) {
        this.element = element;
    }

    public boolean check() {
        return element.isDisplayed();
    }
}
