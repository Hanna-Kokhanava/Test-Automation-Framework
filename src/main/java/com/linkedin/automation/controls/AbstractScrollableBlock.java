package com.linkedin.automation.controls;

import com.linkedin.automation.core.device.functions.Direction;
import com.linkedin.automation.page_elements.block.MobileBlock;
import com.linkedin.automation.page_elements.interfaces.Availability;
import com.linkedin.automation.services.ui.handlers.LgControlHandlersFactory;
import com.linkedin.automation.services.ui.handlers.scroll.Point;
import com.linkedin.automation.services.ui.handlers.scroll.ScrollAdapter;
import com.linkedin.automation.services.ui.handlers.scroll.ScrollType;
import com.linkedin.automation.services.ui.handlers.scroll.ScrollVector;
import com.linkedin.automation.services.ui.handlers.scroll.constraints.*;
import com.linkedin.automation.services.ui.listeners.IPageEventListener;
import com.linkedin.automation.services.ui.listeners.PageEvent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Siarhei_Ziablitsau
 */
public abstract class AbstractScrollableBlock extends MobileBlock<ScrollAdapter> {
    private ScrollCounter scrollCounter;
    private SearchType searchType;
    private Set<IPageEventListener> pageEventListeners;
    private final int defMaxScrollNumber = 50;

    public AbstractScrollableBlock(WebElement element) {
        super(element);
        setScrollHandler(
                new ScrollAdapter(LgControlHandlersFactory.createScrollHandler(this))
        );
        setScrollLimit(defMaxScrollNumber);
        setSearchType(SearchType.DISPLAYED);
    }

    protected enum SearchType {
        FULL_DISPLAYED,
        EXIST, DISPLAYED
    }

    protected void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }

    @Nullable
    private <T extends Availability> ElementSearcher getElementSearcher(T targetElement, ScrollType scrollType) {
        switch (searchType) {
            case EXIST:
                return new ElementSearcherExist(targetElement);
            case DISPLAYED:
                return new ElementSearcherDisplayed(targetElement);
            case FULL_DISPLAYED:
                return new ElementSearcherFullDisplayed((WrapsElement) targetElement, this, scrollType);
            default:
                return null;
        }
    }

    /**
     * Performs scroll to the element
     *
     * @param targetElement target element
     */
    public <T extends Availability> T searchElement(T targetElement) {
        ElementSearcher elementSearcher = getElementSearcher(targetElement, getScrollHandler().getScrollType());
        getScrollHandler().addConstraint(elementSearcher);

        scrollToEnd(Objects.requireNonNull(getScrollHandler().getScrollType().getEndingDirection(), "Incorrect direction"));
        //search from current position
        if (!Objects.requireNonNull(elementSearcher).check()) {
            getScrollHandler().removeConstraint(elementSearcher);
            // search from Top position again
            scrollToEnd(getScrollHandler().getScrollType().getBeginningDirection());
            getScrollHandler().addConstraint(elementSearcher);
            scrollToEnd(getScrollHandler().getScrollType().getEndingDirection());
        }
        getScrollHandler().removeConstraint(elementSearcher);
        return targetElement;
    }

    /**
     * Performs scroll in defined {@link Direction} to endpoint with all constraints
     *
     * @param direction instance of {@link Direction}
     */
    public void scrollToEnd(@Nonnull Direction direction) {
        while (getScrollHandler().isNextScrollAvailable(
                Objects.requireNonNull(getScrollHandler().getVector(direction), "Incorrect direction"))) {
            scroll(direction);
        }
    }

    @Override
    public void scroll(Direction direction) {
        if (getScrollHandler().scroll(getScrollHandler().getVector(direction))) {
            notifyListeners(new PageEvent(this, PageEvent.Type.SCROLL_PERFORMED), direction);
        }
    }

    @Override
    public void scroll(double startX, double startY, double endX, double endY, Duration duration) {
        ScrollVector vector = new ScrollVector(new Point(startX, startY), new Point(endX, endY), duration);
        if (getScrollHandler().scroll(vector)) {
            notifyListeners(
                    new PageEvent(this, PageEvent.Type.SCROLL_PERFORMED), getScrollHandler().getScrollType().getDirectionFor(vector)
            );
        }
    }

    /**
     * Sets total number of the scrolls available for this block
     *
     * @param scrollLimitNumber number of the scrolls
     */
    public void setScrollLimit(int scrollLimitNumber) {
        if (Objects.nonNull(scrollCounter)) {
            getScrollHandler().removeConstraint(scrollCounter);
            removePageEventListener(scrollCounter);
        }
        scrollCounter = new ScrollCounter(getScrollHandler().getScrollType(), scrollLimitNumber);
        addPageEventListener(scrollCounter);
        getScrollHandler().addConstraint(scrollCounter);

    }

    /**
     * Returns total number of the scrolls available for this Selector.
     * Default value is 50.
     *
     * @return total number scrolls
     */
    public int getScrollLimit() {
        if (null == scrollCounter) {
            setScrollLimit(defMaxScrollNumber);
        }
        return scrollCounter.getScrollLimitNumber();
    }

    /**
     * Returns {@link Set} of existing instances of {@link IPageEventListener}.
     *
     * @return {@link Set} of listeners
     */
    protected Set<IPageEventListener> getPageEventListeners() {
        return pageEventListeners;
    }

    /**
     * Adds {@link IPageEventListener} instance.
     *
     * @param listener instance of {@link IPageEventListener}
     */
    protected void addPageEventListener(@Nonnull IPageEventListener listener) {
        if (Objects.isNull(getPageEventListeners())) {
            pageEventListeners = new HashSet<>();
        }
        getPageEventListeners().add(listener);
    }

    /**
     * Removes instance of {@link IPageEventListener}
     *
     * @param listener instance of {@link IPageEventListener}
     */
    protected void removePageEventListener(@Nonnull IPageEventListener listener) {
        if (Objects.nonNull(getPageEventListeners())) {
            getPageEventListeners().remove(listener);
        }
    }

    /**
     * Invokes notification of all listeners about happened page event
     *
     * @param eventType      instance of happened {@link PageEvent}
     * @param contentBlock   object that generated this {@link PageEvent}
     * @param additionalInfo additional information about this {@link PageEvent}
     */
    private void notifyListeners(PageEvent eventType, Object contentBlock, Object... additionalInfo) {
        if (null == eventType || null == pageEventListeners) {
            return;
        }

        for (IPageEventListener listener : pageEventListeners) {
            listener.firePageEvent(eventType, contentBlock, additionalInfo);
        }
    }

}
