package me.frogdog.engine.game;

import me.frogdog.engine.core.HudManager;
import me.frogdog.engine.core.SceneManager;
import me.frogdog.engine.core.audio.Sound;
import me.frogdog.engine.core.maths.Maths;
import me.frogdog.engine.core.input.mouse.MousePicker;
import me.frogdog.engine.core.rendering.hud.gui.Item;
import me.frogdog.engine.core.rendering.hud.gui.items.GuiTexture;
import me.frogdog.engine.core.rendering.hud.gui.items.buttons.Button;
import me.frogdog.engine.core.rendering.hud.gui.items.font.Font;
import me.frogdog.engine.core.rendering.hud.gui.items.font.text.Text;
import me.frogdog.engine.core.world.*;
import me.frogdog.engine.core.world.entity.Entity;
import me.frogdog.engine.core.world.entity.mobs.Zombie;
import me.frogdog.engine.core.world.entity.player.Player;
import me.frogdog.engine.core.world.entity.projectile.Bullet;
import me.frogdog.engine.core.world.particle.Particle;
import me.frogdog.engine.core.world.particle.ParticleEffect;
import me.frogdog.engine.core.world.particle.ParticleTexture;
import me.frogdog.engine.core.world.skybox.Skybox;
import me.frogdog.engine.core.world.terrain.BlendMapTerrain;
import me.frogdog.engine.core.world.terrain.HeightGenerator;
import me.frogdog.engine.core.world.terrain.Terrain;
import me.frogdog.engine.core.world.terrain.TerrainTexture;
import me.frogdog.engine.core.input.keyboard.Keyboard;
import me.frogdog.engine.core.input.mouse.Mouse;
import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.rendering.RenderManager;
import me.frogdog.engine.utils.loader.ObjectLoader;
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
    private Zombie conorBrady;
    ParticleTexture particleTexture;
    GuiTexture cursor;
    private Model bullet;

    private int mode = 0;
    private boolean debugMode;
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

        conorBrady = new Zombie(new Model((loader.loadOBJModel("/models/player.obj")), new Texture(loader.loadTexture("textures/zombie.png"))), new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0.0f, 0.0f, 0.0f), 1.0f);
        conorBrady.getModel().getMaterial().setDisableCulling(true);
        scene.addEntity(conorBrady);

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
            float x = Maths.randRange(-400f, 400f);
            float z = Maths.randRange(-400f, 400f);
            if (terrain.getTerrainHeight(x, z) >= -18) {
                Entity entity = new Entity(new Model((loader.loadOBJModel("/models/tree.obj")), new Texture(loader.loadTexture("textures/tree.png"))), new Vector3f(x, terrain.getTerrainHeight(x, z) - 2, z), new Vector3f(0.0f, 0.0f, 0.0f), 3.0f);
                scene.addEntity(entity);
            }
        }

        cursor = new GuiTexture(loader.loadTextureSheet("textures/png/cursor-pointer-1.png"), mouse.getHudPos().x, mouse.getHudPos().y, 0.015f, 0.015f);
        bullet = new Model((loader.loadOBJModel("/models/cube.obj")), new Texture(loader.loadTexture("textures/cube.png")));
    }

    @Override
    public void input() {
        mouse.input();

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_ESCAPE)) {
            if (mode == 2) {
                mode = 1;
            } else if (mode == 1) {
                mode = 2;
            }
        }

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_F3)) {
            debugMode = !debugMode;
        }

        if (keyboard.isKeyPressed(GLFW.GLFW_KEY_E)) {
            shoot();
        }

        if (keyboard.isKeyDown(GLFW.GLFW_KEY_P)) {
            scene.addParticle(new Particle(particleTexture , new Vector3f(player.getPosition().x, player.getPosition().y, player.getPosition().z), new Vector3f(0, 30, 0), 1, 4, 0, 1));
        }

        keyboard.clearKeys();
    }

    @Override
    public void update(float interval) {
        Button playButton = new Button(0, 0, 0.2f, 0.05f, "Start game");
        Button quitButton = new Button(0, -0.2f, 0.2f, 0.05f, "Quit");
        Button resume = new Button(0, 0, 0.2f, 0.05f, "Resume");
        Button mainMenu = new Button(0, -0.2f, 0.2f, 0.05f, "Main menu");

        if (isClicked(playButton) && mode == 0) {
            mode = 1;
        }

        if (isClicked(quitButton) && mode == 0) {
            GLFW.glfwSetWindowShouldClose(Main.getWindow().getWindow(), true);
        }

        if (isClicked(resume) && mode == 2) {
            mode = 1;
        }

        if (isClicked(mainMenu) && mode == 2) {
            mode = 0;
        }

        mouse.clear();

        switch (mode) {
            case 0:
                hud.addItem(playButton);
                hud.addItem(quitButton);
                hud.drawText("Zombified", 0 - hud.getTextWidth("Zombified", 4) / 2, 0.6f, 4);
                camera.getPosition().x = 0.0f;
                camera.getPosition().y = (scene.getTerrains().get(0).getTerrainHeight(0, 0) + 20.0f);
                camera.getPosition().z = 0.0f;
                camera.setYaw(camera.getYaw() + 0.025f);
                break;
            case 1:
                camera.update(mouse);
                player.update(keyboard, scene.getTerrains().get(0));
                conorBrady.update(scene.getTerrains().get(0), player);
                picker.update();
                effect.generateParticles(scene, new Vector3f(player.getPosition()));
                for (Entity entity : scene.getEntities()) {
                    if (entity instanceof Bullet) {
                        ((Bullet) entity).update();
                    }
                }
                break;
            case 2:
                hud.addItem(mainMenu);
                hud.addItem(resume);
                hud.drawText("Paused", 0 - hud.getTextWidth("Paused", 4) / 2, 0.6f, 4);
                break;
        }

        if (debugMode) {
            hud.drawText("Frog Engine Dev 0.1", -0.975f, 0.965f);
            hud.drawText("Player XYZ: " + (int) player.getPosition().x + " " + (int) player.getPosition().y + " " + (int) player.getPosition().z, -0.975f, 0.915f);
            hud.drawText("OpenGL version 3.3", -0.975f, 0.865f);
        }

        cursor.getPosition().x = mouse.getHudPos().x;
        cursor.getPosition().y = mouse.getHudPos().y;

        hud.addItem(cursor);

        if (Maths.isPointInsideAABB(new Vector3f(0, scene.getTerrains().get(0).getTerrainHeight(0, 0) + 1.25f, 0), player)) {
            System.out.println("t");
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

    private boolean isClicked(Item item) {
        if (Maths.isCollide2D(cursor, item)) {
            if (mouse.isLeftButtonUp()) {
                return true;
            }
        }
        return false;
    }

    private void shoot() {
        scene.addEntity(new Bullet(bullet, new Vector3f(player.getPosition().x, player.getPosition().y, player.getPosition().z), new Vector3f(player.getRotation().x, player.getRotation().y, player.getRotation().z), 0.5f));
    }
}
