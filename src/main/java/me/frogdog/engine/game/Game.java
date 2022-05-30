package me.frogdog.engine.game;

import me.frogdog.engine.core.HudManager;
import me.frogdog.engine.core.SceneManager;
import me.frogdog.engine.core.audio.Sound;
import me.frogdog.engine.core.maths.Maths;
import me.frogdog.engine.core.maths.MousePicker;
import me.frogdog.engine.core.maths.Random;
import me.frogdog.engine.core.rendering.hud.gui.Item;
import me.frogdog.engine.core.rendering.hud.gui.items.Button;
import me.frogdog.engine.core.rendering.hud.gui.items.GuiTexture;
import me.frogdog.engine.core.rendering.hud.gui.items.font.Font;
import me.frogdog.engine.core.rendering.hud.gui.items.font.text.Text;
import me.frogdog.engine.core.world.*;
import me.frogdog.engine.core.world.entity.Entity;
import me.frogdog.engine.core.world.entity.player.Player;
import me.frogdog.engine.core.world.particle.Particle;
import me.frogdog.engine.core.world.particle.ParticleEffect;
import me.frogdog.engine.core.world.particle.ParticleTexture;
import me.frogdog.engine.core.world.skybox.Skybox;
import me.frogdog.engine.core.world.terrain.BlendMapTerrain;
import me.frogdog.engine.core.world.terrain.HeightGenerator;
import me.frogdog.engine.core.world.terrain.Terrain;
import me.frogdog.engine.core.world.terrain.TerrainTexture;
import me.frogdog.engine.core.input.Keyboard;
import me.frogdog.engine.core.input.Mouse;
import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.rendering.RenderManager;
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
    private Player player;
    private Mouse mouse;
    private MousePicker picker;
    private ParticleEffect effect;
    private Skybox skybox;
    ParticleTexture particleTexture;

    private boolean debugMode = false;
    private final String[] textureFiles = new String[] {"textures/skybox/day/right.png", "textures/skybox/day/left.png", "textures/skybox/day/top.png", "textures/skybox/day/bottom.png", "textures/skybox/day/back.png", "textures/skybox/day/front.png"};
    private final String[] nightTextureFiles = new String[] {"textures/skybox/night/nightRight.png", "textures/skybox/night/nightLeft.png", "textures/skybox/night/nightTop.png", "textures/skybox/night/nightBottom.png", "textures/skybox/night/nightBack.png", "textures/skybox/night/nightFront.png"};

    public Game() {
        renderer = new RenderManager();
        keyboard = new Keyboard();
        mouse = new Mouse();
        loader = new ObjectLoader();
        scene = new SceneManager(-90);
    }

    @Override
    public void init() throws Exception {
        renderer.init();
        mouse.init();
        keyboard.init();
        hud = new HudManager(new Font("font/Dubai.png"));
        sound = new Sound("audio/gun-gunshot-01.wav");

        particleTexture = new ParticleTexture(loader.loadTexture("textures/particleStar.png"), new Vector2f(0.0f, 0.0f), new Vector2f(0.0f, 0.0f), new Vector2f(0.0f, 0.0f));
        effect = new ParticleEffect(particleTexture, 40, 25, 0.3f, 4, 1);
        effect.randomizeRotation();
        effect.setLifeError(0.1f);
        effect.setSpeedError(0.4f);
        effect.setScaleError(0.8f);

        skybox = new Skybox(textureFiles, nightTextureFiles);
        scene.addSkybox(skybox);
        player = new Player(new Model((loader.loadOBJModel("/models/player.obj")), new Texture(loader.loadTexture("textures/player.png"))), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), 1.0f);
        camera = new Camera(player);
        player.getModel().getMaterial().setDisableCulling(true);
        scene.addEntity(player);

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("textures/terrain.png"));
        TerrainTexture redTexture = new TerrainTexture(loader.loadTexture("textures/flowers.png"));
        TerrainTexture greenTexture = new TerrainTexture(loader.loadTexture("textures/stone.png"));
        TerrainTexture blueTexture = new TerrainTexture(loader.loadTexture("textures/dirt.png"));
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("textures/maps/blendmap.png"));

        BlendMapTerrain blendMapTerrain = new BlendMapTerrain(backgroundTexture, redTexture, greenTexture, blueTexture);

        HeightGenerator heightGenerator = new HeightGenerator();
        Terrain terrain = new Terrain(new Vector3f(-400, -1, -400), loader, new Material(new Vector4f(0.0f, 0.0f, 0.0f, 0.0f), 0.1f), blendMapTerrain, blendMap , heightGenerator);
        scene.addTerrain(terrain);

        picker = new MousePicker(mouse, camera, Main.getWindow().updateProjectionMatrix(), terrain);

        for (int i = 0; i < 200; i++) {
            float x = Random.randRange(-400f, 400f);
            float z = Random.randRange(-400f, 400f);
            if (terrain.getTerrainHeight(x, z) >= -18) {
                Entity entity = new Entity(new Model((loader.loadOBJModel("/models/tree.obj")), new Texture(loader.loadTexture("textures/tree.png"))), new Vector3f(x, terrain.getTerrainHeight(x, z) - 2, z), new Vector3f(0.0f, 0.0f, 0.0f), 3.0f);
                scene.addEntity(entity);
            }
        }
    }

    @Override
    public void input() {
        mouse.input();

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_F3)) {
            debugMode = !debugMode;
        }

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_F4)) {
            mouse.getHudPos().x = 0;
            mouse.getHudPos().y = 0;
        }

        if (keyboard.isKeyDown(GLFW.GLFW_KEY_P)) {
            scene.addParticle(new Particle(particleTexture , new Vector3f(player.getPosition().x, player.getPosition().y, player.getPosition().z), new Vector3f(0, 30, 0), 1, 4, 0, 1));
        }
    }

    @Override
    public void update(float interval) {
        if (debugMode) {
            hud.drawText("Frog Engine Dev 0.1", -0.975f, 0.965f);
            hud.drawText("Player XYZ: " + (int) player.getPosition().x + " " + (int) player.getPosition().y + " " + (int) player.getPosition().z, -0.975f, 0.915f);
            hud.drawText("OpenGL version 3.3", -0.975f, 0.865f);
        }

        camera.update(mouse);
        player.update(keyboard, scene.getTerrains().get(0));
        picker.update();
        effect.generateParticles(scene, new Vector3f(player.getPosition()));

        GuiTexture cursor = null;
        Button button = new Button(new Vector2f(0.0f, 0.0f), new Vector2f(0.2f, 0.2f), "heck");

        try {
            cursor = new GuiTexture(loader.loadTextureSheet("textures/png/cursor-pointer-1.png"), mouse.getHudPos(), new Vector2f(0.015f, 0.015f));
        } catch (Exception e) {
            e.printStackTrace();
        }

        hud.addItem(button);
        hud.addItem(cursor);

        assert cursor != null;
        if (Maths.isCollide2D(cursor, button)) {
            if (mouse.isLeftButtonPress()) {
                System.out.println("Button pressed");
            }
        }

        /*
        Vector3f terrainPoint = picker.getCurrentTerrainPoint();

        if (terrainPoint != null) {
            Entity cyl = scene.getEntities().get(scene.getEntities().size() - 1);
            cyl.setPos(terrainPoint.x, terrainPoint.y, terrainPoint.z);
        }
         */

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

        for (Item item : hud.getItems()) {
            if (item instanceof Button) {
                hud.drawText(((Button) item).getLabel(), (item.getPosition().x - hud.getTextWidth(((Button) item).getLabel())) / 2, item.getPosition().y);
            }
            renderer.processGuiItem(item);
        }

        for (Text text : hud.getText()) {
            renderer.processText(text);
        }

        hud.getText().clear();
        hud.getItems().clear();
        scene.getParticles().clear();
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
