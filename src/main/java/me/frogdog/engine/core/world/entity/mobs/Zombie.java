package me.frogdog.engine.core.world.entity.mobs;

import me.frogdog.engine.core.EngineManager;
import me.frogdog.engine.core.input.keyboard.Keyboard;
import me.frogdog.engine.core.world.Model;
import me.frogdog.engine.core.world.entity.Entity;
import me.frogdog.engine.core.world.entity.player.Player;
import me.frogdog.engine.core.world.terrain.Terrain;
import me.frogdog.engine.utils.Consts;
import org.joml.Vector3f;

public class Zombie extends Entity {

    private static final float SPEED = 20.0f;
    private static final float TURN_SPEED = 100.0f;

    private float currentTurnSpeed = 0;
    private float currentSpeed = 0;

    public Zombie(Model model, Vector3f pos, Vector3f rotation, float scale) {
        super(model, pos, rotation, scale);
    }

    public void update(Keyboard keyboard, Terrain terrain, Player player) {
        if (this.getPosition() != player.getPosition()) {
            currentSpeed = SPEED;
        }
        super.incRotation(0, currentTurnSpeed * EngineManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * EngineManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotation().y)));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotation().y)));
        super.incPos(dx, 0, dz);
        float terrainHeight = terrain.getTerrainHeight(super.getPosition().x, super.getPosition().z);
        if (super.getPosition().y < terrainHeight + 1) { //quick fix
            super.getPosition().y = terrainHeight + 1;
        }
    }
}
