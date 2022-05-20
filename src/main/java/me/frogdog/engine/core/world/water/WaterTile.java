package me.frogdog.engine.core.world.water;

import me.frogdog.engine.core.world.Model;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.utils.ObjectLoader;
import org.joml.Vector3f;

public class WaterTile {

    private ObjectLoader loader;
    private Model model;
    private Vector3f position;

    public WaterTile(Vector3f position) {
        loader = new ObjectLoader();
        this.model = loader.loadModel(Consts.WATER_VERTICES, 2);
        this.position = position;
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }
}
