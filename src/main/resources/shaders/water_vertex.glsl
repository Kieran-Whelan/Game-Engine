#version 400

in vec3 position;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

void main() {
    vec4 worldPos = transformationMatrix * vec4(position, 1.0);
    vec4 positionRelativeToCam = viewMatrix * worldPos;
    //gl_Position = projectionMatrix * viewMatrix * transformationMatrix * worldPos;
    gl_Position = projectionMatrix * positionRelativeToCam;
}