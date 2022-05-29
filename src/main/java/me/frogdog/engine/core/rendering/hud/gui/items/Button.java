package me.frogdog.engine.core.rendering.hud.gui.items;

import me.frogdog.engine.core.rendering.hud.gui.Item;
import org.joml.Vector2f;

public class Button extends Item {

    private String label;

    public Button(Vector2f position, Vector2f scale, String label) {
        super(position, scale);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
