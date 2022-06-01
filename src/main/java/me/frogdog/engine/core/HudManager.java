package me.frogdog.engine.core;

import me.frogdog.engine.core.rendering.hud.gui.Item;
import me.frogdog.engine.core.rendering.hud.gui.items.font.Font;
import me.frogdog.engine.core.rendering.hud.gui.items.font.text.Text;

import java.util.ArrayList;
import java.util.List;

public class HudManager {

    private Font font;
    private List<Text> text;
    private List<Item> items;

    public HudManager(Font font) {
        this.font = font;
        text = new ArrayList<>();
        items = new ArrayList<>();
    }

    public List<Text> getText() {
        return text;
    }

    public void drawText(String string, float x, float y) {
        text.add(new Text(font, string, x, y, 1));
    }

    public void drawText(String string, float x, float y, int scale) {
        text.add(new Text(font, string, x, y, scale));
    }

    public float getTextWidth(String string) {
        Text test = new Text(font, string, 0, 0, 1);
        return test.getWidth();
    }

    public float getTextWidth(String string, int scale) {
        Text test = new Text(font, string, 0, 0, scale);
        return test.getWidth();
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }
}
