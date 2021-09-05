package de.sanj0.cs;

import java.awt.*;
import java.util.List;

public class ClothFace {
    private final List<ClothPoint> points;
    private Color color;
    private float initialArea;

    public ClothFace(final List<ClothPoint> points, final Color color) {
        this.points = points;
        this.color = color;
        initialArea = area(points);
    }

    public static float area(final List<ClothPoint> points) {
        float sum = 0;
        for (int i = 0; i < points.size(); i++) {
            final ClothPoint current = points.get(i);
            if (i == 0) {
                sum += current.getPosition().getX() * (points.get(i + 1).getPosition().getY() - points.get(points.size() - 1).getPosition().getY());
            } else if (i == points.size() - 1) {
                sum += current.getPosition().getX() * (points.get(0).getPosition().getY() - points.get(i - 1).getPosition().getY());
            } else {
                sum += current.getPosition().getX() * (points.get(i + 1).getPosition().getY() - points.get(i - 1).getPosition().getY());
            }
        }
        return 0.5f * Math.abs(sum);
    }

    public float areaRatio() {
        return area(points) / initialArea;
    }

    /**
     * Gets {@link #color}.
     *
     * @return the value of {@link #color}
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets {@link #color}.
     *
     * @param color the new value of {@link #color}
     */
    public void setColor(final Color color) {
        this.color = color;
    }

    /**
     * Gets {@link #points}.
     *
     * @return the value of {@link #points}
     */
    public List<ClothPoint> getPoints() {
        return points;
    }

    /**
     * Gets {@link #initialArea}.
     *
     * @return the value of {@link #initialArea}
     */
    public float getInitialArea() {
        return initialArea;
    }

    /**
     * Sets {@link #initialArea}.
     *
     * @param initialArea the new value of {@link #initialArea}
     */
    public void setInitialArea(final float initialArea) {
        this.initialArea = initialArea;
    }
}
