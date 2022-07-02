package me.frogdog.engine.core.rendering.world.skybox;

import me.frogdog.engine.core.rendering.world.Model;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.utils.loader.ObjectLoader;

public class Skybox {

    private ObjectLoader loader;
    private Model model;
    private float rotationSpeed = 0.5f;
    private int dayTextureMap;
    private int nightTextureMap;

    public Skybox(String[] textureFiles, String[] nightTextureFiles) throws Exception {
        loader = new ObjectLoader();
        this.model = loader.loadModel(Consts.VERTICES, 3);
        this.dayTextureMap = loader.loadCubeMap(textureFiles);
        this.nightTextureMap = loader.loadCubeMap(nightTextureFiles);
    }

    public Skybox(String[] textureFiles, String[] nightTextureFiles, float rotationSpeed) throws Exception {
        loader = new ObjectLoader();
        this.model = loader.loadModel(Consts.VERTICES, 3);
        this.dayTextureMap = loader.loadCubeMap(textureFiles);
        this.nightTextureMap = loader.loadCubeMap(nightTextureFiles);
        this.rotationSpeed = rotationSpeed;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
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
