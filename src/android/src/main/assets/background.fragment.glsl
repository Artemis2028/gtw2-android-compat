#ifdef GL_ES
precision mediump float;
#endif

#define time 5.0

const float PI = 3.14159;
const float RADIUS = 50.;

const float CELL_SIZE = 4.;
const float CELL_THICK = 0.5;
const float CELL_RADIUS = .4*CELL_SIZE - CELL_THICK;

float
squared_distance(const in vec2 a, const in vec2 b)
{
	vec2 d = a - b;
	return dot(d, d);
}
void main(void)
{
	float v = 0.0;
	float w = v - RADIUS;
	
	float r = pow(1. + pow(w, 2.), -.17)*CELL_RADIUS;

	vec2 offs = vec2(step(CELL_SIZE, mod(gl_FragCoord.y, 2.*CELL_SIZE))*.5*CELL_SIZE, 0);
	vec2 cell_center = offs + CELL_SIZE*floor((gl_FragCoord.xy - offs)/CELL_SIZE) + .5*vec2(CELL_SIZE, CELL_SIZE);
	float d = distance(gl_FragCoord.xy, cell_center);
	
	float c = smoothstep(r + CELL_THICK, r, d);
		
	gl_FragColor = vec4(c, c, c, 1);
}