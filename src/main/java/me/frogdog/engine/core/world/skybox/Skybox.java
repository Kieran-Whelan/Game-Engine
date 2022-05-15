package me.frogdog.engine.core.world.skybox;

import me.frogdog.engine.core.world.Model;
import me.frogdog.engine.utils.ObjectLoader;

public class Skybox {

    private static final float SIZE = 500f;
    private static final float[] VERTICES = {
            -SIZE,  SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            -SIZE,  SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE,  SIZE
    };

    private final String[] textureFiles = new String[] {"textures/skybox/day/right.png", "textures/skybox/day/left.png", "textures/skybox/day/top.png", "textures/skybox/day/bottom.png", "textures/skybox/day/back.png", "textures/skybox/day/front.png"};
    private final String[] nightTextureFiles = new String[] {"textures/skybox/night/nightRight.png", "textures/skybox/night/nightLeft.png", "textures/skybox/night/nightTop.png", "textures/skybox/night/nightBottom.png", "textures/skybox/night/nightBack.png", "textures/skybox/night/nightFront.png"};

    private ObjectLoader loader;
    private Model model;
    private float rotationSpeed = 0.5f;
    private int dayTextureMap;
    private int nightTextureMap;

    public Skybox() throws Exception {
        loader = new ObjectLoader();
        this.model = loader.loadModel(VERTICES, 3);
        this.dayTextureMap = loader.loadCubeMap(textureFiles);
        this.nightTextureMap = loader.loadCubeMap(nightTextureFiles);
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public float[] getVertices() {
        return VERTICES;
    }

    public int getDayTextureMap() {
        return dayTextureMap;
    }

    public int getNightTextureMap() {
        return nightTextureMap;
    }

    public Model getModel() {
        return model;
    }
}
