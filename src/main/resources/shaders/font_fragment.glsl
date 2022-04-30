#version 400

in vec2 textureCoords;
in vec2 textureCoordsOffset;
in vec2 vZoom;

out vec4 outColour;

uniform sampler2D hudTexture;

void main() {
    //outColour = texture(hudTexture, textureCoords) * vec4(0.0, 1.0, 0.8, 1.0);
    outColour = texture(hudTexture, fract(textureCoords) * vZoom + textureCoordsOffset);
}