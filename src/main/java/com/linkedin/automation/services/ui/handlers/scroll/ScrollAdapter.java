package com.linkedin.automation.services.ui.handlers.scroll;

import com.linkedin.automation.core.device.functions.Direction;
import com.linkedin.automation.page_elements.interfaces.Scrollable;
import com.linkedin.automation.services.ui.handlers.scroll.constraints.Constraint;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Siarhei_Ziablitsau
 */
public class ScrollAdapter implements Scrollable {
    private Scrollable scrollableHandler;

    private ScrollType scrollType;
    private ScrollVector toBeginningVector;
    private ScrollVector toEndingVector;

    private List<Constraint> constraintList = new ArrayList<>();

    public ScrollAdapter(@Nonnull Scrollable scrollableHandler) {
        setScrollable(scrollableHandler);
        setScrollType(ScrollType.VERTICAL);
    }

    public ScrollAdapter(@Nonnull Scrollable scrollableHandler, @Nonnull ScrollType scrollType) {
        setScrollable(scrollableHandler);
        setScrollType(scrollType);
    }

    /**
     * Adds some constraint for scrolling.
     *
     * @param constraint the {@link Constraint} checks the condition for the permissibility of further scrolling
     * @return constraint was added success
     */
    public boolean addConstraint(Constraint constraint) {
        return getConstraintList().add(constraint);
    }

    /**
     * Removes some constraint for scrolling.
     *
     * @param constraint instance of {@link Constraint}
     * @return constraint was removed success
     */
    public boolean removeConstraint(Constraint constraint) {
        return getConstraintList().remove(constraint);
    }

    public List<Constraint> getConstraintList() {
        return constraintList;
    }

    public void setScrollable(@Nonnull Scrollable scrollableHandler) {
        this.scrollableHandler = scrollableHandler;
    }

    /**
     * Returns defined {@link ScrollType}
     *
     * @return instance of {@link ScrollType}
     */
    public ScrollType getScrollType() {
        return scrollType;
    }

    /**
     * Sets defined {@link ScrollType} for this block.
     * It define general rules for scrolling.
     *
     * @param scrollType instance of {@link ScrollType}
     */
    public void setScrollType(@Nonnull ScrollType scrollType) {
        this.scrollType = scrollType;
        setVector(new ScrollVector(scrollType.getBeginningDirection()));
        setVector(new ScrollVector(scrollType.getEndingDirection()));
    }

    /**
     * Sets instance of {@link ScrollVector} for this block.
     * Throws {@link NullPointerException} in case defined {@link ScrollVector} is not corresponded to defined {@link ScrollType}
     *
     * @param scrollVector instance of {@link ScrollVector}
     */
    public void setVector(@Nonnull ScrollVector scrollVector) {
        Direction generalDirection = Objects.requireNonNull(
                getScrollType().getDirectionFor(scrollVector), "ScrollVector is not corresponded scrolling type"
        );
        if (generalDirection == getScrollType().getBeginningDirection()) {
            this.toBeginningVector = scrollVector;
        } else {
            this.toEndingVector = scrollVector;
        }
    }

    /**
     * Returns defined {@link ScrollVector} for defined {@link Direction}
     * In case  defined {@link Direction} is not corresponded to defined {@link ScrollType} of this block returns null
     *
     * @param direction instance of {@link Direction}
     * @return instance of {@link ScrollVector} or null.
     */
    @Nullable
    public ScrollVector getVector(@Nonnull Direction direction) {
        if (!getScrollType().isAvailable(direction)) {
            return null;
        }
        if (null != toBeginningVector && getScrollType().getDirectionFor(toBeginningVector) == direction) {
            return toBeginningVector;
        }
        if (null != toEndingVector && getScrollType().getDirectionFor(toEndingVector) == direction) {
            return toEndingVector;
        }
        return null;
    }

    /**
     * Performs once of scroll in the defined direction.
     *
     * @param scrollVector direction of the scroll
     * @return is scroll was performed or not
     */
    public boolean scroll(@Nullable ScrollVector scrollVector) {
        if (Objects.isNull(scrollVector) || !getScrollType().isAvailable(scrollVector)) {
            return false;
        }

        scrollableHandler.scroll(
                scrollVector.getStartPoint().getX(), scrollVector.getStartPoint().getY(),
                scrollVector.getEndPoint().getX(), scrollVector.getEndPoint().getY(),
                scrollVector.getDuration()
        );
        return true;
    }

    @Override
    public void scroll(Direction direction) {
        scroll(
                getVector(direction)
        );
    }

    @Override
    public void scroll(double startX, double startY, double endX, double endY, Duration duration) {
        scroll(
                new ScrollVector(new Point(startX, startY), new Point(endX, endY), duration)
        );
    }

    /**
     * Checks that scroll in defined direction is available
     *
     * @param scrollVector scroll vector
     * @return returns scrolls availability
     */
    public boolean isNextScrollAvailable(@Nonnull ScrollVector scrollVector) {
        return getConstraintList().stream().allMatch(function -> function.apply(scrollVector));// check constraints
    }

    /**
     * Checks that scroll in defined direction is available
     *
     * @param direction scroll general direction
     * @return returns scrolls availability
     */
    public boolean isNextScrollAvailable(@Nonnull Direction direction) {
        ScrollVector vector = getVector(direction);
        return !Objects.isNull(vector) && isNextScrollAvailable(vector);
    }

}
