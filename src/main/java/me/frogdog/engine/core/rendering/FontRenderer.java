package me.frogdog.engine.core.rendering;

import me.frogdog.engine.core.Camera;
import me.frogdog.engine.core.entity.Model;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.utils.interfaces.IRenderer;

public class FontRenderer implements IRenderer {

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
