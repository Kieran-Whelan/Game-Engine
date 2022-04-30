package me.frogdog.engine.core.rendering;

import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.ShaderManager;
import me.frogdog.engine.core.WindowManager;
import me.frogdog.engine.core.entity.Entity;
import me.frogdog.engine.core.entity.Model;
import me.frogdog.engine.core.entity.SceneManager;
import me.frogdog.engine.core.entity.terrain.Terrain;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.core.rendering.hud.HudRenderer;
import me.frogdog.engine.core.rendering.hud.font.FontRenderer;
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
    private HudRenderer hudRenderer;
    private FontRenderer fontRenderer;

    private static boolean isCulling = false;

    private Map<Model, List<Entity>> entities = new HashMap<>();

    public RenderManager() {
        instance = this;
        window = Main.getWindow();
    }

    public void init() throws Exception {
        entityRenderer = new EntityRenderer();
        terrainRenderer = new TerrainRenderer();
        hudRenderer = new HudRenderer();
        fontRenderer = new FontRenderer();
        entityRenderer.init();
        terrainRenderer.init();
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
        shader.setUniform("directionalLight", directionalLight);
    }

    public void render(Camera camera, SceneManager scene) {
        clear();

        if (window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(false);
        }

        terrainRenderer.render(camera, scene.getPointLights(), scene.getSpotLights(), scene.getDirectionalLight());
        entityRenderer.render(camera, scene.getPointLights(), scene.getSpotLights(), scene.getDirectionalLight());
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

    public void processEntity(Entity entity) {
        List<Entity> entitiesList = entityRenderer.getEntities().get(entity.getModel());
        if (entitiesList != null) {
            entitiesList.add(entity);
        } else {
            List<Entity> newEntityList = new ArrayList<>();
            newEntityList.add(entity);
            entityRenderer.getEntities().put(entity.getModel(), newEntityList);
        }
    }

    public void processTerrain(Terrain terrain) {
        terrainRenderer.getTerrain().add(terrain);
    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup() {
        entityRenderer.cleanup();
        terrainRenderer.cleanup();
        hudRenderer.cleanup();
        fontRenderer.cleanup();
    }

    public static RenderManager getInstance() {
        return instance;
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }
}
