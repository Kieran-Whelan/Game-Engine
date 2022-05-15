package me.frogdog.engine.core.rendering.hud.font;

import me.frogdog.engine.utils.ObjectLoader;

public class Font {

    private final ObjectLoader loader;
    private String filename;
    private int fontSheet;

    public Font(String filename) throws Exception {
        this.loader = new ObjectLoader();
        this.filename = filename;
        this.fontSheet = loader.loadTextureSheet(filename);
    }

    public int getFontSheet() {
        return fontSheet;
    }
}
