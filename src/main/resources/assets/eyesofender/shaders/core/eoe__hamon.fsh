#version 150
#define PI 3.1415926538
uniform sampler2D DiffuseSampler;

uniform float GameTime;
uniform float Speed;
uniform vec3 Color;

in vec4 vertexColor;
in vec2 texCoord0;


out vec4 fragColor;

vec3 veryOptimizedFantasticCode(vec3 colour){
    vec3 outColor = vec3(0,0,0);
    if((colour.r >= colour.g && colour.g >= colour.b) || (colour.r >= colour.b && colour.g >= colour.b)){
        outColor = vec3(max(colour.r*0.1, max(colour.g*0.1, colour.b)));
    }else if(colour.r >= colour.g && colour.b >= colour.g || (colour.r >= colour.b && colour.b >= colour.g)){
        outColor = vec3(max(colour.r*0.1, max(colour.b*0.1, colour.g)));
    }else if((colour.g >= colour.r && colour.r >= colour.b) || (colour.g >= colour.b && colour.r >= colour.b)){
        outColor = vec3(max(colour.g*0.1, max(colour.r*0.1, colour.b)));
    }else if((colour.g >= colour.r && colour.b >= colour.r) || (colour.g >= colour.b && colour.b >= colour.r)){
        outColor = vec3(max(colour.g*0.1, max(colour.b*0.1, colour.r)));
    }else if((colour.b >= colour.g && colour.g >= colour.r) || (colour.b >= colour.r && colour.g >= colour.r)){
        outColor = vec3(max(colour.b*0.1, max(colour.g*0.1, colour.r)));
    }else if((colour.b >= colour.g && colour.r >= colour.g) || (colour.b >= colour.r && colour.r >= colour.g)){
        outColor = vec3(max(colour.b*0.1, max(colour.r*0.1, colour.g)));
    }
    return outColor;
}

void main( ) {
    float time = GameTime * Speed;

    vec2 p = mod(texCoord0 * PI * 2, PI * 2) - 256.0;

    vec2 i = vec2(p);
    float c = 1.0;
    float inten = .005;
    float alpha = 0.1;

    for (int n = 0; n < 5; n++) {
        float t = time * (1.0 - (3.5 / float(n + 1)));
        i = p + vec2(cos(t - i.x) + sin(t + i.y), sin(t - i.y) + cos(t + i.x));
        c += 1.0 / length(vec2(p.x / (sin(i.x + t) / inten),p.y / (cos(i.y + t) / inten)));
    }
    c /= float(5);
    c = 1.17 - pow(c, 1.4);
    vec3 colour = vec3(pow(abs(c), 8.0));
    colour = clamp(colour + Color, 0.0, 1.0);

    if(length(colour.rgb) < 0.1 * 3){
        discard;
    }
    fragColor = vec4(colour, veryOptimizedFantasticCode(colour));
}
