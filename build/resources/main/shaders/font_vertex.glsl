#version 400

in vec2 position;

out vec2 textureCoords;
out vec2 textureCoordsOffset;
out vec2 vZoom;

uniform mat4 transformationMatrix;
uniform vec2 sheetIndex;

void main() {
    vec2 offset = vec2(32.0 * sheetIndex.x, 64.0 * sheetIndex.y);
    vec2 texSize = vec2(512.0, 1024.0);
    vec2 subImageSize = vec2(32.0, 64.0);

    float u = offset.x / texSize.x;
    float v = offset.y / texSize.y;
    textureCoordsOffset = vec2(u, v);
    vZoom = subImageSize / texSize;

    gl_Position = transformationMatrix * vec4(position, 0.0, 1.0);
    textureCoords = vec2((position.x + 0.9) / 2, 1 - (position.y + 1.0) / 2);
}