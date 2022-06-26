package me.frogdog.engine.core.maths;

import me.frogdog.engine.core.rendering.hud.gui.Item;
import me.frogdog.engine.core.world.entity.Entity;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Maths {

    public static int randRange(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public static float randRange(float min, float max) {
        return min + (float)(Math.random() * ((max - min) + 1));
    }

    public static double randRange(double min, double max) {
        return min + (double)(Math.random() * ((max - min)));
    }

    public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
        float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
        float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
        float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
        float l3 = 1.0f - l1 - l2;
        return l1 * p1.y + l2 * p2.y + l3 * p3.y;
    }

    public static boolean isCollide2D(Item item1, Item item2) {
        boolean collisionX = (item1.getPosition().x - item1.getScale().x) + item1.getScale().x * 2 >= (item2.getPosition().x - item2.getScale().x) &&
                (item2.getPosition().x - item2.getScale().x) + item2.getScale().x * 2 >= (item1.getPosition().x - item1.getScale().x);
        boolean collisionY = (item1.getPosition().y - item1.getScale().y) + item1.getScale().y * 2 >= (item2.getPosition().y - item2.getScale().y) &&
                (item2.getPosition().y - item2.getScale().y) + item2.getScale().y * 2 >= (item1.getPosition().y - item1.getScale().y);
        return collisionX && collisionY;
    }

    public static boolean isPointInsideAABB(Vector3f point, Entity entity) {
         boolean collisionX = (point.x >= entity.getPosition().x && point.x <= (entity.getPosition().x + 5.0));
         boolean collisionY = (point.y >= entity.getPosition().y && point.y <= (entity.getPosition().y + 5.0));
         boolean collisionZ = (point.z >= entity.getPosition().z && point.z <= (entity.getPosition().z + 5.0));
         return collisionX && collisionY && collisionZ;
    }

    public static boolean isAABBInsideAABB(Entity item1, Entity item2) {
        boolean collisionX = (item1.getPosition().x - item1.getSize().x) + item1.getSize().x * 2 >= (item2.getPosition().x - item2.getSize().x) &&
                (item2.getPosition().x - item2.getSize().x) + item2.getSize().x * 2 >= (item1.getPosition().x - item1.getSize().x);
        boolean collisionY = (item1.getPosition().y - item1.getSize().y) + item1.getSize().y * 2 >= (item2.getPosition().y - item2.getSize().y) &&
                (item2.getPosition().y - item2.getSize().y) + item2.getSize().y * 2 >= (item1.getPosition().y - item1.getSize().y);
        boolean collisionZ = (item1.getPosition().z - item1.getSize().z) + item1.getSize().z * 2 >= (item2.getPosition().z - item2.getSize().z) &&
                (item2.getPosition().z - item2.getSize().z) + item2.getSize().z * 2 >= (item1.getPosition().z - item1.getSize().z);
        return collisionX && collisionY && collisionZ;
    }
}
