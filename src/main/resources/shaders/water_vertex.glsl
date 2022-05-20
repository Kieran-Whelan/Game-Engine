#version 400

in vec3 position;
in vec2 textureCoord;
in vec3 normal;

out vec2 fragTextureCoord;
out vec3 fragNormal;
out vec3 fragPos;
out vec3 textureCoords;
out float visibility;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

const float density = 0.0035;
//const float density = 0.007;
const float gradient = 5.0;
//const float gradient = 1.5;

void main() {
    vec4 worldPos = transformationMatrix * vec4(position, 1.0);
    vec4 positionRelativeToCam = viewMatrix * worldPos;
    //gl_Position = projectionMatrix * viewMatrix * transformationMatrix * worldPos;
    gl_Position = projectionMatrix * positionRelativeToCam;

    fragNormal = normalize(worldPos).xyz;
    fragPos = worldPos.xyz;
    fragTextureCoord = textureCoord;

    float distance = length(positionRelativeToCam.xyz);
    visibility = exp(-pow((distance * density), gradient));
    visibility = clamp(visibility, 0.0, 1.0);
}