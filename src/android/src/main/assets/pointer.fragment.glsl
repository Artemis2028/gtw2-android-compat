#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord0;

#define resolution vec2(32,32)
#define mouse vec2(0.5,0.5)
#define BEAM_WIDTH 0.01

void main( void ) {
	/*vec2 unit = vec2(cos(0.0), sin(0.0));

	vec2 position = v_texCoord0.xy;
	vec2 ms = mouse;
	float f = 0.0;

	float intensity = BEAM_WIDTH;
	f += intensity / abs(dot(position - ms, unit));
	f += intensity / abs(dot(position - ms, vec2(unit.y, -unit.x)));
	f += intensity / abs(0.25 - length(position - ms));
	f += intensity / abs(0.4 - length(position - ms));*/

	gl_FragColor = vec4(1, 1, 1, 1.0);
}