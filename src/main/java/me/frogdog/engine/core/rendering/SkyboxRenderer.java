package me.frogdog.engine.core.rendering;

import me.frogdog.engine.core.EngineManager;
import me.frogdog.engine.core.ShaderManager;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.maths.Transformation;
import me.frogdog.engine.core.rendering.world.skybox.Skybox;
import me.frogdog.engine.core.rendering.world.Model;
import me.frogdog.engine.game.Main;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.utils.Utils;
import me.frogdog.engine.utils.interfaces.IRenderer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class SkyboxRenderer implements IRenderer {

    private ShaderManager shader;
    private List<Skybox> skyboxes;
    private float rotation = 0;
    private float time = 0;

    public SkyboxRenderer() throws Exception {
        shader = new ShaderManager();
        skyboxes = new ArrayList<>();
    }

    @Override
    public void init() throws Exception {
        shader.createVertexShader(Utils.loadResource("/shaders/skybox_vertex.glsl"));
        shader.createFragmentShader(Utils.loadResource("/shaders/skybox_fragment.glsl"));
        shader.link();
        shader.createUniform("projectionMatrix");
        shader.createUniform("viewMatrix");
        shader.createUniform("cubeMap");
        shader.createUniform("cubeMap2");
        shader.createUniform("fogColour");
        shader.createUniform("blendFactor");
    }

    @Override
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
        shader.bind();
        setupTextureUniforms();
        for (Skybox skybox : skyboxes) {
            shader.setUniform("projectionMatrix", Main.getWindow().updateProjectionMatrix());
            bind(skybox.getModel());
            prepare(skybox, camera);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, skybox.getModel().getVertexCount());
            unbind();
        }
        skyboxes.clear();
        shader.unbind();
    }

    @Override
    public void bind(Model model) {
        GL30.glBindVertexArray(model.getId());
        GL20.glEnableVertexAttribArray(0);
    }

    @Override
    public void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void prepare(Object skybox, Camera camera) {
        Matrix4f matrix = Transformation.getViewMatrix(camera);
        matrix.m30(0);
        matrix.m31(0);
        matrix.m32(0);
        rotation += 0.5f * EngineManager.getFrameTimeSeconds();
        matrix.rotateY((float) Math.toRadians(rotation));
        shader.setUniform("viewMatrix", matrix);
        shader.setUniform("fogColour", new Vector3f(Consts.SKY_COLOUR.x, Consts.SKY_COLOUR.y, Consts.SKY_COLOUR.z));
        time += EngineManager.getFrameTimeSeconds() * 1000;
        time %= 24000;

        int texture1;
        int texture2;
        float blendFactor;

        if (time >= 0 && time < 5000){
            texture1 = ((Skybox)skybox).getNightTextureMap();
            texture2 = ((Skybox)skybox).getNightTextureMap();
            blendFactor = (time - 0)/(5000 - 0);
        } else if (time >= 5000 && time < 8000){
            texture1 = ((Skybox)skybox).getNightTextureMap();
            texture2 = ((Skybox)skybox).getDayTextureMap();
            blendFactor = (time - 5000)/(8000 - 5000);
        } else if (time >= 8000 && time < 21000){
            texture1 = ((Skybox)skybox).getDayTextureMap();
            texture2 = ((Skybox)skybox).getDayTextureMap();
            blendFactor = (time - 8000)/(21000 - 8000);
        } else {
            texture1 = ((Skybox)skybox).getDayTextureMap();
            texture2 = ((Skybox)skybox).getNightTextureMap();
            blendFactor = (time - 21000)/(24000 - 21000);
        }

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture2);
        shader.setUniform("blendFactor", blendFactor);
    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }

    private void setupTextureUniforms() {
        shader.setUniform("cubeMap", 0);
        shader.setUniform("cubeMap2", 1);
    }

    public List<Skybox> getSkyboxes() {
        return skyboxes;
    }
}
