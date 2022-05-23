#version 400

in vec2 textureCoords;

out vec4 outColour;

uniform sampler2D particleTexture;

void main() {
    outColour = texture(particleTexture, textureCoords);
}