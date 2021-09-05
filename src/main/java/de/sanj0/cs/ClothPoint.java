package de.sanj0.cs;

import de.edgelord.saltyengine.transform.Vector2f;

import java.util.Objects;

public class ClothPoint {
    private Vector2f position;
    private Vector2f previousPosition;
    private boolean locked;

    public ClothPoint(final Vector2f position, final boolean locked) {
        this.position = position;
        this.previousPosition = new Vector2f(position);
        this.locked = locked;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClothPoint that = (ClothPoint) o;
        return locked == that.locked && Objects.equals(position, that.position) && Objects.equals(previousPosition, that.previousPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, previousPosition, locked);
    }

    /**
     * Gets {@link #position}.
     *
     * @return the value of {@link #position}
     */
    public Vector2f getPosition() {
        return position;
    }

    /**
     * Sets {@link #position}.
     *
     * @param position the new value of {@link #position}
     */
    public void setPosition(final Vector2f position) {
        this.position = position;
    }

    /**
     * Gets {@link #previousPosition}.
     *
     * @return the value of {@link #previousPosition}
     */
    public Vector2f getPreviousPosition() {
        return previousPosition;
    }

    /**
     * Sets {@link #previousPosition}.
     *
     * @param previousPosition the new value of {@link #previousPosition}
     */
    public void setPreviousPosition(final Vector2f previousPosition) {
        this.previousPosition = previousPosition;
    }

    /**
     * Gets {@link #locked}.
     *
     * @return the value of {@link #locked}
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets {@link #locked}.
     *
     * @param locked the new value of {@link #locked}
     */
    public void setLocked(final boolean locked) {
        this.locked = locked;
    }
}
