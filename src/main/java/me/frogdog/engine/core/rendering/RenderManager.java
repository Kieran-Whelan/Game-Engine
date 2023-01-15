package me.frogdog.engine.core.rendering;

import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.ShaderManager;
import me.frogdog.engine.core.WindowManager;
import me.frogdog.engine.core.rendering.hud.gui.Item;
import me.frogdog.engine.core.rendering.hud.gui.items.font.Glyph;
import me.frogdog.engine.core.rendering.hud.gui.items.font.text.Text;
import me.frogdog.engine.core.rendering.world.particle.Particle;
import me.frogdog.engine.core.rendering.world.skybox.Skybox;
import me.frogdog.engine.core.rendering.world.body.Body;
import me.frogdog.engine.core.rendering.world.Model;
import me.frogdog.engine.core.SceneManager;
import me.frogdog.engine.core.rendering.world.terrain.Terrain;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.core.rendering.hud.GuiRenderer;
import me.frogdog.engine.core.rendering.hud.FontRenderer;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.game.Main;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenderManager {

    private static RenderManager instance = null;
    private final WindowManager window;
    private EntityRenderer entityRenderer;
    private TerrainRenderer terrainRenderer;
    private SkyboxRenderer skyboxRenderer;
    private WaterRenderer waterRenderer;
    private ParticleRenderer particleRenderer;
    private GuiRenderer hudRenderer;
    private FontRenderer fontRenderer;

    private static boolean isCulling = false;

    private Map<Model, List<Body>> entities = new HashMap<>();

    public RenderManager() {
        instance = this;
        window = Main.getWindow();
    }

    public void init() throws Exception {
        entityRenderer = new EntityRenderer();
        terrainRenderer = new TerrainRenderer();
        skyboxRenderer = new SkyboxRenderer();
        waterRenderer = new WaterRenderer();
        particleRenderer = new ParticleRenderer();
        hudRenderer = new GuiRenderer();
        fontRenderer = new FontRenderer();
        entityRenderer.init();
        terrainRenderer.init();
        skyboxRenderer.init();
        waterRenderer.init();
        particleRenderer.init();
        hudRenderer.init();
        fontRenderer.init();
    }

    public static void renderLights(PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight, ShaderManager shader) {
        shader.setUniform("ambientLight", Consts.AMBIENT_LIGHT);
        shader.setUniform("specularPower", Consts.SPECULAR_POWER);
        int numLights = spotLights != null ? spotLights.length : 0;
        for (int i = 0; i < numLights; i++) {
            shader.setUniform("spotLights", spotLights[i], i);
        }

        numLights = pointLights != null ? pointLights.length : 0;
        for (int i = 0; i < numLights; i++) {
            shader.setUniform("pointLights", pointLights[i], i);
        }
        if (directionalLight != null) {
            shader.setUniform("directionalLight", directionalLight);
        }
    }

    public void render(Camera camera, SceneManager scene) {
        clear();

        if (window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(false);
        }

        terrainRenderer.render(camera, scene.getPointLights(), scene.getSpotLights(), scene.getDirectionalLight());
        entityRenderer.render(camera, scene.getPointLights(), scene.getSpotLights(), scene.getDirectionalLight());
        skyboxRenderer.render(camera, scene.getPointLights(), scene.getSpotLights(), scene.getDirectionalLight());
        waterRenderer.render(camera, scene.getPointLights(), scene.getSpotLights(), scene.getDirectionalLight());
        particleRenderer.render(camera, scene.getPointLights(), scene.getSpotLights(), scene.getDirectionalLight());
        hudRenderer.render(camera, scene.getPointLights(), scene.getSpotLights(), scene.getDirectionalLight());
        fontRenderer.render(camera, scene.getPointLights(), scene.getSpotLights(), scene.getDirectionalLight());

    }

    public static void enableCulling() {
        if (!isCulling) {
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glCullFace(GL11.GL_BACK);
            isCulling = true;
        }
    }

    public static void disableCulling() {
        if (isCulling) {
            GL11.glDisable(GL11.GL_CULL_FACE);
            isCulling = false;
        }
    }

    public void processEntity(Body entity) {
        List<Body> entitiesList = entityRenderer.getEntities().get(entity.getModel());
        if (entitiesList != null) {
            entitiesList.add(entity);
        } else {
            List<Body> newEntityList = new ArrayList<>();
            newEntityList.add(entity);
            entityRenderer.getEntities().put(entity.getModel(), newEntityList);
        }
    }

    public void processTerrain(Terrain terrain) {
        terrainRenderer.getTerrain().add(terrain);
    }

    public void processParticle(Particle particle) {
        List<Particle> list = particleRenderer.getParticles().get(particle.getParticleTexture());
        if (list == null) {
            list = new ArrayList<Particle>();
            particleRenderer.getParticles().put(particle.getParticleTexture(), list);
        }
        list.add(particle);
    }

    public void processSkybox(Skybox skybox) {
        skyboxRenderer.getSkyboxes().add(skybox);
    }

    public void processGuiItem(Item item) {
        hudRenderer.getGuiItems().add(item);
    }

    public void processText(Text text) {
        for (Glyph glyph : text.getText()) {
            fontRenderer.getGlyphs().add(glyph);
        }
    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(Consts.SKY_COLOUR.x, Consts.SKY_COLOUR.y, Consts.SKY_COLOUR.z, Consts.SKY_COLOUR.w);
    }

    public void cleanup() {
        entityRenderer.cleanup();
        terrainRenderer.cleanup();
        skyboxRenderer.cleanup();
        waterRenderer.cleanup();
        particleRenderer.cleanup();
        hudRenderer.cleanup();
        fontRenderer.cleanup();
    }

}
