package me.frogdog.engine.core.rendering.world.body.mobs;

import me.frogdog.engine.core.EngineManager;
import me.frogdog.engine.core.rendering.world.body.Body;
import me.frogdog.engine.core.rendering.world.body.player.Player;
import me.frogdog.engine.core.rendering.world.Model;
import me.frogdog.engine.core.rendering.world.terrain.Terrain;
import me.frogdog.engine.utils.Consts;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Zombie extends Body {

    private static final float SPEED = 12.0f;
    private static final float TURN_SPEED = 25.0f;
    private static final float FOLLOW_SHARPNESS = 0.1f;

    private float currentTurnSpeed = 0;
    private float currentSpeed = 0;
    private float currentUpSpeed = 0;
    private float health = 100;

    public Zombie(Model model, Vector3f pos, Vector3f rotation, Vector3f size, float scale) {
        super(model, pos, rotation, size, scale);
    }

    public void update(Terrain terrain, Player player) {
        /*
        if (getDistanceFromPlayer(player) > 15.0f) {
            currentSpeed = SPEED;
        } else {
            currentSpeed = 0;
        }
        super.setRotation(0, getZombieRotation(player), 0);
        float distance = currentSpeed * EngineManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(super.getRotation().y));
        float dz = (float) (distance * Math.cos(super.getRotation().y));
        super.incPos(dx, 0, dz);

         */
        this.currentUpSpeed += Consts.GRAVITY * EngineManager.getFrameTimeSeconds();
        float terrainHeight = terrain.getTerrainHeight(super.getPosition().x, super.getPosition().z);
        if (super.getPosition().y < terrainHeight - 0.7f) { //quick fix
            this.currentUpSpeed = 0;
            super.getPosition().y = terrainHeight - 0.7f;
        }
        super.incPos(0, currentUpSpeed * EngineManager.getFrameTimeSeconds(), 0);
        Vector3f position = new Vector3f(player.getPosition().x, player.getPosition().y, player.getPosition().z);
        super.incPos(position.sub(super.getPosition()).normalize().mul(SPEED * EngineManager.getFrameTimeSeconds()));
    }

    private float getZombieRotation(Player player) {
        Vector3f zombiePos = new Vector3f(super.getPosition().x, super.getPosition().y, super.getPosition().z);
        zombiePos.sub(player.getPosition()).normalize();
        Vector3f zombieRotation = new Vector3f();
        Quaternionf q = new Quaternionf(zombiePos.x, zombiePos.y, zombiePos.z, 1.0);
        q.getEulerAnglesXYZ(zombieRotation);
        return zombieRotation.y;
    }

    private float getDistanceFromPlayer(Player player) {
        Vector3f distance = new Vector3f(super.getPosition().x, super.getPosition().y, super.getPosition().z);
        return distance.distance(player.getPosition());
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void incHealth(float health) {
        this.health += health;
    }
}
