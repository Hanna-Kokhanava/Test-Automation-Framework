package com.linkedin.automation.core.device.functions;

/**
 * Created on 11.04.2018
 */
public enum Direction {
    UP(0.48, 0.3, 0.52, 0.7, "Up"),
    DOWN(0.48, 0.7, 0.52, 0.3, "Down"),
    LEFT(0.3, 0.48, 0.7, 0.52, "Left"),
    RIGHT(0.7, 0.48, 0.3, 0.52, "Right");

    private double startX, startY, endX, endY;
    private String direction;

    Direction(double startX, double startY, double endX, double endY, String direction) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.direction = direction;
    }

    public String getDir() {
        return direction;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }
}
