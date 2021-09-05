package de.sanj0.cs;

import de.edgelord.saltyengine.transform.Vector2f;
import de.edgelord.saltyengine.utils.ColorUtil;

import java.awt.*;
import java.util.*;

public class ClothGenerator {

    /**
     * Generates a rectangular sheet of cloth.
     *
     * @param origin the origin of the cloth (upper left corner)
     * @param d the distance between points
     * @param n the number of points
     * @param faces generate faces?
     * @return a rectangular sheet of cloth
     */
    public static Cloth generateRectangle(final Vector2f origin, final Vector2f d,
                                          final Vector2f n, final boolean faces, final Color color) {
        final Cloth cloth = new Cloth(new ArrayList<>(Math.round(n.getX() * n.getY())),
                new ArrayList<>(), new ArrayList<>(), color);

        for (int y = 0; y < n.getY(); y++) {
            for (int x = 0; x < n.getX(); x++) {
                cloth.getPoints().add(new ClothPoint(origin.added(d.multiplied(new Vector2f(x, y))), false));
            }
        }

        // not really efficient but way easier
        for (int i = 0; i < cloth.getPoints().size(); i++) {
            final ClothPoint p = cloth.getPoints().get(i);
            ClothPoint b = null;
            ClothPoint c = null;
            if (i % n.getX() < n.getX() - 1) {
                b = cloth.getPoints().get(i + 1);
                cloth.getConstraints().add(new ClothConstraint(p, b, p.getPosition().distance(b.getPosition())));
            }

            if (i < cloth.getPoints().size() - n.getX()) {
                c = cloth.getPoints().get(i + (int) n.getX());
                cloth.getConstraints().add(new ClothConstraint(p, c, p.getPosition().distance(c.getPosition())));
            }

            if (faces) {
                if (b != null && c != null) {
                    final ClothPoint e = cloth.getPoints().get(i + (int) n.getX() + 1);
                    cloth.getFaces().add(new ClothFace(Arrays.asList(p, b, e, c), color));
                }
            }
        }

        return cloth;
    }

    /**
     * Generates a rectangular sheet of cloth.
     *
     * @param origin the origin of the cloth (upper left corner)
     * @param d the distance between points
     * @param n the number of points
     * @param faces generate faces?
     * @return a rectangular sheet of cloth
     */
    public static Cloth generateGermanFlag(final Vector2f origin, final Vector2f d,
                                          final Vector2f n, final boolean faces, final Color color) {
        final Cloth cloth = new Cloth(new ArrayList<>(Math.round(n.getX() * n.getY())),
                new ArrayList<>(), new ArrayList<>(), color);
        final Map<ClothPoint, Color> colorsOrGermany = new HashMap<>();

        Color col;
        for (int y = 0; y < n.getY(); y++) {
            if (y <= n.getY() / 3 - 1) {
                col = Color.BLACK;
            } else if (y <= n.getY() * (2f / 3f) - 1) {
                col = ColorUtil.CRIMSON_RED;
            } else {
                col = ColorUtil.GOLD;
            }
            for (int x = 0; x < n.getX(); x++) {
                final ClothPoint p = new ClothPoint(origin.added(d.multiplied(new Vector2f(x, y))), false);
                cloth.getPoints().add(p);
                colorsOrGermany.put(p, col);
            }
        }

        // not really efficient but way easier
        for (int i = 0; i < cloth.getPoints().size(); i++) {
            final ClothPoint p = cloth.getPoints().get(i);
            ClothPoint b = null;
            ClothPoint c = null;
            if (i % n.getX() < n.getX() - 1) {
                b = cloth.getPoints().get(i + 1);
                cloth.getConstraints().add(new ClothConstraint(p, b, p.getPosition().distance(b.getPosition())));
            }

            if (i < cloth.getPoints().size() - n.getX()) {
                c = cloth.getPoints().get(i + (int) n.getX());
                cloth.getConstraints().add(new ClothConstraint(p, c, p.getPosition().distance(c.getPosition())));
            }

            if (faces) {
                if (b != null && c != null) {
                    final ClothPoint e = cloth.getPoints().get(i + (int) n.getX() + 1);
                    cloth.getFaces().add(new ClothFace(Arrays.asList(p, b, e, c), colorsOrGermany.get(p)));
                }
            }
        }

        return cloth;
    }

    public static Cloth generateRope(final Vector2f origin, final float d, final int n, final Color color, final int... locked) {
        final Cloth cloth = new Cloth(new ArrayList<>(n),
                new ArrayList<>(), new ArrayList<>(), color);

        final Set<Integer> lockedPoints = new HashSet<>(locked.length);
        for (int i = 0; i < locked.length; i++) {
            lockedPoints.add(locked[i]);
        }

        ClothPoint prev = null;
        for (int i = 0; i < n; i++) {
            final ClothPoint p = new ClothPoint(origin.added(new Vector2f(i * d, 0)), lockedPoints.contains(i));
            cloth.getPoints().add(p);
            if (prev != null) {
                cloth.getConstraints().add(new ClothConstraint(prev, p, prev.getPosition().distance(p.getPosition())));
            }
            prev = p;
        }

        return cloth;
    }
}
