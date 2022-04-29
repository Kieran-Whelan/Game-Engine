package me.frogdog.engine.core.rendering.hud;

import me.frogdog.engine.core.Camera;
import me.frogdog.engine.core.ShaderManager;
import me.frogdog.engine.core.Transformation;
import me.frogdog.engine.core.entity.Model;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.utils.ObjectLoader;
import me.frogdog.engine.utils.Utils;
import me.frogdog.engine.utils.interfaces.IRenderer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class HudRenderer implements IRenderer {

    private ShaderManager shader;
    private ObjectLoader loader;
    private final Model quad;
    private List<HudTexture> hudTextures;

    float[] vertices = new float[] {
            -1, 1, -1,
            -1, 1, 1,
            1, -1
    };

    public HudRenderer() throws Exception {
        loader = new ObjectLoader();
        shader = new ShaderManager();
        hudTextures = new ArrayList<>();
        quad = loader.loadModel(vertices);
    }

    @Override
    public void init() throws Exception {
        HudTexture test = new HudTexture(loader.loadTexture("textures/grass.png"), new Vector2f(0.0f, 0.0f), new Vector2f(0.25f, 0.25f));
        hudTextures.add(test);
        shader.createVertexShader(Utils.loadResource("/shaders/hud_vertex.glsl"));
        shader.createFragmentShader(Utils.loadResource("/shaders/hud_fragment.glsl"));
        shader.link();
        shader.createUniform("hudTexture");
        shader.createUniform("transformationMatrix");
    }

    @Override
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
        shader.bind();
        GL30.glBindVertexArray(quad.getId());
        GL20.glEnableVertexAttribArray(0);
        for (HudTexture hudTexture : hudTextures) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, hudTexture.getId());
            Matrix4f matrix = Transformation.createTransformationMatrix(hudTexture);
            shader.setUniform("transformationMatrix", matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }

        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.unbind();
    }

    @Override
    public void bind(Model model) {

    }

    @Override
    public void unbind() {

    }

    @Override
    public void prepare(Object o, Camera camera) {

    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }

    public void addTexture(HudTexture texture) {
        this.hudTextures.add(texture);
    }
}
