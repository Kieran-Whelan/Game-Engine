package me.frogdog.engine.core.rendering.world.water;

import me.frogdog.engine.core.rendering.world.Model;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.utils.loader.ObjectLoader;
import org.joml.Vector3f;

public class WaterTile {

    private ObjectLoader loader;
    private Model model;
    private Vector3f position;
    private float scale;

    public WaterTile(Vector3f position, float scale) {
        loader = new ObjectLoader();
        this.model = loader.loadModel(Consts.WATER_VERTICES, 2);
        this.position = position;
        this.scale = scale;
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getScale() {
        return scale;
    }
}
