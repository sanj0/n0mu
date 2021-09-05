package de.sanj0.cs;

import de.edgelord.saltyengine.core.Game;
import de.edgelord.saltyengine.core.GameConfig;
import de.edgelord.saltyengine.scene.Scene;
import de.edgelord.saltyengine.transform.Vector2f;
import de.edgelord.saltyengine.utils.ColorUtil;

public class Main extends Game {
    public static void main(String[] args) {
        init(GameConfig.config(1920, 1080, "Cloth Simulator", 10));
        start(30, new Scene() {
            @Override
            public void initialize() {
                addGameObject(new ClothCreator(ClothGenerator.generateGermanFlag(
                 new Vector2f(100, 50), new Vector2f(20, 20), new Vector2f(60, 44), true, ColorUtil.DODGER_BLUE)));
                //addGameObject(new ClothCreator(ClothGenerator.generateRope(new Vector2f(0, 1080f / 2f), 20, 96, ColorUtil.DEEP_PINK, 0, 95)));
            }
        });
    }
}
