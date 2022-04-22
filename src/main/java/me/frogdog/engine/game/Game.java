package me.frogdog.engine.game;

import me.frogdog.engine.core.*;
import me.frogdog.engine.core.entity.*;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.core.rendering.RenderManager;
import me.frogdog.engine.utils.Consts;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game implements ILoigc {

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private final WindowManager window;

    private List<Entity> entities;
    private List<Terrain> terrains;

    private Camera camera;

    Vector3f cameraInc;

    private float lightAngle, spotAngle = 0, spotInc = 1;
    private DirectionalLight directionalLight;
    private PointLight[] pointLights;
    private SpotLight[] spotLights;

    public Game() {
        renderer = new RenderManager();
        window = Main.getWindow();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);
        lightAngle = -90;
    }

    @Override
    public void init() throws Exception {
        renderer.init();
        camera.setPosition(0 ,0 ,0);

        float[] vertices = new float[] {
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
        };

        float[] textCoords = new float[] {
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.0f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };

        int[] indices = new int[] {
                0, 1, 3, 3, 1, 2,
                8, 10, 11, 9, 8, 11,
                12, 13, 7, 5, 12, 7,
                14, 15, 6, 4, 14, 6,
                16, 18, 19, 17, 16, 19,
                4, 6, 7, 5, 4, 7,
        };

        Model model = loader.loadModel(vertices, textCoords, indices);
        //Model model = loader.loadOBLModel("/models/bunny.obj");
        model.setTexture(new Texture(loader.loadTexture("textures/grass.png")), 1.0f);

        terrains = new ArrayList<>();
        Terrain terrain = new Terrain(new Vector3f(0, 1, 0), loader, new Material(new Texture(loader.loadTexture("textures/terrain.png")), 0.1f));
        Terrain terrain1 = new Terrain(new Vector3f(0, 1, -400), loader, new Material(new Texture(loader.loadTexture("textures/grass.png")), 0.1f));
        terrains.add(terrain);
        terrains.add(terrain1);

        entities = new ArrayList<>();
        Random r = new Random();

        for (int i = 0; i < 2000; i++) {
            float x = r.nextFloat() * 800;
            float z = r.nextFloat() * -800;
            entities.add(new Entity(model, new Vector3f(x, 0, z), new Vector3f(0, 0, 0), 1));
        }
        entities.add(new Entity(model, new Vector3f(0, 0, -5), new Vector3f(0, 0, 0), 1));

        float lightIntensity = 1.0f;
        //point light
        Vector3f lightPosition = new Vector3f(0.0f, 0.0f, -3.2f);
        Vector3f lightColour =  new Vector3f(1, 1, 1);
        PointLight pointLight = new PointLight(lightColour, lightPosition, lightIntensity, 0, 0, 1);

        //spot light
        Vector3f coneDir = new Vector3f(0, 0, 1);
        float cutoff = (float) Math.cos(Math.toRadians(100));
        SpotLight spotLight = new SpotLight(new PointLight(lightColour, new Vector3f(0, 0, 1.0f), lightIntensity, 0, 0, 1), coneDir, cutoff);

        SpotLight spotLight1 = new SpotLight(pointLight, coneDir, cutoff);
        spotLight1.getPointLight().setPosition(new Vector3f(0.5f, 0.5f, -3.6f));

        //directional light
        lightIntensity = 0.0f;
        lightPosition = new Vector3f(-1, -10, 0);
        lightColour =  new Vector3f(1, 1, 1);
        directionalLight = new DirectionalLight(lightColour, lightPosition, lightIntensity);

        pointLights = new PointLight[] {pointLight};
        spotLights = new SpotLight[] {spotLight, spotLight1};
    }

    @Override
    public void input() {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW.GLFW_KEY_W)) {
            cameraInc.z = -1;
        }

        if (window.isKeyPressed(GLFW.GLFW_KEY_S)) {
            cameraInc.z = 1;
        }

        if (window.isKeyPressed(GLFW.GLFW_KEY_A)) {
            cameraInc.x = -1;
        }

        if (window.isKeyPressed(GLFW.GLFW_KEY_D)) {
            cameraInc.x = 1;
        }

        if (window.isKeyPressed(GLFW.GLFW_KEY_Z)) {
            cameraInc.y = -1;
        }

        if (window.isKeyPressed(GLFW.GLFW_KEY_X)) {
            cameraInc.y = 1;
        }

        float lightPos = spotLights[0].getPointLight().getPosition().z;

        float lightPos1 = spotLights[1].getPointLight().getPosition().z;

        if (window.isKeyPressed(GLFW.GLFW_KEY_N)) {
            spotLights[0].getPointLight().getPosition().z = lightPos + 0.1f;
        }

        if (window.isKeyPressed(GLFW.GLFW_KEY_M)) {
            spotLights[0].getPointLight().getPosition().z = lightPos - 0.1f;
        }

        if (window.isKeyPressed(GLFW.GLFW_KEY_O)) {
            spotLights[1].getPointLight().getPosition().z = lightPos1 + 0.1f;
        }

        if (window.isKeyPressed(GLFW.GLFW_KEY_P)) {
            spotLights[1].getPointLight().getPosition().z = lightPos1 - 0.1f;
        }

        if (window.isKeyPressed(GLFW.GLFW_KEY_L)) {
            pointLights[0].getPosition().x += 0.1f;
        }

        if (window.isKeyPressed(GLFW.GLFW_KEY_K)) {
            pointLights[0].getPosition().x -= 0.1f;
        }
    }

    @Override
    public void update(float interval, MouseManager mouseManager) {
        camera.movePosition(cameraInc.x * Consts.CAMERA_MOVE_SPEED, cameraInc.y * Consts.CAMERA_MOVE_SPEED, cameraInc.z * Consts.CAMERA_MOVE_SPEED);

        if (mouseManager.isRightButtonPress()) {
            Vector2f rotVec = mouseManager.getDisplVec();
            camera.moveRotation(rotVec.x * Consts.MOUSE_SENSITIVITY, rotVec.y * Consts.MOUSE_SENSITIVITY, 0);
        }

        //entity.incRotation(0.0f, 0.25f, 0.0f);
        spotAngle +=  spotInc * 0.05f;
        if (spotAngle > 4) {
            spotInc = -1;
        } else if (spotAngle <= 4) {
            spotInc = 1;
        }

        double spotAngleRad = Math.toRadians(spotAngle);
        Vector3f coneDir = spotLights[0].getPointLight().getPosition();
        coneDir.y = (float) Math.sin(spotAngleRad);

        lightAngle += 0.05f;
        if (lightAngle > 90) {
            directionalLight.setIntensity(0);
            if (lightAngle >= 360) {
                lightAngle = -90;
            }
        } else if (lightAngle <= -80 || lightAngle >= 80) {
            float factor = 1 - (Math.abs(lightAngle) - 80) / 10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColour().y = Math.max(factor, 0.9f);
            directionalLight.getColour().z = Math.max(factor, 0.5f);
        } else {
            directionalLight.setIntensity(1);
            directionalLight.getColour().x = 1;
            directionalLight.getColour().y = 1;
            directionalLight.getColour().z = 1;
        }

        double angRad = Math.toRadians(lightAngle);
        directionalLight.getDirection().x = (float) Math.sin(angRad);
        directionalLight.getDirection().y = (float) Math.cos(angRad);

        for (Entity entity : entities) {
            renderer.processEntity(entity);
        }

        for (Terrain terrain : terrains) {
            renderer.processTerrain(terrain);
        }
    }

    @Override
    public void render() {
        renderer.render(camera, directionalLight, pointLights, spotLights);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }
}
