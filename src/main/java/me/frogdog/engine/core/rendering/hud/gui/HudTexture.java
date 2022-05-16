package me.frogdog.engine.core.rendering.hud.gui;

import org.joml.Vector2f;

public class HudTexture {

    private int id;
    private Vector2f position;
    private Vector2f scale;

    public HudTexture(int id, Vector2f position, Vector2f scale) {
        this.id = id;
        this.position = position;
        this.scale = scale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }
}
