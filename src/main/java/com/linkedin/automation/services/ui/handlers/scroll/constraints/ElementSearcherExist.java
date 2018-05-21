package com.linkedin.automation.services.ui.handlers.scroll.constraints;

import com.linkedin.automation.page_elements.interfaces.Availability;

public class ElementSearcherExist extends ElementSearcher {

    private final Availability element;

    public ElementSearcherExist(Availability element) {
        this.element = element;
    }

    public boolean check() {
        return element.isExist();
    }
}
