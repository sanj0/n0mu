package de.sanj0.cs;

import de.edgelord.saltyengine.core.graphics.SaltyGraphics;
import de.edgelord.saltyengine.core.interfaces.FixedTickRoutine;
import de.edgelord.saltyengine.gameobject.DrawingRoutine;
import de.edgelord.saltyengine.transform.Vector2f;
import de.edgelord.saltyengine.utils.ColorUtil;
import de.edgelord.saltyengine.utils.GeneralUtil;

import java.awt.*;
import java.util.List;

public class ClothRenderer extends DrawingRoutine implements FixedTickRoutine {

    private final Cloth cloth;
    private boolean facesOnly;
    public ClothRenderer(final Cloth cloth, final boolean facesOnly) {
        super(DrawingPosition.BEFORE_GAMEOBJECTS);
        this.cloth = cloth;
        this.facesOnly = facesOnly;
    }

    @Override
    public void draw(final SaltyGraphics saltyGraphics) {
        if (!facesOnly) {
            saltyGraphics.setStroke(new BasicStroke(10));
            for (final ClothConstraint c : cloth.getConstraints()) {
                saltyGraphics.setColor(ColorUtil.blend(Color.BLACK, cloth.getColor(), GeneralUtil.clamp(c.getLength() / c.getA().getPosition().distance(c.getB().getPosition()), 0, 1)));
                saltyGraphics.drawLine(c.getA().getPosition(), c.getB().getPosition());
            }
            for (final ClothPoint p : cloth.getPoints()) {
                if (p.isLocked())
                    saltyGraphics.setColor(cloth.getColor().darker().darker());
                else
                    saltyGraphics.setColor(cloth.getColor());
                saltyGraphics.drawPoint(p.getPosition(), 25);
            }
        }
        for (final ClothFace face : cloth.getFaces()) {
            saltyGraphics.setColor(ColorUtil.blend(ColorUtil.BLACK, face.getColor(), GeneralUtil.clamp(face.areaRatio(), 0, 1)));
            final List<ClothPoint> ps = face.getPoints();
            final Vector2f[] points = new Vector2f[ps.size()];
            for (int i = 0; i < points.length; i++) {
                points[i] = ps.get(i).getPosition();
            }
            saltyGraphics.drawPolygon(points);
        }
    }

    @Override
    public void onFixedTick() {
        cloth.tick();
    }

    /**
     * Gets {@link #cloth}.
     *
     * @return the value of {@link #cloth}
     */
    public Cloth getCloth() {
        return cloth;
    }

    /**
     * Gets {@link #facesOnly}.
     *
     * @return the value of {@link #facesOnly}
     */
    public boolean isFacesOnly() {
        return facesOnly;
    }

    /**
     * Sets {@link #facesOnly}.
     *
     * @param facesOnly the new value of {@link #facesOnly}
     */
    public void setFacesOnly(final boolean facesOnly) {
        this.facesOnly = facesOnly;
    }
}
