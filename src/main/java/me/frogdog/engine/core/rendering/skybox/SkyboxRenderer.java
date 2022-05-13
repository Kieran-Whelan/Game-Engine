package me.frogdog.engine.core.rendering.skybox;

import me.frogdog.engine.core.EngineManager;
import me.frogdog.engine.core.ShaderManager;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.maths.Transformation;
import me.frogdog.engine.core.world.Model;
import me.frogdog.engine.game.Main;
import me.frogdog.engine.utils.Consts;
import me.frogdog.engine.utils.ObjectLoader;
import me.frogdog.engine.utils.Utils;
import me.frogdog.engine.utils.interfaces.IRenderer;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class SkyboxRenderer implements IRenderer {

    private ShaderManager shader;
    private ObjectLoader loader;
    private Model cube;
    private int texture;
    private int nightTexture;
    private float rotation = 0;
    private float time = 0;

    private static final float SIZE = 500f;
    private static final float ROTATION_SPEED = 0.5f;

    private final float[] vertices = new float[] {
            -SIZE,  SIZE, -SIZE,
            -SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE, -SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,

            -SIZE, -SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE, -SIZE,  SIZE,
            -SIZE, -SIZE,  SIZE,

            -SIZE,  SIZE, -SIZE,
            SIZE,  SIZE, -SIZE,
            SIZE,  SIZE,  SIZE,
            SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE,  SIZE,
            -SIZE,  SIZE, -SIZE,

            -SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE, -SIZE,
            SIZE, -SIZE, -SIZE,
            -SIZE, -SIZE,  SIZE,
            SIZE, -SIZE,  SIZE
    };

    private final String[] textureFiles = new String[] {"textures/skybox/day/right.png", "textures/skybox/day/left.png", "textures/skybox/day/top.png", "textures/skybox/day/bottom.png", "textures/skybox/day/back.png", "textures/skybox/day/front.png"};
    private final String[] nightTextureFiles = new String[] {"textures/skybox/night/nightRight.png", "textures/skybox/night/nightLeft.png", "textures/skybox/night/nightTop.png", "textures/skybox/night/nightBottom.png", "textures/skybox/night/nightBack.png", "textures/skybox/night/nightFront.png"};

    public SkyboxRenderer() throws Exception {
        loader = new ObjectLoader();
        shader = new ShaderManager();
        cube = loader.loadModel(vertices, 3);
        texture = loader.loadCubeMap(textureFiles);
        nightTexture = loader.loadCubeMap(nightTextureFiles);
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
        shader.setUniform("projectionMatrix", Main.getWindow().updateProjectionMatrix());
        prepare(cube, camera);
        bind(cube);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0 , cube.getVertexCount());
        unbind();
        shader.unbind();
    }

    @Override
    public void bind(Model model) {
        time += EngineManager.getFrameTimeSeconds() * 1000;
        time %= 24000;

        int texture1;
        int texture2;
        float blendFactor;

        if (time >= 0 && time < 5000){
            texture1 = nightTexture;
            texture2 = nightTexture;
            blendFactor = (time - 0)/(5000 - 0);
        } else if (time >= 5000 && time < 8000){
            texture1 = nightTexture;
            texture2 = texture;
            blendFactor = (time - 5000)/(8000 - 5000);
        } else if (time >= 8000 && time < 21000){
            texture1 = texture;
            texture2 = texture;
            blendFactor = (time - 8000)/(21000 - 8000);
        } else {
            texture1 = texture;
            texture2 = nightTexture;
            blendFactor = (time - 21000)/(24000 - 21000);
        }

        GL30.glBindVertexArray(cube.getId());
        GL20.glEnableVertexAttribArray(0);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture2);
        shader.setUniform("blendFactor", blendFactor);
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
        rotation += ROTATION_SPEED * EngineManager.getFrameTimeSeconds();
        matrix.rotateY((float) Math.toRadians(rotation));
        shader.setUniform("viewMatrix", matrix);
        shader.setUniform("fogColour", new Vector3f(Consts.SKY_COLOUR.x, Consts.SKY_COLOUR.y, Consts.SKY_COLOUR.z));
    }

    @Override
    public void cleanup() {
        shader.cleanup();
    }

    private void setupTextureUniforms() {
        shader.setUniform("cubeMap", 0);
        shader.setUniform("cubeMap2", 1);
    }
}
