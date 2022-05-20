#version 400

in float visibility;

out vec4 outColour;

uniform vec3 skyColour;

void main() {
    outColour = mix(vec4(skyColour, 1.0), vec4(0.0, 0.0, 0.9, 1.0), visibility);
}