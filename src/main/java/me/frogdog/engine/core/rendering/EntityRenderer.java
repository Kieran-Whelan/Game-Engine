package me.frogdog.engine.core.rendering;

import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.ShaderManager;
import me.frogdog.engine.core.maths.Transformation;
import me.frogdog.engine.core.rendering.world.body.Body;
import me.frogdog.engine.core.rendering.world.Model;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.game.Main;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.utils.Utils;
import me.frogdog.engine.utils.interfaces.IRenderer;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityRenderer implements IRenderer {

    private ShaderManager shader;
    private Map<Model, List<Body>> entities;

    public EntityRenderer() throws Exception {
        entities = new HashMap<>();
        shader = new ShaderManager();
    }

    @Override
    public void init() throws Exception {
        shader.createVertexShader(Utils.loadResource("/shaders/entity_vertex.glsl"));
        shader.createFragmentShader(Utils.loadResource("/shaders/entity_fragment.glsl"));
        shader.link();
        shader.createUniform("textureSampler");
        shader.createUniform("skyColour");
        shader.createUniform("transformationMatrix");
        shader.createUniform("projectionMatrix");
        shader.createUniform("viewMatrix");
        shader.createUniform("ambientLight");
        shader.createMaterialUniform("material");
        shader.createUniform("specularPower");
        shader.createDirectionalLightUniform("directionalLight");
        shader.createPointLightListUniform("pointLights", Consts.MAX_POINT_LIGHTS);
        shader.createSpotLightListUniform("spotLights", Consts.MAX_SPOT_LIGHTS);

    }

    @Override
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
        shader.bind();
        shader.setUniform("projectionMatrix", Main.getWindow().updateProjectionMatrix());
        RenderManager.renderLights(pointLights, spotLights, directionalLight, shader);
        for (Model model : entities.keySet()) {
            bind(model);
            List<Body> entitiesList = entities.get(model);
            for (Body entity : entitiesList) {
                prepare(entity, camera);
                GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbind();
        }

        entities.clear();
        shader.unbind();
    }

    @Override
    public void bind(Model model) {
        GL30.glBindVertexArray(model.getId());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        if (model.getMaterial().isDisableCulling()) {
            RenderManager.disableCulling();
        } else {
            RenderManager.enableCulling();
        }

        shader.setUniform("material", model.getMaterial());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getId());
    }

    @Override
    public void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void prepare(Object entity, Camera camera) {
        shader.setUniform("textureSampler", 0);
        shader.setUniform("skyColour", new Vector3f(Consts.SKY_COLOUR.x, Consts.SKY_COLOUR.y, Consts.SKY_COLOUR.z));
        shader.setUniform("transformationMatrix", Transformation.createTransformationMatrix((Body) entity));
        shader.setUniform("viewMatrix", Transformation.getViewMatrix(camera));
    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }

    public Map<Model, List<Body>> getEntities() {
        return entities;
    }
}
