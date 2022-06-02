package me.frogdog.engine.core.maths;

import me.frogdog.engine.core.input.mouse.Mouse;
import me.frogdog.engine.core.world.entity.player.Player;
import me.frogdog.engine.utils.Consts;
import org.joml.Vector3f;

public class Camera {

    private Player player;

    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;

    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch;
    private float yaw;
    private float roll;

    public Camera() {
        this.pitch = 20f;
        this.yaw = yaw;
        this.roll = roll;
    }

    public Camera(Player player) {
        this.pitch = 20f;
        this.yaw = yaw;
        this.roll = roll;
        this.player = player;
    }

    public void update(Mouse mouse) {
        calcZoom(mouse);
        calcPitch(mouse);
        calcAngleAroundPlayer(mouse);
        float horizontalDistance = calcHorizontalDistanceFromPlayer();
        float verticalDistance = calcVerticalDistanceFromPlayer();
        calcCameraPos(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotation().y  + angleAroundPlayer) * 1;
    }

    private void calcZoom(Mouse mouse) {
        float zoom = 0;
        if (mouse.isScrollUp()) {
            zoom = -0.01f;
        }

        if (mouse.isScrollDown()) {
            zoom = 0.01f;
        }

        distanceFromPlayer -= zoom;
    }

    private void calcPitch(Mouse mouse) {
        if (mouse.isRightButtonPress()) {
            float pitchChange = mouse.getDisplVec().x * Consts.MOUSE_SENSITIVITY;
            pitch -= pitchChange;
        }
    }

    private void calcAngleAroundPlayer(Mouse mouse) {
        if (mouse.isLeftButtonPress()) {
            float angleChange = mouse.getDisplVec().y * Consts.MOUSE_SENSITIVITY;
            angleAroundPlayer -= angleChange;
        }
    }

    private float calcHorizontalDistanceFromPlayer() {
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calcVerticalDistanceFromPlayer() {
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }

    private void calcCameraPos(float hd, float vd) {
        float angle = player.getRotation().y + angleAroundPlayer;
        float offsetX = (float) (hd * Math.sin(Math.toRadians(angle)));
        float offsetZ = (float) (hd * Math.cos(Math.toRadians(angle)));
        position.x = player.getPosition().x - offsetX;
        position.z = player.getPosition().z - offsetZ;
        position.y = player.getPosition().y + vd;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getDistanceFromPlayer() {
        return distanceFromPlayer;
    }

    public float getAngleAroundPlayer() {
        return angleAroundPlayer;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}
