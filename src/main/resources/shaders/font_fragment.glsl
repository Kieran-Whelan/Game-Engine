#version 400

in vec2 textureCoords;
in vec2 textureCoordsOffset;
in vec2 vZoom;

out vec4 outColour;

uniform sampler2D hudTexture;
uniform vec4 colour;

void main() {
    outColour = texture(hudTexture, fract(textureCoords) * vZoom + textureCoordsOffset) * colour;
}