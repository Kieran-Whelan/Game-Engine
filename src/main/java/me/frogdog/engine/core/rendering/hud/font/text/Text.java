package me.frogdog.engine.core.rendering.hud.font.text;

import me.frogdog.engine.core.rendering.hud.HudTexture;
import me.frogdog.engine.core.rendering.hud.font.FontRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Text {

    private FontRenderer fontRenderer;

    public void drawString(String text, Vector2f pos) {
        char[] textGlyphs = text.toCharArray();
        for (char glyph : textGlyphs) {
        }

    }

    public void drawString(String text, Vector2f pos, Vector4f colour) {

    }

    private Vector2f getGlyphIndex(char glyph) {
        Vector2f index;
        switch (glyph) {
            case 'A':
                index = new Vector2f(1.0f, 5.0f);
                break;
            default:
                index = new Vector2f(0.0f, 0.0f);
                break;
        }

        return index;
    }
}
