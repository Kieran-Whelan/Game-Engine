package me.frogdog.engine.game;

import me.frogdog.engine.core.audio.Sound;
import me.frogdog.engine.core.entity.*;
import me.frogdog.engine.core.entity.terrain.BlendMapTerrain;
import me.frogdog.engine.core.entity.terrain.Terrain;
import me.frogdog.engine.core.entity.terrain.TerrainTexture;
import me.frogdog.engine.core.input.Keyboard;
import me.frogdog.engine.core.input.Mouse;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.rendering.RenderManager;
import me.frogdog.engine.core.rendering.hud.font.text.Text;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.utils.ObjectLoader;
import me.frogdog.engine.utils.interfaces.ILoigc;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

import java.util.Random;

public class Game implements ILoigc {

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private SceneManager sceneManager;
    private Camera camera;
    private Keyboard keyboard;
    private Sound sound;
    private Text text;

    private float cameraSpeed = 0.5f;

    Vector3f cameraInc;

    public Game() {
        renderer = new RenderManager();
        keyboard = new Keyboard();
        loader = new ObjectLoader();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);
        sceneManager = new SceneManager(-90);
    }

    @Override
    public void init() throws Exception {
        renderer.init();
        text = new Text("font/Dubai.png");
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
                0, 1, 3,
                3, 1, 2,
                8, 10, 11,
                9, 8, 11,
                12, 13, 7,
                5, 12, 7,
                14, 15, 6,
                4, 14, 6,
                16, 18, 19,
                17, 16, 19,
                4, 6, 7,
                5, 4, 7
        };

        Model model = loader.loadModel(vertices, textCoords, indices);
        //Model model = loader.loadOBLModel("/models/bunny.obj");
        model.setMaterial(new Material(new Texture(loader.loadTexture("textures/grass.png")), 1.0f));
        model.getMaterial().setDisableCulling(true);

        Random r = new Random();

        for (int i = 0; i < 2000; i++) {
            float x = r.nextFloat(-400, 400);
            float z = r.nextFloat() * -400;
            sceneManager.addEntity(new Entity(model, new Vector3f(x, 2, z), new Vector3f(0, 0, 0), 1));
        }
        sceneManager.addEntity(new Entity(model, new Vector3f(0, 2, 5), new Vector3f(0, 0, 0), 1));

        //sound = new Sound("audio/unlock.wav");

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("textures/terrain.png"));
        TerrainTexture redTexture = new TerrainTexture(loader.loadTexture("textures/flowers.png"));
        TerrainTexture greenTexture = new TerrainTexture(loader.loadTexture("textures/stone.png"));
        TerrainTexture blueTexture = new TerrainTexture(loader.loadTexture("textures/dirt.png"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("textures/blendMap.png"));

        BlendMapTerrain blendMapTerrain = new BlendMapTerrain(backgroundTexture, redTexture, greenTexture, blueTexture);

        Terrain terrain = new Terrain(new Vector3f(-400, -1, -400), loader, new Material(new Vector4f(0.0f, 0.0f, 0.0f, 0.0f), 0.1f), blendMapTerrain, blendMap);
        Terrain terrain1 = new Terrain(new Vector3f(0, -1, -400), loader, new Material(new Vector4f(0.0f, 0.0f, 0.0f, 0.0f), 0.1f), blendMapTerrain, blendMap);
        //Terrain terrain1 = new Terrain(new Vector3f(0, -1, -400), loader, new Material(new Texture(loader.loadTexture("textures/terrain.png")), 0.1f));
        sceneManager.addTerrain(terrain);
        sceneManager.addTerrain(terrain1);

        float lightIntensity = 1.0f;
        //point light
        Vector3f lightPosition = new Vector3f(-0.5f, -0.5f, -3.2f);
        Vector3f lightColour =  new Vector3f(1, 1, 1);
        PointLight pointLight = new PointLight(lightColour, lightPosition, lightIntensity, 0, 0, 1);

        //spot light
        Vector3f coneDir = new Vector3f(0, -5, 0);
        float cutoff = (float) Math.cos(Math.toRadians(100));
        lightIntensity = 5f;
        SpotLight spotLight = new SpotLight(new PointLight(new Vector3f(0.25f, 0, 0), new Vector3f(0f, 4f, 5f), lightIntensity, 0, 0, 0.2f), coneDir, cutoff);

        SpotLight spotLight1 = new SpotLight(new PointLight(new Vector3f(0, 0.25f, 0), new Vector3f(0f, 4f, 5f), lightIntensity, 0, 0, 0.2f), coneDir, cutoff);

        //directional light
        lightIntensity = 1f;
        lightPosition = new Vector3f(0, 10, 0);
        lightColour =  new Vector3f(1, 1, 1);
        sceneManager.setDirectionalLight(new DirectionalLight(lightColour, lightPosition, lightIntensity));

        sceneManager.setPointLights(new PointLight[] {pointLight});
        sceneManager.setSpotLights(new SpotLight[] {spotLight, spotLight1});
    }

    @Override
    public void input() {
        cameraInc.set(0, 0, 0);
        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_W)) {
            cameraInc.z = -1;
        }

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_S)) {
            cameraInc.z = 1;
        }

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_A)) {
            cameraInc.x = -1;
        }

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_D)) {
            cameraInc.x = 1;
        }

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            cameraSpeed = 5.0f;
        }

        if (keyboard.isKeyReleased(GLFW.GLFW_KEY_LEFT_SHIFT)) {
            cameraSpeed = 0.5f;
        }

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_Z)) {
            cameraInc.y = -1;
        }

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_X)) {
            cameraInc.y = 1;
        }

        float lightPos = sceneManager.getSpotLights()[0].getPointLight().getPosition().z;
        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_N)) {
            sceneManager.getSpotLights()[0].getPointLight().getPosition().z = lightPos + 0.1f;
        }

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_M)) {
            sceneManager.getSpotLights()[0].getPointLight().getPosition().z = lightPos - 0.1f;
        }
    }

    @Override
    public void update(float interval, Mouse mouseManager) {
        //camera.movePosition(cameraInc.x * Consts.CAMERA_MOVE_SPEED, cameraInc.y * Consts.CAMERA_MOVE_SPEED, cameraInc.z * Consts.CAMERA_MOVE_SPEED);
        camera.movePosition(cameraInc.x * cameraSpeed, cameraInc.y * cameraSpeed, cameraInc.z * cameraSpeed);
        text.drawString("XYZ: " + (int) camera.getPosition().x + " " + (int) camera.getPosition().y + " " + (int) camera.getPosition().z, new Vector2f(-0.8f, 0.8f), 6);

        //sound.play();

        if (mouseManager.isRightButtonPress()) {
            Vector2f rotVec = mouseManager.getDisplVec();
            camera.moveRotation(rotVec.x * Consts.MOUSE_SENSITIVITY, rotVec.y * Consts.MOUSE_SENSITIVITY, 0);
        }

        //entity.incRotation(0.0f, 0.25f, 0.0f);

        sceneManager.incSpotAngle(0.15f);
        if (sceneManager.getSpotAngle() > 9600) {
            sceneManager.setSpotInc(-1);
        } else if (sceneManager.getSpotAngle() <= -9600) {
            sceneManager.setSpotAngle(-1);
        }

        double spotAngleRad = Math.toRadians(sceneManager.getSpotAngle());
        Vector3f coneDir = sceneManager.getSpotLights()[0].getPointLight().getPosition();
        coneDir.x = (float) Math.sin(spotAngleRad);

        coneDir = sceneManager.getSpotLights()[1].getPointLight().getPosition();
        coneDir.x = (float) Math.cos(spotAngleRad * 0.15);

        sceneManager.incLightAngle(0.1f);
        if (sceneManager.getLightAngle() > 90) {
            sceneManager.getDirectionalLight().setIntensity(0);
            if (sceneManager.getLightAngle() >= 360) {
                sceneManager.setLightAngle(-90);
            }
        } else if (sceneManager.getLightAngle() <= -80 || sceneManager.getLightAngle() >= 80) {
            float factor = 1 - (Math.abs(sceneManager.getLightAngle()) - 80) / 10.0f;
            sceneManager.getDirectionalLight().setIntensity(factor);
            sceneManager.getDirectionalLight().getColour().y = Math.max(factor, 0.9f);
            sceneManager.getDirectionalLight().getColour().z = Math.max(factor, 0.5f);
        } else {
            sceneManager.getDirectionalLight().setIntensity(1);
            sceneManager.getDirectionalLight().getColour().x = 1;
            sceneManager.getDirectionalLight().getColour().y = 1;
            sceneManager.getDirectionalLight().getColour().z = 1;
        }

        double angRad = Math.toRadians(sceneManager.getLightAngle());
        sceneManager.getDirectionalLight().getDirection().x = (float) Math.sin(angRad);
        sceneManager.getDirectionalLight().getDirection().y = (float) Math.cos(angRad);

        for (Terrain terrain : sceneManager.getTerrains()) {
            renderer.processTerrain(terrain);
        }

        for (Entity entity : sceneManager.getEntities()) {
            renderer.processEntity(entity);
        }
    }

    @Override
    public void render() {
        renderer.render(camera, sceneManager);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }
}
