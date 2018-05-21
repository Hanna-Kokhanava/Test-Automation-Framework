package com.linkedin.automation.services.ui.listeners;

import com.google.inject.Inject;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public class PageEventNotifier {
    @Inject
    private Set<IPageEventListener> pageEventListeners;
    @Inject
    private Set<IPageActionListener> pageActionListeners;

    public void notifyAllListeners(@Nonnull PageEvent event, Object... additionalEventInfo) {
        getPageEventListeners().forEach(listener -> listener.firePageEvent(event, additionalEventInfo));
    }

    public void notifyAllTypes(@Nonnull List<Class> types, @Nonnull PageEvent event, Object... additionalEventInfo) {
        if (types.isEmpty()) {
            return;
        }
        getPageEventListeners().stream().
                filter(listenerType -> types.contains(listenerType.getClass())).
                forEach(listener -> listener.firePageEvent(event, additionalEventInfo));
    }

    public void notifyAllListeners(@Nonnull PageAction action, Object... additionalEventInfo) {
        getPageActionListeners().forEach(listener -> listener.firePageAction(action, additionalEventInfo));
    }

    public void notifyAllTypes(@Nonnull List<Class> types, @Nonnull PageAction action, Object... additionalEventInfo) {
        if (types.isEmpty()) {
            return;
        }
        getPageActionListeners().stream().
                filter(listenerType -> types.contains(listenerType.getClass())).
                forEach(listener -> listener.firePageAction(action, additionalEventInfo));
    }


    private Set<IPageEventListener> getPageEventListeners() {
        return pageEventListeners;
    }

    private Set<IPageActionListener> getPageActionListeners() {
        return pageActionListeners;
    }

}
