#version 150
#define PI 3.1415926538
uniform sampler2D DiffuseSampler;

uniform float GameTime;
uniform float Speed;

in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main( ) {
    float time = GameTime * Speed;

    vec2 p = mod(texCoord0 * PI * 2, PI * 2) - 256.0;

    vec2 i = vec2(p);
    float c = 1.0;
    float inten = .005;

    for (int n = 0; n < 5; n++) {
        float t = time * (1.0 - (3.5 / float(n + 1)));
        i = p + vec2(cos(t - i.x) + sin(t + i.y), sin(t - i.y) + cos(t + i.x));
        c += 1.0 / length(vec2(p.x / (sin(i.x + t) / inten),p.y / (cos(i.y + t) / inten)));
    }
    c /= float(5);
    c = 1.17 - pow(c, 1.4);
    vec3 colour = vec3(pow(abs(c), 8.0));
    colour = clamp(colour + vec3(0.5, 0.35, 0.0), 0.0, 1.0);

    if(length(colour.rgb) < 0.1 * 3){
        discard;
    }
    fragColor = vec4(colour, max(colour.r, max(colour.g, colour.b)));
}
