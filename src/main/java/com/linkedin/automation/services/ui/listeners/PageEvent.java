package com.linkedin.automation.services.ui.listeners;

public class PageEvent extends AbstractEvent<PageEvent.Type> {
    public enum Type {
        //---------- Generic events ----------
        CONTENT_CHANGE,
        SCROLL_PERFORMED,

    }

    public PageEvent(Object source, Type event) {
        super(source, event);
    }
}
