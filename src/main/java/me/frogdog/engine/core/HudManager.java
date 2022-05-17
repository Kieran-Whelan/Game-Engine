package me.frogdog.engine.core;

import me.frogdog.engine.core.rendering.hud.gui.items.font.text.Text;

import java.util.ArrayList;
import java.util.List;

public class HudManager {

    private List<Text> text;

    public HudManager() {
        text = new ArrayList<>();
    }

    public List<Text> getText() {
        return text;
    }

    public void addText(Text textElement) {
        text.add(textElement);
    }
}
