package com.linkedin.automation.services.ui.listeners;

import java.util.EventObject;

/**
 * Presents types of events that should perform some {@link IPageEventListener}
 */
public class AbstractEvent<EventType> extends EventObject {

    protected EventType type;

    public AbstractEvent(Object source, EventType type) {
        super(source);
        this.type = type;
    }

    public EventType getType() {
        return type;
    }
}
