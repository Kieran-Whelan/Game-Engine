package me.frogdog.engine.core;

import me.frogdog.engine.core.rendering.world.particle.Particle;
import me.frogdog.engine.core.rendering.world.skybox.Skybox;
import me.frogdog.engine.core.rendering.world.body.Body;
import me.frogdog.engine.core.rendering.world.terrain.Terrain;
import me.frogdog.engine.core.lighting.DirectionalLight;
import me.frogdog.engine.core.lighting.PointLight;
import me.frogdog.engine.core.lighting.SpotLight;
import me.frogdog.engine.utils.Consts;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {

    private List<Body> entities;
    private List<Terrain> terrains;
    private List<Particle> particles;
    private List<Skybox> skyboxes;

    private Vector3f ambientLight;
    private SpotLight[] spotLights;
    private PointLight[] pointLights;
    private DirectionalLight directionalLight;
    private float lightAngle;
    private float spotAngle = 0;
    private float spotInc = 1;

    public SceneManager(float lightAngle) {
        entities = new ArrayList<>();
        terrains = new ArrayList<>();
        particles = new ArrayList<>();
        skyboxes = new ArrayList<>();
        ambientLight = Consts.AMBIENT_LIGHT;
        this.lightAngle = lightAngle;
    }

    public List<Body> getEntities() {
        return entities;
    }

    public void addEntity(Body entity) {
        this.entities.add(entity);
    }

    public List<Terrain> getTerrains() {
        return terrains;
    }

    public void addTerrain(Terrain terrain) {
        this.terrains.add(terrain);
    }

    public List<Skybox> getSkybox() {
        return skyboxes;
    }

    public void addSkybox(Skybox skybox) {
        skyboxes.add(skybox);
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public void addParticle(Particle particle)   {
        particles.add(particle);
    }

    public Vector3f getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(Vector3f ambientLight) {
        this.ambientLight = ambientLight;
    }

    public void setAmbientLight(float x, float y, float z) {
        this.ambientLight = new Vector3f(x, y, z);
    }

    public SpotLight[] getSpotLights() {
        return spotLights;
    }

    public void setSpotLights(SpotLight[] spotLights) {
        this.spotLights = spotLights;
    }

    public PointLight[] getPointLights() {
        return pointLights;
    }

    public void setPointLights(PointLight[] pointLights) {
        this.pointLights = pointLights;
    }

    public float getLightAngle() {
        return lightAngle;
    }

    public void setLightAngle(float lightAngle) {
        this.lightAngle = lightAngle;
    }

    public void incLightAngle(float increment) {
        this.lightAngle += increment;
    }

    public float getSpotAngle() {
        return spotAngle;
    }

    public void setSpotAngle(float spotAngle) {
        this.spotAngle = spotAngle;
    }

    public void incSpotAngle(float increment) {
        this.spotAngle += increment;
    }

    public float getSpotInc() {
        return spotInc;
    }

    public void setSpotInc(float spotInc) {
        this.spotInc = spotInc;
    }

    public DirectionalLight getDirectionalLight() {
        return directionalLight;
    }

    public void setDirectionalLight(DirectionalLight directionalLight) {
        this.directionalLight = directionalLight;
    }
}
