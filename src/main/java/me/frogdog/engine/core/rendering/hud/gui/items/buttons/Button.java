package me.frogdog.engine.core.rendering.hud.gui.items.buttons;

import me.frogdog.engine.core.rendering.hud.gui.Item;

public class Button extends Item {

    private String label;

    public Button(float x, float y, float width, float height, String label) {
        super(x, y, width, height);
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
