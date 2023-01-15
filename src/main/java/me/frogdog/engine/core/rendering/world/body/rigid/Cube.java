package me.frogdog.engine.core.rendering.world.body.rigid;

import me.frogdog.engine.core.rendering.world.Model;
import me.frogdog.engine.core.rendering.world.body.PhysicsBody;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.utils.loader.ObjectLoader;
import org.joml.Vector3f;

public class Cube extends PhysicsBody {

    private ObjectLoader loader;
    private Model model;

    public Cube(Model model, Vector3f pos, Vector3f rotation, Vector3f size, float scale, float mass) {
        super(model, pos, rotation, size, scale, mass);
    }
}
