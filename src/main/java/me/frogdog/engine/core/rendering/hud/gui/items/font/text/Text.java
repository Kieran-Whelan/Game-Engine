package me.frogdog.engine.core.rendering.hud.gui.items.font.text;

import me.frogdog.engine.core.rendering.RenderManager;
import me.frogdog.engine.core.rendering.hud.gui.items.font.Font;
import me.frogdog.engine.core.rendering.hud.gui.items.font.Glyph;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class Text {

    private List<Glyph> text;
    private final float space = 0.023f;
    private final int factor = 8;
    private final char[] lowerCase = {'a', 'g', 'b', 'c', 'd', 'e', 'f', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private final char[] upperCase = {'A', 'G', 'B', 'C', 'D', 'E', 'F', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public Text(Font font, String string, float x, float y) {
        text = getGlyphString(font, string, new Vector2f(x, y));
    }

    public List<Glyph> getGlyphString(Font font, String text, Vector2f pos) {
        List<Glyph> glyphs = new ArrayList<>();
        char[] textGlyphs = text.toCharArray();
        float x = 0;
        for (char glyph : textGlyphs) {
            glyphs.add(new Glyph(font, new Vector2f(pos.x + x, pos.y), new Vector2f(0.125f / factor, 0.25f / factor), getGlyphIndex(glyph)));
            x += space * getSpaceMultiplier(glyph);
        }
        return glyphs;
    }

    /*
    public void drawString(String text, Vector2f pos, Vector4f colour) {
        char[] textGlyphs = text.toCharArray();
        float x = 0;
        for (char glyph : textGlyphs) {
            RenderManager.getInstance().getFontRenderer().getGlyphs().add(new Glyph(this, new Vector2f(pos.x + x, pos.y), new Vector2f(0.125f, 0.25f), getGlyphIndex(glyph), colour));
            x += space * getSpaceMultiplier(glyph);
        }
    }

    public void drawString(String text, Vector2f pos, int factor) {
        char[] textGlyphs = text.toCharArray();
        float x = 0;
        for (char glyph : textGlyphs) {
            RenderManager.getInstance().getFontRenderer().getGlyphs().add(new Glyph(this, new Vector2f(pos.x + x, pos.y), new Vector2f(0.125f / factor, 0.25f / factor), getGlyphIndex(glyph)));
            x += (space / factor) * getSpaceMultiplier(glyph);
        }
    }

    public void drawString(String text, Vector2f pos, int factor, Vector4f colour) {
        char[] textGlyphs = text.toCharArray();
        float x = 0;
        for (char glyph : textGlyphs) {
            RenderManager.getInstance().getFontRenderer().getGlyphs().add(new Glyph(this, new Vector2f(pos.x + x, pos.y), new Vector2f(0.125f / factor, 0.25f / factor), getGlyphIndex(glyph), colour));
            x += (space / factor) * getSpaceMultiplier(glyph);
        }
    }

     */

    //shadowJar doesnt build this function
    /*
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
            case ' ' -> new Vector2f(0.0f, 2.0f);
            default -> new Vector2f(1.0f, 0.0f);
        };
    }
     */

    private Vector2f getGlyphIndex(char glyph) {
        Vector2f result;
        switch (glyph) {
            case '!':
                result = new  Vector2f(1.0f, 2.0f);
                break;
            case '"':
                result = new  Vector2f(2.0f, 2.0f);
                break;
            case '#':
                result = new  Vector2f(3.0f, 2.0f);
                break;
            case '$':
                result = new  Vector2f(4.0f, 2.0f);
                break;
            case '%':
                result = new  Vector2f(5.0f, 2.0f);
                break;
            case '&':
                result = new  Vector2f(6.0f, 2.0f);
                break;
            case '\'':
                result = new  Vector2f(7.0f, 2.0f);
                break;
            case '(':
                result = new  Vector2f(8.0f, 2.0f);
                break;
            case ')':
                result = new  Vector2f(9.0f, 2.0f);
                break;
            case '*':
                result = new  Vector2f(10.0f, 2.0f);
                break;
            case '+':
                result = new  Vector2f(11.0f, 2.0f);
                break;
            case ',':
                result = new  Vector2f(12.0f, 2.0f);
                break;
            case '-':
                result = new  Vector2f(13.0f, 2.0f);
                break;
            case '.':
                result = new  Vector2f(14.0f, 2.0f);
                break;
            case '/':
                result = new  Vector2f(15.0f, 2.0f);
                break;
            case '0':
                result = new  Vector2f(0.0f, 3.0f);
                break;
            case '1':
                result = new  Vector2f(1.0f, 3.0f);
                break;
            case '2':
                result = new  Vector2f(2.0f, 3.0f);
                break;
            case '3':
                result = new  Vector2f(3.0f, 3.0f);
                break;
            case '4':
                result = new  Vector2f(4.0f, 3.0f);
                break;
            case '5':
                result = new  Vector2f(5.0f, 3.0f);
                break;
            case '6':
                result = new  Vector2f(6.0f, 3.0f);
                break;
            case '7':
                result = new  Vector2f(7.0f, 3.0f);
                break;
            case '8':
                result = new  Vector2f(8.0f, 3.0f);
                break;
            case '9':
                result = new  Vector2f(9.0f, 3.0f);
                break;
            case ':':
                result = new Vector2f(10.0f, 3.0f);
                break;
            case ';':
                result = new Vector2f(11.0f, 3.0f);
                break;
            case '<':
                result = new Vector2f(12.0f, 3.0f);
                break;
            case '=':
                result = new Vector2f(13.0f, 3.0f);
                break;
            case '>':
                result = new Vector2f(14.0f, 3.0f);
                break;
            case '?':
                result = new Vector2f(15.0f, 3.0f);
                break;
            case '@':
                result = new Vector2f(0.0f, 4.0f);
                break;
            case 'A':
                result = new Vector2f(1.0f, 4.0f);
                break;
            case 'B':
                result = new Vector2f(2.0f, 4.0f);
                break;
            case 'C':
                result = new Vector2f(3.0f, 4.0f);
                break;
            case 'D':
                result = new Vector2f(4.0f, 4.0f);
                break;
            case 'E':
                result = new Vector2f(5.0f, 4.0f);
                break;
            case 'F':
                result = new Vector2f(6.0f, 4.0f);
                break;
            case 'G':
                result = new Vector2f(7.0f, 4.0f);
                break;
            case 'H':
                result = new Vector2f(8.0f, 4.0f);
                break;
            case 'I':
                result = new Vector2f(9.0f, 4.0f);
                break;
            case 'J':
                result = new Vector2f(10.0f, 4.0f);
                break;
            case 'K':
                result = new Vector2f(11.0f, 4.0f);
                break;
            case 'L':
                result = new Vector2f(12.0f, 4.0f);
                break;
            case 'M':
                result = new Vector2f(13.0f, 4.0f);
                break;
            case 'N':
                result = new Vector2f(14.0f, 4.0f);
                break;
            case 'O':
                result = new Vector2f(15.0f, 4.0f);
                break;
            case 'P':
                result = new Vector2f(0.0f, 5.0f);
                break;
            case 'Q':
                result = new Vector2f(1.0f, 5.0f);
                break;
            case 'R':
                result = new Vector2f(2.0f, 5.0f);
                break;
            case 'S':
                result = new Vector2f(3.0f, 5.0f);
                break;
            case 'T':
                result = new Vector2f(4.0f, 5.0f);
                break;
            case 'U':
                result = new Vector2f(5.0f, 5.0f);
                break;
            case 'V':
                result = new Vector2f(6.0f, 5.0f);
                break;
            case 'W':
                result = new Vector2f(7.0f, 5.0f);
                break;
            case 'X':
                result = new Vector2f(8.0f, 5.0f);
                break;
            case 'Y':
                result = new Vector2f(9.0f, 5.0f);
            case 'Z':
                result = new Vector2f(10.0f, 5.0f);
                break;
            case '[':
                result = new Vector2f(11.0f, 5.0f);
                break;
            case '\\':
                result = new Vector2f(12.0f, 5.0f);
                break;
            case ']':
                result = new Vector2f(13.0f, 5.0f);
                break;
            case '^':
                result = new Vector2f(14.0f, 5.0f);
                break;
            case '_':
                result = new Vector2f(15.0f, 5.0f);
                break;
            case '`':
                result = new Vector2f(0.0f, 6.0f);
                break;
            case 'a':
                result = new Vector2f(1.0f, 6.0f);
                break;
            case 'b':
                result = new Vector2f(2.0f, 6.0f);
                break;
            case 'c':
                result = new Vector2f(3.0f, 6.0f);
                break;
            case 'd':
                result = new Vector2f(4.0f, 6.0f);
                break;
            case 'e':
                result = new Vector2f(5.0f, 6.0f);
                break;
            case 'f':
                result = new Vector2f(6.0f, 6.0f);
                break;
            case 'g':
                result = new Vector2f(7.0f, 6.0f);
                break;
            case 'h':
                result = new Vector2f(8.0f, 6.0f);
                break;
            case 'i':
                result = new Vector2f(9.0f, 6.0f);
                break;
            case 'j':
                result = new Vector2f(10.0f, 6.0f);
                break;
            case 'k':
                result = new Vector2f(11.0f, 6.0f);
                break;
            case 'l':
                result = new Vector2f(12.0f, 6.0f);
                break;
            case 'm':
                result = new Vector2f(13.0f, 6.0f);
                break;
            case 'n':
                result = new Vector2f(14.0f, 6.0f);
                break;
            case 'o':
                result = new Vector2f(15.0f, 6.0f);
                break;
            case 'p':
                result = new Vector2f(0.0f, 7.0f);
                break;
            case 'q':
                result = new Vector2f(1.0f, 7.0f);
                break;
            case 'r':
                result = new Vector2f(2.0f, 7.0f);
                break;
            case 's':
                result = new Vector2f(3.0f, 7.0f);
                break;
            case 't':
                result = new Vector2f(4.0f, 7.0f);
                break;
            case 'u':
                result = new Vector2f(5.0f, 7.0f);
                break;
            case 'v':
                result = new Vector2f(6.0f, 7.0f);
                break;
            case 'w':
                result = new Vector2f(7.0f, 7.0f);
                break;
            case 'x':
                result = new Vector2f(8.0f, 7.0f);
                break;
            case 'y':
                result = new Vector2f(9.0f, 7.0f);
                break;
            case 'z':
                result = new Vector2f(10.0f, 7.0f);
                break;
            case '{':
                result = new Vector2f(11.0f, 7.0f);
                break;
            case '|':
                result = new Vector2f(12.0f, 7.0f);
                break;
            case '}':
                result = new Vector2f(13.0f, 7.0f);
                break;
            case '~':
                result = new Vector2f(14.0f, 7.0f);
                break;
            case ' ':
                result = new Vector2f(0.0f, 2.0f);
                break;
            default:
                result = new Vector2f(1.0f, 0.0f);
                break;
        };
        return result;
    }

    private float getSpaceMultiplier(char glyph) {
        if (glyph == 'i') {
            return 0.65f;
        }

        if (glyph == 'I') {
            return 0.75f;
        }

        if (glyph == 'w' || glyph == 'm') {
            return 1.5f;
        }

        if (glyph == 'W' || glyph == 'M') {
            return 1.75f;
        }

        if (isUpperCase(glyph)) {
            return 1.25f;
        }
        return 1.0f;
    }

    private boolean isUpperCase(char glyph) {
        for (char c : upperCase) {
            if (glyph == c) {
                return true;
            }
        }
        return false;
    }

    private boolean isLowerCase(char glyph) {
        for (char c : lowerCase) {
            if (glyph == c) {
                return true;
            }
        }
        return false;
    }

    public List<Glyph> getText() {
        return text;
    }
}
