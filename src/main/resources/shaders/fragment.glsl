#version 400 core

in vec2 fragTextureCoord;
in vec3 fragNormal;
in vec3 fragPos;

out vec4 fragColour;

struct Material {
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    int hasTexture;
    float reflectance;
};

struct DirectionalLight {
    vec3 colour;
    vec3 direction;
    float intensity;
};

struct PointLight {
    vec3 colour;
    vec3 position;
    float intensity;
    float constant;
    float linear;
    float exponent;
};

uniform sampler2D textureSampler;
uniform vec3 ambientLight;
uniform Material material;
uniform float specularPower;
uniform DirectionalLight directionalLight;
uniform PointLight pointLight;

vec4 ambientC;
vec4 diffuseC;
vec4 specularC;

void setupColours(Material material, vec2 textCoord) {
    if (material.hasTexture == 1) {
        ambientC = texture(textureSampler, textCoord);
        diffuseC = ambientC;
        specularC = ambientC;
    } else {
        ambientC = material.ambient;
        diffuseC = material.diffuse;
        specularC = material.specular;
    }
}

vec4 calcLightColour(vec3 lightColour, float lightIntensity, vec3 position, vec3 toLightDir, vec3 normal) {
    vec4 diffuseColour = vec4(0, 0, 0, 0);
    vec4 specColour = vec4(0, 0, 0, 0);

    //diffuse Light
    float diffuseFactor = max(dot(normal, toLightDir), 0.0);
    diffuseColour = diffuseC * vec4(lightColour, 1.0) * lightIntensity *  diffuseFactor;

    //specular Colour
    vec3 cameraDirection = normalize(-position);
    vec3 fromLightDir = -toLightDir;
    vec3 reflectedLight = normalize(reflect(fromLightDir, normal));
    float specularFactor = max(dot(cameraDirection, reflectedLight), 0.0);
    specularFactor = pow(specularFactor, specularPower);
    specColour = specularC * lightIntensity * specularFactor * material.reflectance * vec4(lightColour, 1.0);

    return(diffuseColour + specColour);
}


vec4 calcPointLight(PointLight light, vec3 position, vec3 normal) {
    vec3 lightDir = light.position - position;
    vec3 toLightDir = normalize(lightDir);
    vec4 lightColour = calcLightColour(light.colour, light.intensity, position, toLightDir, normal);

    float distance = length(lightDir);
    float attenuationInv = light.constant + light.linear * distance + light.exponent * distance * distance;
    return lightColour / attenuationInv;
}

vec4 calcDirectionalLight(DirectionalLight light, vec3 position, vec3 normal) {
    return calcLightColour(light.colour, light.intensity, position, normalize(light.direction), normal);
}

void main() {

    setupColours(material, fragTextureCoord);

    vec4 diffuseSpecularComp = calcDirectionalLight(directionalLight, fragPos, fragNormal);
    diffuseSpecularComp += calcPointLight(pointLight, fragPos, fragNormal);

    fragColour = ambientC * vec4(ambientLight, 1) + diffuseSpecularComp;
}