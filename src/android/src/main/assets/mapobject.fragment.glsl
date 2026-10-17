#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord0;
varying vec4 v_color;

uniform sampler2D u_texture1;

void main() {
    gl_FragColor =  v_color;
}