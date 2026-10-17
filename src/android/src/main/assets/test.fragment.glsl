#ifdef GL_ES
precision mediump float;
#endif

uniform vec3 u_color;
uniform sampler2D u_texture1;
uniform sampler2D u_texture2;
uniform sampler2D u_texture3;

uniform vec2 u_resolution;

varying vec2 v_texCoord0;

//RADIUS of our vignette, where 0.5 results in a circle fitting the screen
const float RADIUS = 0.75;

//softness of our vignette, between 0.0 and 1.0
const float SOFTNESS = 0.45;

void main() {
    vec4 texColor1 = texture2D(u_texture1, v_texCoord0);
    vec4 texColor2 = texture2D(u_texture2, v_texCoord0);

   float mask = texture2D(u_texture3, v_texCoord0).a;

    vec2 position = (gl_FragCoord.xy / u_resolution.xy) - vec2(0.5);

    //OPTIONAL: correct for aspect ratio
    position.x *= u_resolution.x / u_resolution.y;
    vec2 bottom = vec2(1.0, 1.0);
    //determine the vector length from center
    float len = length(position);

    //our vignette effect, using smoothstep
    float vignette = smoothstep(RADIUS, RADIUS-SOFTNESS, len);

    vec4 texColor = mix(texColor1, texColor2, mask);
    //texColor.rgb *= vignette;
    vec4 fogColor  = vec4(0.5,0.6,0.7,0.5);

    gl_FragColor = mix(texColor, fogColor, 1.0-vignette);
}