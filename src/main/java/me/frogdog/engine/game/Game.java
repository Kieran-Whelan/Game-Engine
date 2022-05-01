package me.frogdog.engine.game;

import me.frogdog.engine.core.SceneManager;
import me.frogdog.engine.core.audio.Sound;
import me.frogdog.engine.core.world.*;
import me.frogdog.engine.core.world.entity.Entity;
import me.frogdog.engine.core.world.entity.player.Player;
import me.frogdog.engine.core.world.terrain.BlendMapTerrain;
import me.frogdog.engine.core.world.terrain.Terrain;
import me.frogdog.engine.core.world.terrain.TerrainTexture;
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

public class Game implements ILoigc {

    private final RenderManager renderer;
    private final ObjectLoader loader;
    private SceneManager sceneManager;
    private Camera camera;
    private Keyboard keyboard;
    private Sound sound;
    private Text text;
    private Player player;

    private float cameraSpeed = 0.5f;
    private boolean debugMode = false;

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

        //sound = new Sound("audio/unlock.wav");

        player = new Player(camera, new Model((loader.loadOBLModel("/models/player.obj")), new Texture(loader.loadTexture("textures/player.png"))), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), 1.0f);
        player.getModel().getMaterial().setDisableCulling(true);
        sceneManager.addEntity(player);

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("textures/terrain.png"));
        TerrainTexture redTexture = new TerrainTexture(loader.loadTexture("textures/flowers.png"));
        TerrainTexture greenTexture = new TerrainTexture(loader.loadTexture("textures/stone.png"));
        TerrainTexture blueTexture = new TerrainTexture(loader.loadTexture("textures/dirt.png"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("textures/maps/blendmap.png"));

        BlendMapTerrain blendMapTerrain = new BlendMapTerrain(backgroundTexture, redTexture, greenTexture, blueTexture);

        Terrain terrain = new Terrain(new Vector3f(-200, -1, -200), loader, new Material(new Vector4f(0.0f, 0.0f, 0.0f, 0.0f), 0.1f), blendMapTerrain, blendMap, "textures/maps/heightmap.png");
        sceneManager.addTerrain(terrain);

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
        if (keyboard.isKeyDown(GLFW.GLFW_KEY_X)) {
            camera.getPosition().y -= cameraSpeed;
        }

        if (keyboard.isKeyDown(GLFW.GLFW_KEY_Z)) {
            camera.getPosition().y += cameraSpeed;
        }

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_F3)) {
            debugMode = !debugMode;
        }
    }

    @Override
    public void update(float interval, Mouse mouseManager) {
        //camera.movePosition(cameraInc.x * Consts.CAMERA_MOVE_SPEED, cameraInc.y * Consts.CAMERA_MOVE_SPEED, cameraInc.z * Consts.CAMERA_MOVE_SPEED);
        camera.movePosition(cameraInc.x * cameraSpeed, cameraInc.y * cameraSpeed, cameraInc.z * cameraSpeed);
        if (debugMode) {
            text.drawString("Frog Engine dev 0.01", new Vector2f(-0.975f, 0.965f), 8);
            text.drawString("XYZ: " + (int) camera.getPosition().x + " " + (int) camera.getPosition().y + " " + (int) camera.getPosition().z, new Vector2f(-0.975f, 0.925f), 8);
            text.drawString("OpenGL version 3.3", new Vector2f(-0.975f, 0.885f), 8);

        }


        player.update(keyboard);

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
