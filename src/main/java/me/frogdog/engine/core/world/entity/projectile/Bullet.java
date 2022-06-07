package me.frogdog.engine.core.world.entity.projectile;

import me.frogdog.engine.core.EngineManager;
import me.frogdog.engine.core.world.Model;
import me.frogdog.engine.core.world.entity.Entity;
import org.joml.Vector3f;

public class Bullet extends Entity {

    private static final float SPEED = 30f;

    public Bullet(Model model, Vector3f pos, Vector3f rotation, float scale) {
        super(model, pos, rotation, scale);
    }

    public void update() {
        float distance = SPEED * EngineManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotation().y)));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotation().y)));
        super.incPos(dx, 0, dz);
    }
}
