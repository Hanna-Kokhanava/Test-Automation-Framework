package com.linkedin.automation.services.ui.handlers.scroll.constraints;

import com.linkedin.automation.core.device.functions.Direction;
import com.linkedin.automation.services.ui.handlers.scroll.ScrollType;
import com.linkedin.automation.services.ui.handlers.scroll.ScrollVector;
import com.linkedin.automation.services.ui.listeners.IPageEventListener;
import com.linkedin.automation.services.ui.listeners.PageEvent;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class does counting of performed scrolls.
 */
public class ScrollCounter extends Constraint implements IPageEventListener {
    private final ScrollType scrollType;
    private final int scrollLimitNumber;
    private int scrollPerformedNumber;

    public ScrollCounter(ScrollType scrollType, int scrollLimitNumber) {
        this(scrollType, scrollLimitNumber, 0);
    }

    public ScrollCounter(ScrollType scrollType, int scrollLimitNumber, int scrollPerformedNumber) {
        this.scrollType = scrollType;
        this.scrollLimitNumber = scrollLimitNumber;
        this.scrollPerformedNumber = scrollPerformedNumber;
    }

    @Override
    public Boolean apply(ScrollVector scrollVector) {
        return 0 < (getScrollType().getEndingDirection() == getScrollType().getDirectionFor(scrollVector)
                ? getScrollLimitNumber() - getScrollPerformedNumber()
                : getScrollPerformedNumber());
    }

    public ScrollType getScrollType() {
        return scrollType;
    }

    public int getScrollLimitNumber() {
        return scrollLimitNumber;
    }

    public int getScrollPerformedNumber() {
        return scrollPerformedNumber;
    }

    /**
     * Calculates accessible number of scrolls
     *
     * @param direction instance of {@link Direction}
     */
    private void update(@Nonnull Direction direction) {
        if (getScrollType().getEndingDirection() == direction) {
            scrollPerformedNumber++;
        } else {
            scrollPerformedNumber--;
        }
    }

    @Override
    public void firePageEvent(PageEvent event, Object... additionalEventInfo) {
        if (PageEvent.Type.SCROLL_PERFORMED == event.getType()) {
            Object direction = Arrays.stream(additionalEventInfo).
                    filter(info -> info.getClass() == Direction.class).
                    findFirst().
                    orElse(null);
            update(Direction.class.cast(Objects.requireNonNull(direction, "Event is not contains Direction")));
        }
    }
}
