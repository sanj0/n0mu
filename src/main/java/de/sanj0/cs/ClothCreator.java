package de.sanj0.cs;

import de.edgelord.saltyengine.core.SceneManager;
import de.edgelord.saltyengine.core.event.CollisionEvent;
import de.edgelord.saltyengine.core.graphics.SaltyGraphics;
import de.edgelord.saltyengine.gameobject.FixedTask;
import de.edgelord.saltyengine.gameobject.GameObject;
import de.edgelord.saltyengine.input.Input;
import de.edgelord.saltyengine.input.KeyboardInputAdapter;
import de.edgelord.saltyengine.input.MouseInputAdapter;
import de.edgelord.saltyengine.transform.Transform;
import de.edgelord.saltyengine.transform.Vector2f;
import de.edgelord.saltyengine.utils.ColorUtil;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ClothCreator extends GameObject {

    private Cloth cloth;
    private ClothRenderer clothRenderer;

    private Transform startBtn = new Transform(15, 15, 100, 25);
    private ClothPoint dragStart = null;
    private final List<ClothPoint> allDraggedOver = new ArrayList<>();

    public ClothCreator(final Cloth c) {
        super(0, 0, 0, 0, "cloth-creator");

        if (c != null) {
            this.cloth = c;
        } else {
            cloth = new Cloth(new ArrayList<>(10), new ArrayList<>(9), new ArrayList<>(5), ColorUtil.GOLD);
        }

        clothRenderer = new ClothRenderer(cloth, false);
        Input.addMouseInputHandler(new MouseInputAdapter() {
            @Override
            public void mouseDragged(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    for (final ClothPoint p : cloth.getPoints()) {
                        if (Input.getCursorPosition().distance(p.getPosition()) <= 25) {
                            if (!allDraggedOver.contains(p)) {
                                allDraggedOver.add(p);
                            }
                            return;
                        }
                    }
                }
            }

            @Override
            public void mousePressed(final MouseEvent e) {
                // constraint drag
                // only on btn 1
                if (e.getButton() == MouseEvent.BUTTON1) {
                    for (final ClothPoint p : cloth.getPoints()) {
                        if (Input.getCursorPosition().distance(p.getPosition()) <= 25) {
                            dragStart = p;
                            return;
                        }
                    }
                }

                // start btn
                if (startBtn.contains(Input.getCursor())) {
                    removeFromCurrentScene();
                    clothRenderer.setFacesOnly(true);
                    SceneManager.getCurrentScene().addDrawingRoutine(clothRenderer);
                    SceneManager.getCurrentScene().addFixedTask(new FixedTask() {
                        @Override
                        public void onFixedTick() {
                            clothRenderer.onFixedTick();
                            //cloth.getPoints().get(0).getPosition().setY((float) Math.sin(System.currentTimeMillis() / 100d) * 540f + 540);
                            //cloth.getPoints().get(cloth.getPoints().size() - 2).getPosition().setY((float) Math.sin(-System.currentTimeMillis() / 100d) * 540f + 540);
                        }
                    });
                }

                // lock and remove
                // both with right btn
                if (e.getButton() == MouseEvent.BUTTON3) {
                    for (final ClothPoint p : cloth.getPoints()) {
                        if (Input.getCursorPosition().distance(p.getPosition()) <= 25) {
                            if (e.getClickCount() == 1) {
                                p.setLocked(!p.isLocked());
                            } else if (e.getClickCount() == 2) {
                                cloth.getPoints().remove(p);
                                cloth.getConstraints().removeIf(c -> c.getA() == p || c.getB() == p);
                                cloth.getFaces().removeIf(f -> f.getPoints().contains(p));
                            }
                            return;
                        }
                    }
                }
                dragStart = null;
                allDraggedOver.clear();
                cloth.getPoints().add(new ClothPoint(Input.getCursorPosition(), false));
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
                if (allDraggedOver.size() >= 3) {
                    cloth.getFaces().add(new ClothFace(allDraggedOver, cloth.getColor()));
                } else if (dragStart != null) {
                    for (final ClothPoint p : cloth.getPoints()) {
                        if (Input.getCursorPosition().distance(p.getPosition()) <= 25) {
                            if (p != dragStart) {
                                // in order to see if we already created, we will just do remove if,
                                // because we need to iterate either way
                                final ClothConstraint clothConstraint = new ClothConstraint(dragStart, p, dragStart.getPosition().distance(p.getPosition()));
                                if (!cloth.getConstraints().removeIf(c -> c.equals(clothConstraint))) {
                                    cloth.getConstraints().add(clothConstraint);
                                }
                                dragStart = null;
                                return;
                            }
                        }
                    }
                }
                allDraggedOver.clear();
            }

            @Override
            public void mouseClicked(final MouseEvent e) {

            }
        });

        Input.addKeyboardInputHandler(new KeyboardInputAdapter() {
            final float d = 50;
            @Override
            public void keyPressed(final KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT:
                        for (final ClothPoint p : cloth.getPoints()) {
                            if (p.isLocked()) {
                                p.getPosition().add(new Vector2f(d, 0));
                            }
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        for (final ClothPoint p : cloth.getPoints()) {
                            if (p.isLocked()) {
                                p.getPosition().add(new Vector2f(-d, 0));
                            }
                        }
                        break;
                    case KeyEvent.VK_UP:
                        for (final ClothPoint p : cloth.getPoints()) {
                            if (p.isLocked()) {
                                p.getPosition().add(new Vector2f(0, -d));
                            }
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        for (final ClothPoint p : cloth.getPoints()) {
                            if (p.isLocked()) {
                                p.getPosition().add(new Vector2f(0, d));
                            }
                        }
                        break;
                    case KeyEvent.VK_J:
                        cloth.getPoints().get(0).setPosition(Input.getCursorPosition());
                        break;
                }
            }
        });
    }

    @Override
    public void initialize() {

    }

    @Override
    public void onCollision(final CollisionEvent event) {

    }

    @Override
    public void onFixedTick() {
    }

    @Override
    public void draw(final SaltyGraphics saltyGraphics) {
        if (allDraggedOver.size() >= 3) {
            saltyGraphics.setColor(ColorUtil.GOLD.darker().darker());
            final Vector2f[] points = new Vector2f[allDraggedOver.size()];
            for (int i = 0; i < points.length; i++) {
                points[i] = allDraggedOver.get(i).getPosition();
            }
            saltyGraphics.drawPolygon(points);
        } else if (dragStart != null) {
            saltyGraphics.setColor(ColorUtil.GOLD.darker().darker());
            saltyGraphics.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
            saltyGraphics.drawLine(dragStart.getPosition(), Input.getCursorPosition());
        }
        clothRenderer.draw(saltyGraphics);
        saltyGraphics.setColor(ColorUtil.GREEN);
        saltyGraphics.drawRoundRect(startBtn, 5);
        saltyGraphics.setColor(Color.BLACK);
        saltyGraphics.drawText("start", startBtn.getCentre(), SaltyGraphics.TextAnchor.CENTRE);
    }
}
