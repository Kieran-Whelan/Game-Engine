package me.frogdog.engine.core.world.entity.projectile;

import me.frogdog.engine.core.EngineManager;
import me.frogdog.engine.core.world.Model;
import me.frogdog.engine.core.world.entity.Entity;
import org.joml.Vector3f;

public class Bullet extends Entity {

    private static final float SPEED = 120f;

    private float damage;

    public Bullet(Model model, Vector3f pos, Vector3f rotation, Vector3f size, float scale, float damage) {
        super(model, pos, rotation, size, scale);
        this.damage = damage;
    }

    public void update() {
        float distance = SPEED * EngineManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotation().y)));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotation().y)));
        super.incPos(dx, 0, dz);
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage() {
        this.damage = damage;
    }
}
