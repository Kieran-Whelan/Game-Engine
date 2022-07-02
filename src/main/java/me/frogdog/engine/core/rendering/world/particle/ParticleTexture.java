package me.frogdog.engine.core.rendering.world.particle;

import org.joml.Vector2f;

public class ParticleTexture {

    private int id;
    private Vector2f index;
    private Vector2f sheetDimensions;
    private Vector2f textureDimensions;

    public ParticleTexture(int id, Vector2f index, Vector2f sheetDimensions, Vector2f textureDimensions) {
        this.id = id;
        this.index = index;
        this.sheetDimensions = sheetDimensions;
        this.textureDimensions = textureDimensions;
    }

    public int getId() {
        return id;
    }

    public Vector2f getIndex() {
        return index;
    }

    public Vector2f getSheetDimensions() {
        return sheetDimensions;
    }

    public Vector2f getTextureDimensions() {
        return textureDimensions;
    }
}
