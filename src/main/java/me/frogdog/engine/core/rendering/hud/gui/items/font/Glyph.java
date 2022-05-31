package me.frogdog.engine.core.rendering.hud.gui.items.font;

import me.frogdog.engine.core.rendering.hud.gui.items.GuiTexture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Glyph extends GuiTexture {

    private Font font;
    private Vector2f index;
    private Vector4f colour;

    public Glyph(Font font, Vector2f position, Vector2f scale, Vector2f index) {
        super(font.getFont(), position.x, position.y, scale.x, scale.y);
        this.index = index;
        this.colour = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public Glyph(Font font, Vector2f position, Vector2f scale, Vector2f index, Vector4f colour) {
        super(font.getFont(), position.x, position.y, scale.x, scale.y);
        this.index = index;
        this.colour = colour;
    }

    public Vector2f getIndex() {
        return index;
    }

    public Vector4f getColour() {
        return colour;
    }
}
