package me.frogdog.engine.core.maths;

import me.frogdog.engine.core.world.entity.Entity;
import me.frogdog.engine.core.world.terrain.Terrain;
import me.frogdog.engine.core.rendering.hud.gui.HudTexture;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {

    public static Matrix4f createTransformationMatrix(HudTexture hudTexture) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(hudTexture.getPosition().x, hudTexture.getPosition().y, 0f).
                scale(hudTexture.getScale().x, hudTexture.getScale().y, 1f);
        return matrix;
    }

    public static Matrix4f createTransformationMatrix(Entity entity) {
        Matrix4f matrix = new Matrix4f();
        // divide by two to fix speed
        matrix.identity().translate(entity.getPosition())
                .rotateX((float) Math.toRadians(entity.getRotation().x))
                .rotateY((float) Math.toRadians(entity.getRotation().y))
                .rotateZ((float) Math.toRadians(entity.getRotation().z))
                .scale(entity.getScale());

        return matrix;
    }

    public static Matrix4f createTransformationMatrix(Terrain terrain) {
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(terrain.getPosition()).scale(1);
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
