#version 150

uniform sampler2D Sampler0;
uniform float GameTime;
uniform float TimeOffset;
uniform float Speed;
uniform float Amplitude;
uniform float FreqX;
uniform float FreqY;
uniform vec4 UV;

uniform vec4 ColorModulator;

in vec4 vertexColor;
in vec2 texCoord0;
in vec2 texCoord2;

out vec4 fragColor;

void main() {
    float time = GameTime * Speed + TimeOffset;
    vec2 uv = texCoord0;

    uv.x += cos(uv.y*FreqX+time)/Amplitude;
    uv.y += sin(uv.x*FreqY+time)/Amplitude;

    uv.x = clamp(uv.x, UV.x, UV.y);
    uv.y = clamp(uv.y, UV.z, UV.w);
    vec4 color = texture(Sampler0, uv);
    fragColor = color * ColorModulator;
}