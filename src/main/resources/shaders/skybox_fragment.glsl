#version 400

in vec3 textureCoords;
out vec4 outColour;

uniform samplerCube cubeMap;

void main() {
    outColour = texture(cubeMap, textureCoords);
}