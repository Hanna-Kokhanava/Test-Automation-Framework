package com.linkedin.automation.services.ui.handlers.scroll;


import com.linkedin.automation.core.device.functions.Direction;

import javax.annotation.Nonnull;
import java.time.Duration;

/**
 * Class specifies custom direction for scrolling
 */
public class ScrollVector {
    private Duration duration;
    private Point from;
    private Point to;

    /**
     * Set default direction defined for {@link Direction} instance
     *
     * @param direction instance of {@link Direction}
     */
    public ScrollVector(@Nonnull Direction direction) {
        this(
                direction.getStartX(),
                direction.getStartY(),
                direction.getEndX(),
                direction.getEndY()
        );
    }

    /**
     * Specifies custom of scrolling vector.
     *
     * @param startX is relative value of X coordinate for start of scrolling
     * @param startY is relative value of Y coordinate for start of scrolling
     * @param endX   is relative value of X coordinate for end of scrolling
     * @param endY   is relative value of Y coordinate for end of scrolling
     */
    public ScrollVector(double startX, double startY, double endX, double endY) {
        this(new Point(startX, startY), new Point(endX, endY), Duration.ofSeconds(1));
    }

    /**
     * Specifies custom of scrolling vector.
     *
     * @param from starting point for scrolling
     * @param to   ending point for scrolling
     */
    public ScrollVector(@Nonnull Point from, @Nonnull Point to) {
        this(from, to, Duration.ofSeconds(1));
    }

    /**
     * Specifies custom of scrolling vector.
     *
     * @param from starting point for scrolling
     * @param to   ending point for scrolling
     */
    public ScrollVector(@Nonnull Point from, @Nonnull Point to, @Nonnull Duration duration) {
        setDuration(duration);
        setVector(from, to);
    }

    /**
     * Specifies custom of scrolling vector
     *
     * @param fromPoint is point for start of scrolling
     * @param toPoint   is point for end of scrolling
     */
    private void setVector(Point fromPoint, Point toPoint) {
        if (fromPoint.equals(toPoint)) {
            throw new RuntimeException("Custom scroll vector is incorrect");
        }

        this.from = fromPoint;
        this.to = toPoint;
    }

    /**
     * Returns starting point for scrolling
     *
     * @return instance of {@link Point}
     */
    public Point getStartPoint() {
        return from;
    }

    /**
     * Returns ending point for scrolling
     *
     * @return instance of {@link Point}
     */
    public Point getEndPoint() {
        return to;
    }

    /**
     * Returns time of executing scroll action.
     * Default value is 1 sec.
     *
     * @return instance of {@link Duration}
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets custom time of executing scroll action.
     *
     * @param duration instance of {@link Duration}
     */
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    /**
     * Returns opposite vector for this
     *
     * @return opposite instance of this vector
     */
    public ScrollVector createOppositeVector() {
//        Direction oppositeDirection = Objects.requireNonNull(ScrollType.getOppositeDirection(getDirection()), "Unexpected direction was set");
//        Point fromPoint;
//        Point toPoint;
//
//        switch (oppositeDirection){
//            case UP:
//            case DOWN:
//                fromPoint = new Point(getStartPoint().getX(), getEndPoint().getY());
//                toPoint = new Point(getEndPoint().getX(), getStartPoint().getY());
//                break;
//            case LEFT:
//            case RIGHT:
//                fromPoint = new Point(getEndPoint().getX(), getStartPoint().getY());
//                toPoint = new Point(getStartPoint().getX(), getEndPoint().getY());
//                break;
//            default:
//                fromPoint = null;
//                toPoint = null;
//        }

//        return new ScrollVector(
//                oppositeDirection,
//                Objects.requireNonNull(fromPoint, "Start point for opposite vector was not defined"),
//                Objects.requireNonNull(toPoint, "End point for opposite vector was not defined")
//        );
        return new ScrollVector(getEndPoint(), getStartPoint(), getDuration());
    }

//    /**
//     * Checks that custom vector of scrolling is not in conflict with general {@link Direction}
//     * @return boolean is custom vector correct
//     */
//    private boolean isVectorCorrect(Point fromPoint, Point toPoint){
//        if (2!=fromPoint.getX()+toPoint.getX()+fromPoint.getY()+toPoint.getY()){
//            return false;
//        }
//
//        return !(fromPoint.getX()==toPoint.getX() && fromPoint.getY()==toPoint.getY());
////        switch (getDirection()){
////            case UP:
////                return fromPoint.getY() < toPoint.getY();
////            case DOWN:
////                return fromPoint.getY()>toPoint.getY();
////            case LEFT:
////                return fromPoint.getX() < toPoint.getX();
////            case RIGHT:
////                return fromPoint.getX() > toPoint.getX();
////            default:
////                    return Objects.requireNonNull(null, "Unexpected direction was set");
////        }
//    }

}
