package me.frogdog.engine.core.rendering.hud.font;

import me.frogdog.engine.core.Camera;
import me.frogdog.engine.core.ShaderManager;
import me.frogdog.engine.core.entity.Model;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.core.maths.Transformation;
import me.frogdog.engine.core.rendering.hud.HudTexture;
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

public class FontRenderer implements IRenderer {

    private ShaderManager shader;
    private ObjectLoader loader;
    private final Model quad;
    private List<HudTexture> text;

    float[] vertices = new float[] {
            -1, 1,
            -1, -1,
            1, 1,
            1, -1
    };

    public FontRenderer() throws Exception {
        shader = new ShaderManager();
        loader = new ObjectLoader();
        quad = loader.loadModel(vertices);
        text = new ArrayList<>();
    }

    @Override
    public void init() throws Exception {
        HudTexture test = new HudTexture(loader.loadTexture("font/Dubai.png"), new Vector2f(0.0f, 0.0f), new Vector2f(0.125f, 0.25f));
        text.add(test);
        shader.createVertexShader(Utils.loadResource("/shaders/font_vertex.glsl"));
        shader.createFragmentShader(Utils.loadResource("/shaders/font_fragment.glsl"));
        shader.link();
        shader.createUniform("hudTexture");
        shader.createUniform("transformationMatrix");
        shader.createUniform("sheetIndex");
    }

    @Override
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
        shader.bind();
        shader.setUniform("sheetIndex", new Vector2f(5.0f, 5.0f));
        GL30.glBindVertexArray(quad.getId());
        GL20.glEnableVertexAttribArray(0);
        for (HudTexture text : text) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, text.getId());
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Matrix4f matrix = Transformation.createTransformationMatrix(text);
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
}
