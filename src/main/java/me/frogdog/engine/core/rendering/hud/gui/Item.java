package me.frogdog.engine.core.rendering.hud.gui;

import org.joml.Vector2f;

public class Item {

    private Vector2f position;
    private Vector2f scale;

    public Item(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }
}
