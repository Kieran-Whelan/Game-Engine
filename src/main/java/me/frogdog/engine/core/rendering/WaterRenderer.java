package me.frogdog.engine.core.rendering;

import me.frogdog.engine.core.ShaderManager;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.maths.Transformation;
import me.frogdog.engine.core.world.water.WaterTile;
import me.frogdog.engine.core.world.Model;
import me.frogdog.engine.game.Main;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.utils.Utils;
import me.frogdog.engine.utils.interfaces.IRenderer;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class WaterRenderer implements IRenderer {

    private ShaderManager shader;
    private List<WaterTile> waterTiles;

    public WaterRenderer() throws Exception {
        waterTiles = new ArrayList<>();
        shader = new ShaderManager();
    }

    @Override
    public void init() throws Exception {
        shader.createVertexShader(Utils.loadResource("/shaders/water_vertex.glsl"));
        shader.createFragmentShader(Utils.loadResource("/shaders/water_fragment.glsl"));
        shader.link();
        shader.createUniform("projectionMatrix");
        shader.createUniform("viewMatrix");
        shader.createUniform("transformationMatrix");
        waterTiles.add(new WaterTile(new Vector3f(0f, -20f, 0f), 400));
    }

    @Override
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
        shader.bind();
        shader.setUniform("projectionMatrix", Main.getWindow().updateProjectionMatrix());
        for (WaterTile waterTile : waterTiles) {
            bind(waterTile.getModel());
            prepare(waterTile, camera);
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, waterTile.getModel().getVertexCount());
            unbind();
        }
        shader.unbind();
    }

    @Override
    public void bind(Model model) {
        GL30.glBindVertexArray(model.getId());
        GL20.glEnableVertexAttribArray(0);
    }

    @Override
    public void unbind() {
        GL30.glBindVertexArray(0);
        GL20.glDisableVertexAttribArray(0);
    }

    @Override
    public void prepare(Object waterTile, Camera camera) {
        shader.setUniform("transformationMatrix", Transformation.createTransformationMatrix((WaterTile) waterTile));
        shader.setUniform("viewMatrix", Transformation.getViewMatrix(camera));
    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }


}
