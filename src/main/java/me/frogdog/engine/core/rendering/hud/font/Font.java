package me.frogdog.engine.core.rendering.hud.font;

import me.frogdog.engine.core.entity.Material;
import me.frogdog.engine.core.entity.Model;
import me.frogdog.engine.core.entity.Texture;
import me.frogdog.engine.utils.ObjectLoader;

public class Font {

    private final ObjectLoader loader;
    private String filename;
    private float cols, rows, tileWidth, tileHeight;

    public Font(String filename, float cols, float rows, float tileWidth, float tileHeight) {
        this.loader = new ObjectLoader();
        this.filename = filename;
        this.cols = cols;
        this.rows = rows;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public Model getGlyph(int col, int row) throws Exception {
        float[] quadVertices = new float[] {
                -0.25f, 0.25f, 0.25f,
                -0.25f, -0.25f, 0.25f,
                0.25f, -0.25f, 0.25f,
                0.25f, 0.25f, 0.25f
        };

        float[] quadTextCoords = new float[] {
                ((1.0f / cols) * (col - 1)), (1.0f / rows) * (row - 1),
                ((1.0f / cols) * (col - 1)), (1.0f / rows) * row,
                ((1.0f / cols) * col), (1.0f / rows) * row,
                ((1.0f / cols) * col), (1.0f / rows) * (row - 1)
        };

        int[] quadIndices = new int[] {
                0, 1, 3,
                3, 1, 2
        };

        System.out.println(1.0f / cols);

        Model model = loader.loadModel(quadVertices, quadTextCoords, quadIndices);
        model.setMaterial(new Material(new Texture(loader.loadTexture(filename)), 1.0f));
        model.getMaterial().setDisableCulling(true);
        return model;
    }
}
