package me.frogdog.engine.core.rendering.hud.gui.items.font;

import me.frogdog.engine.utils.loader.ObjectLoader;

public class Font {

    private final ObjectLoader loader;
    private String filename;
    private final int fontSheet;

    public Font(String filename) throws Exception {
        this.loader = new ObjectLoader();
        this.filename = filename;
        this.fontSheet = loader.loadTextureSheet(filename);
    }

    protected int getFont() {
        return fontSheet;
    }
}
