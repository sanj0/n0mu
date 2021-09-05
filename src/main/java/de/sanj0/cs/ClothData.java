package de.sanj0.cs;

import de.edgelord.saltyengine.transform.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class ClothData {
    public static final ClothPoint[] POINTS = {
            new ClothPoint(new Vector2f(0, 1080 / 2f), true),
            new ClothPoint(new Vector2f(640, 1080 / 2f), false),
            new ClothPoint(new Vector2f(1280, 1080 / 2f), false),
            new ClothPoint(new Vector2f(1920, 1080/2f), true)
    };

    public static List<ClothConstraint> daisyChainConstraints(final List<ClothPoint> points,
                                                              UnaryOperator<Float> length) {
        final List<ClothConstraint> clothConstraints = new ArrayList<>(points.size() - 1);

        ClothPoint prev = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            final ClothPoint p = points.get(i);
            clothConstraints.add(new ClothConstraint(prev, p, length.apply(prev.getPosition().distance(p.getPosition()))));
            prev = p;
        }

        return clothConstraints;
    }
}
