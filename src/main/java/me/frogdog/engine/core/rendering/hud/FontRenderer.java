package me.frogdog.engine.core.rendering.hud;

import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.ShaderManager;
import me.frogdog.engine.core.rendering.hud.gui.items.font.Glyph;
import me.frogdog.engine.core.world.Model;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.core.maths.Transformation;
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

public class FontRenderer implements IRenderer {

    private ShaderManager shader;
    private ObjectLoader loader;
    private final Model quad;
    private List<Glyph> glyphs;

    public FontRenderer() throws Exception {
        shader = new ShaderManager();
        loader = new ObjectLoader();
        quad = loader.loadModel(Consts.HUD_VERTICES);
        glyphs = new ArrayList<>();
    }

    @Override
    public void init() throws Exception {
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
        for (Glyph glyph : glyphs) {
            bind(quad);
            prepare(glyph, camera);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, glyph.getId());
            GL11.glDepthFunc(GL11.GL_LEQUAL);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
            unbind();
        }
        glyphs.clear();
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
    public void prepare(Object glyph, Camera camera) {
        shader.setUniform("sheetIndex", ((Glyph) glyph).getIndex());
        shader.setUniform("colour", ((Glyph) glyph).getColour());
        Matrix4f matrix = Transformation.createTransformationMatrix(((Glyph) glyph));
        shader.setUniform("transformationMatrix", matrix);
    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }

    public List<Glyph> getGlyphs() {
        return glyphs;
    }
}
