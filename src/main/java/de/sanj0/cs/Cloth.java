package de.sanj0.cs;

import de.edgelord.saltyengine.transform.Vector2f;
import de.edgelord.saltyengine.utils.SaltySystem;

import java.awt.*;
import java.util.List;

public class Cloth {

    public static final Vector2f HALF = new Vector2f(.5f, .5f);
    private List<ClothPoint> points;
    private List<ClothConstraint> constraints;
    private List<ClothFace> faces;
    private int numIterations = 5;
    private Color color;

    private float gravity = 5f;

    public Cloth(final List<ClothPoint> points, final List<ClothConstraint> contraints,
                 final List<ClothFace> faces, final Color color) {
        this.points = points;
        this.constraints = contraints;
        this.faces = faces;
        this.color = color;
    }

    // inspired by https://www.youtube.com/watch?v=PGk0rnyTa1U
    public void tick() {
        for (final ClothPoint p : points) {
            if (!p.isLocked()) {
                final Vector2f prevPos = new Vector2f(p.getPosition());
                p.getPosition().add(p.getPosition().subtracted(p.getPreviousPosition()));
                p.getPosition().add(new Vector2f(0, gravity / SaltySystem.fixedTickMillis));
                p.setPreviousPosition(prevPos);
            } else {
                //p.setPosition(new Vector2f(p.getPosition().getX(), (float) Math.sin(-System.currentTimeMillis() / 1000d) * 540f + 540));
            }
        }

        for (int i = 0; i < numIterations; i++) {
            for (final ClothConstraint c : constraints) {
                final Vector2f centre = c.getA().getPosition().added(c.getB().getPosition()).multiplied(HALF);
                final Vector2f direction = c.getA().getPosition().subtracted(c.getB().getPosition()).normalize();
                final Vector2f length = new Vector2f(c.getLength(), c.getLength());
                if (!c.getA().isLocked()) {
                    c.getA().setPosition(centre.added(direction.multiplied(length.multiplied(HALF))));
                }
                if (!c.getB().isLocked()) {
                    c.getB().setPosition(centre.subtracted(direction.multiplied(length.multiplied(HALF))));
                }
            }
        }
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
     * Gets {@link #faces}.
     *
     * @return the value of {@link #faces}
     */
    public List<ClothFace> getFaces() {
        return faces;
    }

    /**
     * Sets {@link #faces}.
     *
     * @param faces the new value of {@link #faces}
     */
    public void setFaces(final List<ClothFace> faces) {
        this.faces = faces;
    }

    /**
     * Gets {@link #numIterations}.
     *
     * @return the value of {@link #numIterations}
     */
    public int getNumIterations() {
        return numIterations;
    }

    /**
     * Sets {@link #numIterations}.
     *
     * @param numIterations the new value of {@link #numIterations}
     */
    public void setNumIterations(final int numIterations) {
        this.numIterations = numIterations;
    }

    /**
     * Gets {@link #gravity}.
     *
     * @return the value of {@link #gravity}
     */
    public float getGravity() {
        return gravity;
    }

    /**
     * Sets {@link #gravity}.
     *
     * @param gravity the new value of {@link #gravity}
     */
    public void setGravity(final float gravity) {
        this.gravity = gravity;
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
     * Sets {@link #points}.
     *
     * @param points the new value of {@link #points}
     */
    public void setPoints(final List<ClothPoint> points) {
        this.points = points;
    }

    /**
     * Gets {@link #constraints}.
     *
     * @return the value of {@link #constraints}
     */
    public List<ClothConstraint> getConstraints() {
        return constraints;
    }

    /**
     * Sets {@link #constraints}.
     *
     * @param constraints the new value of {@link #constraints}
     */
    public void setConstraints(final List<ClothConstraint> constraints) {
        this.constraints = constraints;
    }
}
