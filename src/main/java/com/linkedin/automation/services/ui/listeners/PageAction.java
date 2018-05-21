package com.linkedin.automation.services.ui.listeners;


/**
 * Presents types of actions that should perform some {@link IPageActionListener}
 */
public class PageAction extends AbstractEvent<PageAction.Type> {
    public enum Type {

    }

    public PageAction(Object source, Type type) {
        super(source, type);
    }
}
