package me.frogdog.engine.core.maths;

import me.frogdog.engine.core.rendering.hud.gui.Item;
import me.frogdog.engine.core.rendering.world.body.Body;
import me.frogdog.engine.core.rendering.world.body.mobs.Zombie;
import me.frogdog.engine.core.rendering.world.terrain.Terrain;
import me.frogdog.engine.core.rendering.hud.gui.items.GuiTexture;
import me.frogdog.engine.core.rendering.world.water.WaterTile;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {

    public static Matrix4f createTransformationMatrix(GuiTexture hudTexture) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(hudTexture.getPosition().x, hudTexture.getPosition().y, 0f).
                scale(hudTexture.getScale().x, hudTexture.getScale().y, 1f);
        return matrix;
    }

    public static Matrix4f createTransformationMatrix(Item item) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(item.getPosition().x, item.getPosition().y, 0f).
                scale(item.getScale().x, item.getScale().y, 1f);
        return matrix;
    }

    public static Matrix4f createTransformationMatrix(Body entity) {
        Matrix4f matrix = new Matrix4f();
        if (entity instanceof Zombie) {
            matrix.identity().translate(entity.getPosition())
                    .rotateX((float) entity.getRotation().x)
                    .rotateY((float) entity.getRotation().y)
                    .rotateZ((float) entity.getRotation().z)
                    .scale(entity.getScale());
        } else {
            matrix.identity().translate(entity.getPosition())
                    .rotateX((float) Math.toRadians(entity.getRotation().x))
                    .rotateY((float) Math.toRadians(entity.getRotation().y))
                    .rotateZ((float) Math.toRadians(entity.getRotation().z))
                    .scale(entity.getScale());
        }

        return matrix;
    }

    public static Matrix4f createTransformationMatrix(Terrain terrain) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(terrain.getPosition()).scale(1);
        return matrix;
    }

    public static Matrix4f createTransformationMatrix(WaterTile waterTile) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(waterTile.getPosition()).rotateX((float) Math.toRadians(90)).scale(waterTile.getScale());
        return matrix;
    }

    public static Matrix4f getViewMatrix(Camera camera) {
        Vector3f pos = camera.getPosition();
        Matrix4f matrix = new Matrix4f();
        matrix.identity();
        matrix.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0))
                .rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0))
                .rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0, 0, 1));
        matrix.translate(-pos.x, -pos.y, -pos.z);

        return matrix;
    }
}
