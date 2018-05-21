package com.linkedin.automation.services.ui.handlers.scroll;

/**
 * Class represents some point with relative value of coordinate
 */
public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!Point.class.isInstance(obj)) {
            return false;
        }

        Point otherPoint = Point.class.cast(obj);
        return getX() == otherPoint.getX() && getY() == otherPoint.getY();
    }
}
