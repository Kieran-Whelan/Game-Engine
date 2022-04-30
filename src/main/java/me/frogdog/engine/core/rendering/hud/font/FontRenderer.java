package me.frogdog.engine.core.rendering.hud.font;

import me.frogdog.engine.core.Camera;
import me.frogdog.engine.core.ShaderManager;
import me.frogdog.engine.core.entity.Model;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.core.maths.Transformation;
import me.frogdog.engine.core.rendering.hud.HudTexture;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.utils.ObjectLoader;
import me.frogdog.engine.utils.Utils;
import me.frogdog.engine.utils.interfaces.IRenderer;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
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
    private List<HudTexture> glyphList;

    public FontRenderer() throws Exception {
        shader = new ShaderManager();
        loader = new ObjectLoader();
        quad = loader.loadModel(Consts.HUD_VERTICES);
        glyphList = new ArrayList<>();
    }

    @Override
    public void init() throws Exception {
        Font font = new Font("font/Dubai.png");
        HudTexture test = new HudTexture(font.getFontSheet(), new Vector2f(0.0f, 0.0f), new Vector2f(0.125f, 0.25f));
        glyphList.add(test);
        shader.createVertexShader(Utils.loadResource("/shaders/font_vertex.glsl"));
        shader.createFragmentShader(Utils.loadResource("/shaders/font_fragment.glsl"));
        shader.link();
        shader.createUniform("hudTexture");
        shader.createUniform("transformationMatrix");
        shader.createUniform("sheetIndex");
        shader.createUniform("colour");
    }

    @Override
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
        shader.bind();
        shader.setUniform("sheetIndex", new Vector2f(5.0f, 5.0f));
        shader.setUniform("colour", new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
        bind(quad);
        for (HudTexture glyph : glyphList) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, glyph.getId());
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            Matrix4f matrix = Transformation.createTransformationMatrix(glyph);
            shader.setUniform("transformationMatrix", matrix);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }
        unbind();
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
    public void prepare(Object o, Camera camera) {

    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }
}
