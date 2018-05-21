package com.linkedin.automation.services.ui.handlers.scroll;

import com.linkedin.automation.core.device.functions.Direction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author Siarhei_Ziablitsau
 */
public enum ScrollType {
    HORIZONTAL(Direction.LEFT, Direction.RIGHT),
    VERTICAL(Direction.UP, Direction.DOWN);

    private Direction toBeginning;
    private Direction toEnding;

    ScrollType(Direction toBeginning, Direction toEnding) {
        this.toBeginning = toBeginning;
        this.toEnding = toEnding;
    }

    /**
     * Returns instance of {@link Direction} that corresponded scrolling to beginning.
     *
     * @return instance of {@link Direction}
     */
    @Nonnull
    public Direction getBeginningDirection() {
        return toBeginning;
    }

    /**
     * Returns instance of {@link Direction} that corresponded scrolling to ending.
     *
     * @return instance of {@link Direction}
     */
    @Nonnull
    public Direction getEndingDirection() {
        return toEnding;
    }

    /**
     * Checks that definaed {@link Direction} corresponded current type of scrolling
     *
     * @param direction instance of {@link Direction}
     * @return is corresponded or not
     */
    public boolean isAvailable(@Nullable Direction direction) {
        return !Objects.isNull(direction) && (getBeginningDirection() == direction || getEndingDirection() == direction);
    }

    /**
     * Checks that definaed {@link ScrollVector} corresponded current type of scrolling
     *
     * @param scrollVector instance of {@link ScrollVector}
     * @return is corresponded or not
     */
    public boolean isAvailable(@Nullable ScrollVector scrollVector) {
        return isAvailable(getDirectionFor(scrollVector));
    }

    /**
     * Returns opposite {@link Direction} for defined {@link Direction}.
     *
     * @param direction instance of {@link Direction}
     * @return opposite {@link Direction} or null in case that defined {@link Direction} is not corresponded current types of scrolling.
     */
    @Nullable
    public static Direction getOppositeDirection(@Nullable Direction direction) {
        ScrollType targetType = Arrays.stream(ScrollType.values()).
                filter(type -> type.isAvailable(direction)).
                findFirst().
                orElse(null);
        return Objects.nonNull(targetType)
                ? targetType.getBeginningDirection() == direction ? targetType.getEndingDirection() : targetType.getBeginningDirection()
                : null;
    }

    /**
     * Returns {@link Direction} for defined {@link ScrollVector} in context of this {@link ScrollType} instance.
     * In case then vector of scroll is not correspond this {@link ScrollType} returns null.
     *
     * @param scrollVector instance of {@link ScrollVector}
     * @return instance of {@link Direction} or null
     */
    @Nullable
    public Direction getDirectionFor(@Nullable ScrollVector scrollVector) {
        if (Objects.isNull(scrollVector)) {
            return null;
        }
        if (this == VERTICAL) {
            double deltaY = scrollVector.getStartPoint().getY() - scrollVector.getEndPoint().getY();
            return 0d == deltaY ? null : 0 > deltaY ? Direction.UP : Direction.DOWN;
        } else {
            double deltaX = scrollVector.getStartPoint().getX() - scrollVector.getEndPoint().getX();
            return 0d == deltaX ? null : 0 > deltaX ? Direction.LEFT : Direction.RIGHT;
        }
    }

}
