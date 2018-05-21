package com.linkedin.automation.services.ui.listeners;

public interface IPageActionListener extends IExtractorInfo {
    /**
     * Notifies that some action on the page should be performed
     *
     * @param action               instance of {@link PageAction}
     * @param additionalActionInfo some additional information about action, source
     */
    void firePageAction(PageAction action, Object... additionalActionInfo);
}
