package me.frogdog.engine.core;

import me.frogdog.engine.core.entity.Entity;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.utils.Utils;
import me.frogdog.engine.game.Main;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class RenderManager {

    private final WindowManager window;
    private ShaderManager shader;

    public RenderManager() {
        window = Main.getWindow();
    }

    public void init() throws Exception {
        shader = new ShaderManager();
        shader.createVertexShader(Utils.loadResource("/shaders/vertex.glsl"));
        shader.createFragmentShader(Utils.loadResource("/shaders/fragment.glsl"));
        shader.link();
        shader.createUniform("textureSampler");
        shader.createUniform("transformationMatrix");
        shader.createUniform("projectionMatrix");
        shader.createUniform("viewMatrix");
        shader.createUniform("ambientLight");
        shader.createMaterialUniform("material");
        shader.createUniform("specularPower");
        shader.createDirectionalLightUniform("directionalLight");

    }

    public void render(Entity entity, Camera camera, DirectionalLight directionalLight) {
        clear();

        if (window.isResize()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResize(false);
        }

        shader.bind();
        shader.setUniform("textureSampler", 0);
        shader.setUniform("transformationMatrix", Transformation.createTransformationMatrix(entity));
        shader.setUniform("projectionMatrix", window.updateProjectionMatrix());
        shader.setUniform("viewMatrix", Transformation.getViewMatrix(camera));
        shader.setUniform("material", entity.getModel().getMaterial());
        shader.setUniform("ambientLight", Consts.AMBIENT_LIGHT);
        shader.setUniform("specularPower", Consts.SPECULAR_POWER);
        shader.setUniform("directionalLight", directionalLight);

        GL30.glBindVertexArray(entity.getModel().getId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL13.glActiveTexture(GL13.GL_TEXTURE);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getModel().getTexture().getId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
        shader.unbind();
    }

    public void clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup() {
        shader.cleanup();
    }
}