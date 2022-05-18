package me.frogdog.engine.utils;

import org.joml.Vector3f;
import org.joml.Vector4f;

public class Consts {

    public static final String TITLE = "Frog Engine";

    public static final float FOV = (float) Math.toRadians(60);
    public static final float Z_NEAR = 0.01f;
    public static final float Z_FAR = 1000.0f;

    public static final float MOUSE_SENSITIVITY = 0.2f;
    //public static final float CAMERA_MOVE_SPEED = 0.05f;

    public static final Vector4f DEFAULT_COLOUR = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
    //public static final Vector4f SKY_COLOUR = new Vector4f(0.6f, 0.78f, 0.76f, 1.0f);
    public static final Vector4f SKY_COLOUR = new Vector4f(0.3f, 0.44f, 0.44f, 1.0f);

    public static final Vector3f AMBIENT_LIGHT = new Vector3f(0.3f, 0.3f, 0.3f);
    public static final float SPECULAR_POWER = 10f;
    public static final int MAX_SPOT_LIGHTS = 5;
    public static final int MAX_POINT_LIGHTS = 5;

    public static final float[] HUD_VERTICES = {-1, 1, -1, -1, 1, 1, 1, -1};
    public static final float[] PARTICLE_VERTICES = {-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f};

    public static final float GRAVITY = -50.0f;

    //Skybox
    public static final float SIZE = 500f;
    public static final float[] VERTICES = {
            -SIZE,  SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            -SIZE,  SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE,  SIZE
    };

}
