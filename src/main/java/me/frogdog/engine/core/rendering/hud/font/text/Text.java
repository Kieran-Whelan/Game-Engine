package me.frogdog.engine.core.rendering.hud.font.text;

import me.frogdog.engine.core.rendering.RenderManager;
import me.frogdog.engine.core.rendering.hud.font.Font;
import me.frogdog.engine.core.rendering.hud.font.Glyph;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class Text {

    private Font font;
    private float space = 0.15f;

    public Text(String filename) throws Exception {
        font = new Font(filename);
    }

    public void drawString(String text, Vector2f pos) {
        char[] textGlyphs = text.toCharArray();
        float x = 0;
        for (char glyph : textGlyphs) {
            RenderManager.getInstance().getFontRenderer().getGlyphs().add(new Glyph(font, new Vector2f(pos.x + x, pos.y), new Vector2f(0.125f, 0.25f), getGlyphIndex(glyph)));
            x += space * getSpaceMultiplier(glyph);
        }
    }

    public void drawString(String text, Vector2f pos, Vector4f colour) {
        char[] textGlyphs = text.toCharArray();
        float x = 0;
        for (char glyph : textGlyphs) {
            RenderManager.getInstance().getFontRenderer().getGlyphs().add(new Glyph(font, new Vector2f(pos.x + x, pos.y), new Vector2f(0.125f, 0.25f), getGlyphIndex(glyph), colour));
            x += space * getSpaceMultiplier(glyph);
        }
    }

    public void drawString(String text, Vector2f pos, int factor) {
        char[] textGlyphs = text.toCharArray();
        float x = 0;
        for (char glyph : textGlyphs) {
            RenderManager.getInstance().getFontRenderer().getGlyphs().add(new Glyph(font, new Vector2f(pos.x + x, pos.y), new Vector2f(0.125f / factor, 0.25f / factor), getGlyphIndex(glyph)));
            x += (space / factor) * getSpaceMultiplier(glyph);
        }
    }

    public void drawString(String text, Vector2f pos, int factor, Vector4f colour) {
        char[] textGlyphs = text.toCharArray();
        float x = 0;
        for (char glyph : textGlyphs) {
            RenderManager.getInstance().getFontRenderer().getGlyphs().add(new Glyph(font, new Vector2f(pos.x + x, pos.y), new Vector2f(0.125f / factor, 0.25f / factor), getGlyphIndex(glyph), colour));
            x += (space / factor) * getSpaceMultiplier(glyph);
        }
    }

    private Vector2f getGlyphIndex(char glyph) {
        return switch (glyph) {
            case '!' -> new Vector2f(1.0f, 2.0f);
            case '"' -> new Vector2f(2.0f, 2.0f);
            case '#' -> new Vector2f(3.0f, 2.0f);
            case '$' -> new Vector2f(4.0f, 2.0f);
            case '%' -> new Vector2f(5.0f, 2.0f);
            case '&' -> new Vector2f(6.0f, 2.0f);
            case '\'' -> new Vector2f(7.0f, 2.0f);
            case '(' -> new Vector2f(8.0f, 2.0f);
            case ')' -> new Vector2f(9.0f, 2.0f);
            case '*' -> new Vector2f(10.0f, 2.0f);
            case '+' -> new Vector2f(11.0f, 2.0f);
            case ',' -> new Vector2f(12.0f, 2.0f);
            case '-' -> new Vector2f(13.0f, 2.0f);
            case '.' -> new Vector2f(14.0f, 2.0f);
            case '/' -> new Vector2f(15.0f, 2.0f);
            case '0' -> new Vector2f(0.0f, 3.0f);
            case '1' -> new Vector2f(1.0f, 3.0f);
            case '2' -> new Vector2f(2.0f, 3.0f);
            case '3' -> new Vector2f(3.0f, 3.0f);
            case '4' -> new Vector2f(4.0f, 3.0f);
            case '5' -> new Vector2f(5.0f, 3.0f);
            case '6' -> new Vector2f(6.0f, 3.0f);
            case '7' -> new Vector2f(7.0f, 3.0f);
            case '8' -> new Vector2f(8.0f, 3.0f);
            case '9' -> new Vector2f(9.0f, 3.0f);
            case ':' -> new Vector2f(10.0f, 3.0f);
            case ';' -> new Vector2f(11.0f, 3.0f);
            case '<' -> new Vector2f(12.0f, 3.0f);
            case '=' -> new Vector2f(13.0f, 3.0f);
            case '>' -> new Vector2f(14.0f, 3.0f);
            case '?' -> new Vector2f(15.0f, 3.0f);
            case '@' -> new Vector2f(0.0f, 4.0f);
            case 'A' -> new Vector2f(1.0f, 4.0f);
            case 'B' -> new Vector2f(2.0f, 4.0f);
            case 'C' -> new Vector2f(3.0f, 4.0f);
            case 'D' -> new Vector2f(4.0f, 4.0f);
            case 'E' -> new Vector2f(5.0f, 4.0f);
            case 'F' -> new Vector2f(6.0f, 4.0f);
            case 'G' -> new Vector2f(7.0f, 4.0f);
            case 'H' -> new Vector2f(8.0f, 4.0f);
            case 'I' -> new Vector2f(9.0f, 4.0f);
            case 'J' -> new Vector2f(10.0f, 4.0f);
            case 'K' -> new Vector2f(11.0f, 4.0f);
            case 'L' -> new Vector2f(12.0f, 4.0f);
            case 'M' -> new Vector2f(13.0f, 4.0f);
            case 'N' -> new Vector2f(14.0f, 4.0f);
            case 'O' -> new Vector2f(15.0f, 4.0f);
            case 'P' -> new Vector2f(0.0f, 5.0f);
            case 'Q' -> new Vector2f(1.0f, 5.0f);
            case 'R' -> new Vector2f(2.0f, 5.0f);
            case 'S' -> new Vector2f(3.0f, 5.0f);
            case 'T' -> new Vector2f(4.0f, 5.0f);
            case 'U' -> new Vector2f(5.0f, 5.0f);
            case 'V' -> new Vector2f(6.0f, 5.0f);
            case 'W' -> new Vector2f(7.0f, 5.0f);
            case 'X' -> new Vector2f(8.0f, 5.0f);
            case 'Y' -> new Vector2f(9.0f, 5.0f);
            case 'Z' -> new Vector2f(10.0f, 5.0f);
            case '[' -> new Vector2f(11.0f, 5.0f);
            case '\\' -> new Vector2f(12.0f, 5.0f);
            case ']' -> new Vector2f(13.0f, 5.0f);
            case '^' -> new Vector2f(14.0f, 5.0f);
            case '_' -> new Vector2f(15.0f, 5.0f);
            case '`' -> new Vector2f(0.0f, 6.0f);
            case 'a' -> new Vector2f(1.0f, 6.0f);
            case 'b' -> new Vector2f(2.0f, 6.0f);
            case 'c' -> new Vector2f(3.0f, 6.0f);
            case 'd' -> new Vector2f(4.0f, 6.0f);
            case 'e' -> new Vector2f(5.0f, 6.0f);
            case 'f' -> new Vector2f(6.0f, 6.0f);
            case 'g' -> new Vector2f(7.0f, 6.0f);
            case 'h' -> new Vector2f(8.0f, 6.0f);
            case 'i' -> new Vector2f(9.0f, 6.0f);
            case 'j' -> new Vector2f(10.0f, 6.0f);
            case 'k' -> new Vector2f(11.0f, 6.0f);
            case 'l' -> new Vector2f(12.0f, 6.0f);
            case 'm' -> new Vector2f(13.0f, 6.0f);
            case 'n' -> new Vector2f(14.0f, 6.0f);
            case 'o' -> new Vector2f(15.0f, 6.0f);
            case 'p' -> new Vector2f(0.0f, 7.0f);
            case 'q' -> new Vector2f(1.0f, 7.0f);
            case 'r' -> new Vector2f(2.0f, 7.0f);
            case 's' -> new Vector2f(3.0f, 7.0f);
            case 't' -> new Vector2f(4.0f, 7.0f);
            case 'u' -> new Vector2f(5.0f, 7.0f);
            case 'v' -> new Vector2f(6.0f, 7.0f);
            case 'w' -> new Vector2f(7.0f, 7.0f);
            case 'x' -> new Vector2f(8.0f, 7.0f);
            case 'y' -> new Vector2f(9.0f, 7.0f);
            case 'z' -> new Vector2f(10.0f, 7.0f);
            case '{' -> new Vector2f(11.0f, 7.0f);
            case '|' -> new Vector2f(12.0f, 7.0f);
            case '}' -> new Vector2f(13.0f, 7.0f);
            case '~' -> new Vector2f(14.0f, 7.0f);
            default -> new Vector2f(1.0f, 0.0f);
        };
    }

    private float getSpaceMultiplier(char glyph) {
        if (isUpperCase(glyph)) {
            return 1.25f;
        }
        return 1.0f;
    }

    private boolean isUpperCase(char glyph) {
        return switch (glyph) {
            case 'A', 'G', 'B', 'C', 'D', 'E', 'F', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' -> true;
            default -> false;
        };
    }

    private boolean isLowerCase(char glyph) {
        return switch (glyph) {
            case 'a', 'g', 'b', 'c', 'd', 'e', 'f', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' -> true;
            default -> false;
        };
    }
}
