package me.frogdog.engine.core.rendering.world.body;

import me.frogdog.engine.core.maths.Maths;
import me.frogdog.engine.core.rendering.world.Model;
import org.joml.Vector3f;

public class PhysicsBody extends Body {

    private Vector3f velocity;
    private float mass;
    private float time;

    public PhysicsBody(Model model, Vector3f pos, Vector3f rotation, Vector3f size, float scale, float mass) {
        super(model, pos, rotation, size, scale);
        this.velocity = new Vector3f();
        this.mass = mass;
    }

    public void update(float deltaTime) {
        if (Maths.getMagnitude(velocity) != 0) {
            time += deltaTime;
        } else {
            time = 0;
        }
    }

    public float getForce() {
        return mass * (Maths.getMagnitude(velocity) / time);
    }

    public void incVel(Vector3f vel) {
        this.velocity.x += vel.x;
        this.velocity.y += vel.y;
        this.velocity.z += vel.z;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }
}
