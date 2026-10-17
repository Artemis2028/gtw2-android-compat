attribute vec3 a_position, barycentric;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;
attribute vec4 Color;

uniform mat4 u_worldTrans;
uniform mat4 u_projTrans;

varying vec2 v_texCoord0;
varying vec4 vColor;

varying vec3 vBC;

void main() {
    vBC = barycentric;
    vColor = vec4(barycentric,0.5f);
    v_texCoord0 = a_texCoord0;
    gl_Position = u_projTrans * u_worldTrans * vec4(a_position, 1.0);
}