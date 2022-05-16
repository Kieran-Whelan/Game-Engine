package me.frogdog.engine.core.rendering.hud.gui.items;

import me.frogdog.engine.core.rendering.hud.gui.Item;
import org.joml.Vector2f;

public class GuiTexture extends Item {

    private int id;

    public GuiTexture(int id, Vector2f position, Vector2f scale) {
        super(position, scale);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
