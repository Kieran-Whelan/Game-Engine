package me.frogdog.engine.core.rendering;

import me.frogdog.engine.core.ShaderManager;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.maths.Transformation;
import me.frogdog.engine.core.world.Model;
import me.frogdog.engine.core.world.particle.Particle;
import me.frogdog.engine.game.Main;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.utils.ObjectLoader;
import me.frogdog.engine.utils.Utils;
import me.frogdog.engine.utils.interfaces.IRenderer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleRenderer implements IRenderer {

    private ShaderManager shader;
    private ObjectLoader loader;
    private Model quad;
    private List<Particle> particles;

    public ParticleRenderer() throws Exception {
        shader = new ShaderManager();
        loader = new ObjectLoader();
        particles = new ArrayList<>();
        quad = loader.loadModel(Consts.PARTICLE_VERTICES, 2);

    }

    @Override
    public void init() throws Exception {
        shader.createVertexShader(Utils.loadResource("/shaders/particle_vertex.glsl"));
        shader.createFragmentShader(Utils.loadResource("/shaders/particle_fragment.glsl"));
        shader.link();
        shader.createUniform("projectionMatrix");
        shader.createUniform("modelViewMatrix");
    }

    @Override
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {
        shader.bind();
        shader.setUniform("projectionMatrix", Main.getWindow().updateProjectionMatrix());
        update();
        bind(quad);
        for (Particle particle : particles) {
            updateModelViewMatrix(particle.getPosition(), particle.getRotation(), particle.getScale(), Transformation.getViewMatrix(camera));
            GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
        }
        unbind();
        shader.unbind();
    }

    @Override
    public void bind(Model model) {
        GL30.glBindVertexArray(model.getId());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
    }

    @Override
    public void unbind() {
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void prepare(Object particle, Camera camera) {

    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }

    private void update() {
        Iterator<Particle> iterator = particles.iterator();
        while (iterator.hasNext()) {
            Particle p = iterator.next();
            boolean alive = p.update();
            if (!alive) {
                iterator.remove();
            }
        }
    }

    private void updateModelViewMatrix(Vector3f position, float rotation, float scale, Matrix4f viewMatrix) {
        Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.translate(position);

        modelMatrix.m00(viewMatrix.m00());
        modelMatrix.m01(viewMatrix.m10());
        modelMatrix.m02(viewMatrix.m20());
        modelMatrix.m10(viewMatrix.m01());
        modelMatrix.m11(viewMatrix.m11());
        modelMatrix.m12(viewMatrix.m21());
        modelMatrix.m20(viewMatrix.m02());
        modelMatrix.m21(viewMatrix.m12());
        modelMatrix.m22(viewMatrix.m22());
        modelMatrix.rotateZ((float) Math.toRadians(rotation));
        modelMatrix.scale(scale, scale, scale);
        Matrix4f modelViewMatrix = viewMatrix.mul(modelMatrix);
        shader.setUniform("modelViewMatrix", modelViewMatrix);
    }

    public List<Particle> getParticles() {
        return particles;
    }
}
