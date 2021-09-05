package de.sanj0.cs;

import java.util.Objects;

public class ClothConstraint {
    private ClothPoint a;
    private ClothPoint b;
    private float length;

    public ClothConstraint(final ClothPoint a, final ClothPoint b, final float length) {
        this.a = a;
        this.b = b;
        this.length = length;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClothConstraint that = (ClothConstraint) o;
        return Objects.equals(a, that.a) && Objects.equals(b, that.b)
                || Objects.equals(a, that.b) && Objects.equals(b, that.a);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    /**
     * Gets {@link #a}.
     *
     * @return the value of {@link #a}
     */
    public ClothPoint getA() {
        return a;
    }

    /**
     * Sets {@link #a}.
     *
     * @param a the new value of {@link #a}
     */
    public void setA(final ClothPoint a) {
        this.a = a;
    }

    /**
     * Gets {@link #b}.
     *
     * @return the value of {@link #b}
     */
    public ClothPoint getB() {
        return b;
    }

    /**
     * Sets {@link #b}.
     *
     * @param b the new value of {@link #b}
     */
    public void setB(final ClothPoint b) {
        this.b = b;
    }

    /**
     * Gets {@link #length}.
     *
     * @return the value of {@link #length}
     */
    public float getLength() {
        return length;
    }

    /**
     * Sets {@link #length}.
     *
     * @param length the new value of {@link #length}
     */
    public void setLength(final float length) {
        this.length = length;
    }
}
