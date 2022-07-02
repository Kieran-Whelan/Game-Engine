package me.frogdog.engine.core.rendering.hud;

import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.ShaderManager;
import me.frogdog.engine.core.maths.Transformation;
import me.frogdog.engine.core.rendering.hud.gui.Item;
import me.frogdog.engine.core.rendering.hud.gui.items.GuiTexture;
import me.frogdog.engine.core.rendering.world.Model;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.utils.loader.ObjectLoader;
import me.frogdog.engine.utils.Utils;
import me.frogdog.engine.utils.interfaces.IRenderer;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class GuiRenderer implements IRenderer {

    private ShaderManager shader;
    private ObjectLoader loader;
    private final Model quad;
    private List<Item> guiItems;

    public GuiRenderer() throws Exception {
        loader = new ObjectLoader();
        shader = new ShaderManager();
        guiItems = new ArrayList<>();
        quad = loader.loadModel(Consts.HUD_VERTICES);
    }

    @Override
    public void init() throws Exception {
        shader.createVertexShader(Utils.loadResource("/shaders/hud_vertex.glsl"));
        shader.createFragmentShader(Utils.loadResource("/shaders/hud_fragment.glsl"));
        shader.link();
        shader.createUniform("hudTexture");
        shader.createUniform("hasTexture");
        shader.createUniform("transformationMatrix");
        //guiItems.add(new Button(new Vector2f(0.0f, 0.0f), new Vector2f(0.25f, 0.1f)));
    }

    @Override
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
        shader.bind();
        for (Item item : guiItems) {
            bind(quad);
            prepare(item, camera);
            if (item instanceof GuiTexture) {
                shader.setUniform("hasTexture", true);
                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((GuiTexture)item).getId());
            } else {
                shader.setUniform("hasTexture", false);
            }
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
            unbind();
        }
        guiItems.clear();
        shader.unbind();
    }

    @Override
    public void bind(Model model) {
        GL30.glBindVertexArray(quad.getId());
        GL20.glEnableVertexAttribArray(0);
    }

    @Override
    public void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void prepare(Object item, Camera camera) {
        Matrix4f matrix = Transformation.createTransformationMatrix((Item) item);
        shader.setUniform("transformationMatrix", matrix);
    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }

    public List<Item> getGuiItems() {
        return guiItems;
    }
}
