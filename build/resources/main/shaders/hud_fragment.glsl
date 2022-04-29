#version 400

in vec2 textureCoords;

out vec4 outColour;

uniform sampler2D hudTexture;

void main() {
    outColour = texture(hudTexture, textureCoords);
}