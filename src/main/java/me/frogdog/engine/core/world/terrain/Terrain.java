package me.frogdog.engine.core.world.terrain;

import me.frogdog.engine.utils.ObjectLoader;
import me.frogdog.engine.core.world.Material;
import me.frogdog.engine.core.world.Model;
import me.frogdog.engine.core.world.Texture;
import org.joml.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Terrain {

    private static final float SIZE = 800;
    private static final int VERTEX_COUNT = 128;
    private static final float MAX_HEIGHT = 120.0f;
    private static final float MAX_PIXEL_COLOUR = 256.0f * 256.0f * 256.0f;

    private Vector3f position;
    private Model model;
    private TerrainTexture blendMap;
    private BlendMapTerrain blendMapTerrain;

    public Terrain(Vector3f position, ObjectLoader loader, Material material) {
        this.position = position;
        this.model = generateTerrain(loader);
        this.model.setMaterial(material);
    }

    public Terrain(Vector3f position, ObjectLoader loader, Material material, BlendMapTerrain blendMapTerrain, TerrainTexture blendMap) {
        this.position = position;
        this.model = generateTerrain(loader);
        this.model.setMaterial(material);
        this.blendMap = blendMap;
        this.blendMapTerrain = blendMapTerrain;
    }

    public Terrain(Vector3f position, ObjectLoader loader, Material material, BlendMapTerrain blendMapTerrain, TerrainTexture blendMap, String heightMap) {
        this.position = position;
        this.model = generateTerrain(loader, heightMap);
        this.model.setMaterial(material);
        this.blendMap = blendMap;
        this.blendMapTerrain = blendMapTerrain;
    }

    private Model generateTerrain(ObjectLoader loader) {
        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
        int vertexPointer = 0;


        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
                vertices[vertexPointer * 3] = j / (VERTEX_COUNT - 1.0f) * SIZE;
                vertices[vertexPointer * 3 + 1] = 0;
                vertices[vertexPointer * 3 + 2] = i / (VERTEX_COUNT - 1.0f) * SIZE;
                normals[vertexPointer * 3] = 0;
                normals[vertexPointer * 3 + 1] = 1;
                normals[vertexPointer * 3 + 2] = 0;
                textureCoords[vertexPointer * 2] = j / (VERTEX_COUNT - 1.0f);
                textureCoords[vertexPointer * 2 + 1] = i / (VERTEX_COUNT - 1.0f);
                vertexPointer++;
            }
        }

        int pointer = 0;
        for (int z = 0; z < VERTEX_COUNT - 1.0f; z++) {
            for (int x = 0; x < VERTEX_COUNT - 1.0f; x++) {
                int topLeft = (z * VERTEX_COUNT) + x;
                int topRight = topLeft + 1;
                int bottomLeft = ((z + 1) * VERTEX_COUNT) + x;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadModel(vertices, textureCoords, normals, indices);
    }

    private Model generateTerrain(ObjectLoader loader, String heightMap) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(heightMap));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert image != null;
        int VERTEX_COUNT = image.getHeight();

        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6 * (VERTEX_COUNT - 1) * (VERTEX_COUNT - 1)];
        int vertexPointer = 0;


        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
                vertices[vertexPointer * 3] = j / (VERTEX_COUNT - 1.0f) * SIZE;
                vertices[vertexPointer * 3 + 1] = getHeight(j, i, image);
                vertices[vertexPointer * 3 + 2] = i / (VERTEX_COUNT - 1.0f) * SIZE;
                Vector3f normal = calcNormal(j, i, image);
                normals[vertexPointer * 3] = normal.x;
                normals[vertexPointer * 3 + 1] = normal.y;
                normals[vertexPointer * 3 + 2] = normal.z;
                textureCoords[vertexPointer * 2] = j / (VERTEX_COUNT - 1.0f);
                textureCoords[vertexPointer * 2 + 1] = i / (VERTEX_COUNT - 1.0f);
                vertexPointer++;
            }
        }

        int pointer = 0;
        for (int z = 0; z < VERTEX_COUNT - 1.0f; z++) {
            for (int x = 0; x < VERTEX_COUNT - 1.0f; x++) {
                int topLeft = (z * VERTEX_COUNT) + x;
                int topRight = topLeft + 1;
                int bottomLeft = ((z + 1) * VERTEX_COUNT) + x;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadModel(vertices, textureCoords, normals, indices);
    }

    private Vector3f calcNormal(int x, int z, BufferedImage image) {
        float heightL = getHeight(x - 1, z, image);
        float heightR = getHeight(x + 1, z, image);
        float heightD = getHeight(x, z - 1, image);
        float heightU = getHeight(x, z + 1, image);
        Vector3f normal = new Vector3f(heightL - heightR, 2.0f, heightD - heightU);
        normal.normalize();
        return normal;
    }

    private float getHeight(int x, int z, BufferedImage image) {
        if (x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()) {
            return 0;
        }
        float height = image.getRGB(x, z);
        height += MAX_PIXEL_COLOUR / 2f;
        height /= MAX_PIXEL_COLOUR / 2f;
        height *= MAX_HEIGHT / 2f;
        return height;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Model getModel() {
        return model;
    }

    public Material getMaterial() {
        return model.getMaterial();
    }

    public Texture getTexture() {
        return model.getTexture();
    }

    public TerrainTexture getBlendMap() {
        return blendMap;
    }

    public BlendMapTerrain getBlendMapTerrain() {
        return blendMapTerrain;
    }
}