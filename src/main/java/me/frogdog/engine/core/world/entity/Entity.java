package me.frogdog.engine.core.world.entity;

import me.frogdog.engine.core.world.Model;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Entity {

    private Model model;
    private Vector3f pos, rotation;
    private float scale;

    public Entity(Model model, Vector3f pos, Vector3f rotation, float scale) {
        this.model = model;
        this.pos = pos;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void incPos(Vector3f pos) {
        this.pos.x += pos.x;
        this.pos.y += pos.y;
        this.pos.z += pos.z;
    }

    public void incPos(float x, float y, float z) {
        this.pos.x += x;
        this.pos.y += y;
        this.pos.z += z;
    }

    public void setPos(Vector3f pos) {
        this.pos.x = pos.x;
        this.pos.y = pos.y;
        this.pos.z = pos.z;
    }

    public void setPos(float x, float y, float z) {
        this.pos.x = x;
        this.pos.y = y;
        this.pos.z = z;
    }

    public void incRotation(Vector3f pos) {
        this.rotation.x += pos.x;
        this.rotation.y += pos.y;
        this.rotation.z += pos.z;
    }

    public void incRotation(float x, float y, float z) {
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }

    public void setRotation(Vector3f pos) {
        this.rotation.x = pos.x;
        this.rotation.y = pos.y;
        this.rotation.z = pos.z;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return pos;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

}
