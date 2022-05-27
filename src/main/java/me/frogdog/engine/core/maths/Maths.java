package me.frogdog.engine.core.maths;

import me.frogdog.engine.core.rendering.hud.gui.Item;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.primitives.AABBd;
import org.joml.primitives.AABBf;

public class Maths {

    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    public static boolean isCollide2D(Item item1, Item item2) {
        if ((item1.getPosition().x < item2.getPosition().x + item2.getScale().x
                && item1.getPosition().x + item1.getScale().x > item2.getPosition().x
                && item1.getPosition().y < item2.getPosition().y + item2.getScale().y
                && item1.getScale().y + item1.getPosition().y > item2.getPosition().y)) {
            return true;
        }
        return false;
    }
}
