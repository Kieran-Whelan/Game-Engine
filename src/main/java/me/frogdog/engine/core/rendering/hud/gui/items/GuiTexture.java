package me.frogdog.engine.core.rendering.hud.gui.items;

import me.frogdog.engine.core.rendering.hud.gui.Item;
import org.joml.Vector2f;

public class GuiTexture extends Item {

    private int id;

    public GuiTexture(int id, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
