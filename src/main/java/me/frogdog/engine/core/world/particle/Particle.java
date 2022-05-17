package me.frogdog.engine.core.world.particle;

import me.frogdog.engine.core.EngineManager;
import me.frogdog.engine.core.rendering.RenderManager;
import me.frogdog.engine.utils.Consts;
import org.joml.Vector3f;

public class Particle {

    private Vector3f position;
    private Vector3f velocity;
    private float gravityEffect;
    private float lifeLength;
    private float rotation;
    private float scale;

    private float elapsedTime = 0;

    public Particle(Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation, float scale) {
        this.position = position;
        this.velocity = velocity;
        this.gravityEffect = gravityEffect;
        this.lifeLength = lifeLength;
        this.rotation = rotation;
        this.scale = scale;
    }

    public boolean update() {
        velocity.y += Consts.GRAVITY * gravityEffect * EngineManager.getFrameTimeSeconds();
        Vector3f change = new Vector3f(velocity);
        change.mul(EngineManager.getFrameTimeSeconds());
        position.add(change);
        elapsedTime += EngineManager.getFrameTimeSeconds();
        return elapsedTime < lifeLength;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }
}
