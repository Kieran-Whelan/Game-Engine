package me.frogdog.engine.game;

import me.frogdog.engine.core.HudManager;
import me.frogdog.engine.core.SceneManager;
import me.frogdog.engine.core.audio.Sound;
import me.frogdog.engine.core.maths.MousePicker;
import me.frogdog.engine.core.maths.Random;
import me.frogdog.engine.core.rendering.hud.gui.Item;
import me.frogdog.engine.core.rendering.hud.gui.items.Button;
import me.frogdog.engine.core.rendering.hud.gui.items.font.Font;
import me.frogdog.engine.core.rendering.hud.gui.items.font.Glyph;
import me.frogdog.engine.core.rendering.hud.gui.items.font.text.Text;
import me.frogdog.engine.core.world.*;
import me.frogdog.engine.core.world.entity.Entity;
import me.frogdog.engine.core.world.entity.player.Player;
import me.frogdog.engine.core.world.particle.Particle;
import me.frogdog.engine.core.world.particle.ParticleEffect;
import me.frogdog.engine.core.world.skybox.Skybox;
import me.frogdog.engine.core.world.terrain.BlendMapTerrain;
import me.frogdog.engine.core.world.terrain.HeightGenerator;
import me.frogdog.engine.core.world.terrain.Terrain;
import me.frogdog.engine.core.world.terrain.TerrainTexture;
import me.frogdog.engine.core.input.Keyboard;
import me.frogdog.engine.core.input.Mouse;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.rendering.RenderManager;
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
    private SceneManager scene;
    private HudManager hud;
    private Camera camera;
    private Keyboard keyboard;
    private Sound sound;
    private Font font;
    private Player player;
    private Mouse mouse;
    private MousePicker picker;
    private ParticleEffect effect;
    private Skybox skybox;

    private float cameraSpeed = 0.5f;
    private boolean debugMode = false;
    private final String[] textureFiles = new String[] {"textures/skybox/day/right.png", "textures/skybox/day/left.png", "textures/skybox/day/top.png", "textures/skybox/day/bottom.png", "textures/skybox/day/back.png", "textures/skybox/day/front.png"};
    private final String[] nightTextureFiles = new String[] {"textures/skybox/night/nightRight.png", "textures/skybox/night/nightLeft.png", "textures/skybox/night/nightTop.png", "textures/skybox/night/nightBottom.png", "textures/skybox/night/nightBack.png", "textures/skybox/night/nightFront.png"};

    Vector3f cameraInc;

    public Game() {
        renderer = new RenderManager();
        keyboard = new Keyboard();
        mouse = new Mouse();
        loader = new ObjectLoader();
        effect = new ParticleEffect(50, 25, 0.3f, 4, 1);
        effect.randomizeRotation();
        effect.setLifeError(0.1f);
        effect.setSpeedError(0.4f);
        effect.setScaleError(0.8f);
        //cameraInc = new Vector3f(0, 0, 0);
        scene = new SceneManager(-90);
        hud = new HudManager();
    }

    @Override
    public void init() throws Exception {
        renderer.init();
        mouse.init();
        keyboard.init();
        font = new Font("font/Dubai.png");
        sound = new Sound("audio/gun-gunshot-01.wav");
        //camera.setPosition(0 ,0 ,0);

        //sound = new Sound("audio/unlock.wav");

        skybox = new Skybox(textureFiles, nightTextureFiles);
        scene.addSkybox(skybox);
        player = new Player(new Model((loader.loadOBLModel("/models/cube.obj")), new Texture(loader.loadTexture("textures/player.png"))), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), 1.0f);
        camera = new Camera(player);
        player.getModel().getMaterial().setDisableCulling(true);
        scene.addEntity(player);

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("textures/terrain.png"));
        TerrainTexture redTexture = new TerrainTexture(loader.loadTexture("textures/flowers.png"));
        TerrainTexture greenTexture = new TerrainTexture(loader.loadTexture("textures/stone.png"));
        TerrainTexture blueTexture = new TerrainTexture(loader.loadTexture("textures/dirt.png"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("textures/maps/blendmap.png"));

        BlendMapTerrain blendMapTerrain = new BlendMapTerrain(backgroundTexture, redTexture, greenTexture, blueTexture);

        //Terrain terrain = new Terrain(new Vector3f(-400, -1, -400), loader, new Material(new Vector4f(0.0f, 0.0f, 0.0f, 0.0f), 0.1f), blendMapTerrain, blendMap ,"textures/maps/heightmap.png");
        HeightGenerator heightGenerator = new HeightGenerator();
        Terrain terrain = new Terrain(new Vector3f(-400, -1, -400), loader, new Material(new Vector4f(0.0f, 0.0f, 0.0f, 0.0f), 0.1f), blendMapTerrain, blendMap , heightGenerator);
        scene.addTerrain(terrain);

        picker = new MousePicker(mouse, camera, Main.getWindow().updateProjectionMatrix(), terrain);

        for(int i = 0; i < 200; i++) {
            float x = Random.randRange(-400f, 400f);
            float z = Random.randRange(-400f, 400f);
            Entity entity = new Entity(new Model((loader.loadOBLModel("/models/player.obj")), new Texture(loader.loadTexture("textures/player.png"))), new Vector3f(x, terrain.getTerrainHeight(x, z), z) , new Vector3f(0.0f, 0.0f, 0.0f), 1.0f);
            scene.addEntity(entity);
        }

        Entity cyl = new Entity(new Model((loader.loadOBLModel("/models/player.obj")), new Texture(loader.loadTexture("textures/player.png"))), new Vector3f(0, 0, 0) , new Vector3f(0.0f, 0.0f, 0.0f), 1.0f);
        scene.addEntity(cyl);

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
        scene.setDirectionalLight(new DirectionalLight(lightColour, lightPosition, lightIntensity));

        scene.setPointLights(new PointLight[] {pointLight});
        scene.setSpotLights(new SpotLight[] {spotLight, spotLight1});
    }

    @Override
    public void input() {
        mouse.input();

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_F3)) {
            debugMode = !debugMode;
        }

        if (keyboard.isKeyDown(GLFW.GLFW_KEY_P)) {
            scene.addParticle(new Particle(new Vector3f(player.getPosition().x, player.getPosition().y, player.getPosition().z), new Vector3f(0, 30, 0), 1, 4, 0, 1));
        }
    }

    @Override
    public void update(float interval) {
        if (debugMode) {
            hud.addText(new Text(font, "Frog Engine Dev 0.1", -0.975f, 0.965f));
            hud.addText(new Text(font, "Player XYZ: " + (int) player.getPosition().x + " " + (int) player.getPosition().y + " " + (int) player.getPosition().z, -0.975f, 0.915f));
            hud.addText(new Text(font, "OpenGL version 3.3", -0.975f, 0.725f));
        }

        camera.update(mouse);
        player.update(keyboard, scene.getTerrains().get(0));
        picker.update();
        effect.generateParticles(scene, new Vector3f(player.getPosition()));

        Vector3f terrainPoint = picker.getCurrentTerrainPoint();

        if (terrainPoint != null) {
            Entity cyl = scene.getEntities().get(scene.getEntities().size() - 1);
            cyl.setPos(terrainPoint.x, terrainPoint.y, terrainPoint.z);
        }

        //sound.play();

        if (mouse.isRightButtonPress()) {
            Vector2f rotVec = mouse.getDisplVec();
            player.incRotation(0, rotVec.x * Consts.MOUSE_SENSITIVITY, 0);
        }

        //entity.incRotation(0.0f, 0.25f, 0.0f);

        scene.incSpotAngle(0.15f);
        if (scene.getSpotAngle() > 9600) {
            scene.setSpotInc(-1);
        } else if (scene.getSpotAngle() <= -9600) {
            scene.setSpotAngle(-1);
        }

        double spotAngleRad = Math.toRadians(scene.getSpotAngle());
        Vector3f coneDir = scene.getSpotLights()[0].getPointLight().getPosition();
        coneDir.x = (float) Math.sin(spotAngleRad);

        coneDir = scene.getSpotLights()[1].getPointLight().getPosition();
        coneDir.x = (float) Math.cos(spotAngleRad * 0.15);

        scene.incLightAngle(0.1f);
        if (scene.getLightAngle() > 90) {
            scene.getDirectionalLight().setIntensity(0);
            if (scene.getLightAngle() >= 360) {
                scene.setLightAngle(-90);
            }
        } else if (scene.getLightAngle() <= -80 || scene.getLightAngle() >= 80) {
            float factor = 1 - (Math.abs(scene.getLightAngle()) - 80) / 10.0f;
            scene.getDirectionalLight().setIntensity(factor);
            scene.getDirectionalLight().getColour().y = Math.max(factor, 0.9f);
            scene.getDirectionalLight().getColour().z = Math.max(factor, 0.5f);
        } else {
            scene.getDirectionalLight().setIntensity(1);
            scene.getDirectionalLight().getColour().x = 1;
            scene.getDirectionalLight().getColour().y = 1;
            scene.getDirectionalLight().getColour().z = 1;
        }

        double angRad = Math.toRadians(scene.getLightAngle());
        scene.getDirectionalLight().getDirection().x = (float) Math.sin(angRad);
        scene.getDirectionalLight().getDirection().y = (float) Math.cos(angRad);

        for (Terrain terrain : scene.getTerrains()) {
            renderer.processTerrain(terrain);
        }

        for (Entity entity : scene.getEntities()) {
            renderer.processEntity(entity);
        }

        for (Skybox skybox : scene.getSkybox()) {
            renderer.processSkybox(skybox);
        }

        for (Particle particle : scene.getParticles()) {
            renderer.processParticle(particle);
        }

        for (Text text : hud.getText()) {
            renderer.processText(text);
        }

        for (Item item : hud.getItems()) {
            renderer.processGuiItem(item);
        }
        hud.getText().clear();
    }

    @Override
    public void render() {
        renderer.render(camera, scene);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        loader.cleanup();
    }
}
