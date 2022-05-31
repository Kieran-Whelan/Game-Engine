package me.frogdog.engine.core.rendering.hud.gui;

import org.joml.Vector2f;

public class Item {

    private Vector2f position;
    private Vector2f scale;

    public Item(float x, float y, float width, float height) {
        this.position = new Vector2f(x, y);
        this.scale = new Vector2f(width, height);
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }
}
