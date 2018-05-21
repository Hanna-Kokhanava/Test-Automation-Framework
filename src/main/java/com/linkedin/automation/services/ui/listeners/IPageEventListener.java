package com.linkedin.automation.services.ui.listeners;

public interface IPageEventListener extends IExtractorInfo {
    /**
     * Notifies that some event on the page is happened
     *
     * @param event    instance of {@link PageEvent}
     * @param additionalEventInfo some additional information about event, source
     */
    void firePageEvent(PageEvent event, Object... additionalEventInfo);
}
