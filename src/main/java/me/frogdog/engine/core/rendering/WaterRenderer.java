package me.frogdog.engine.core.rendering;

import me.frogdog.engine.core.ShaderManager;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.core.maths.Camera;
import me.frogdog.engine.core.world.water.WaterTile;
import me.frogdog.engine.core.world.Model;
import me.frogdog.engine.utils.interfaces.IRenderer;

import java.util.ArrayList;
import java.util.List;

public class WaterRenderer implements IRenderer {

    private ShaderManager shaderManager;
    private List<WaterTile> waterTiles;

    public WaterRenderer() throws Exception {
        waterTiles = new ArrayList<>();
        shaderManager = new ShaderManager();
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void render(Camera camera, PointLight[] pointLights, SpotLight[] spotLights, DirectionalLight directionalLight) {

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

    }
}
