#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoord0;
varying vec4 v_color;

uniform sampler2D u_texture1;

/*float easeInOutCubic(float pos) {
  float p = pos / 0.6;
  if (p < 1.) {
    return 0.5 * pow(abs(p), 3.);
  }
  return 0.5 * pow(abs(p), 3.);
}*/

void main() {
 //gl_FragColor =  v_color;
    /*if( v_texCoord0.t > 0.5) {
        float opacityX = smoothstep(0.0,1.0,1.0 - v_texCoord0.t);
        gl_FragColor =  v_color * vec4(opacityX, opacityX, opacityX, 1.0);
    } else {
        float opacityX = smoothstep(0.0,1.0,v_texCoord0.t);
        gl_FragColor =  v_color * vec4(opacityX, opacityX, opacityX, 1.0);
    }

    float opacityX = 0.5f;
    gl_FragColor = texture2D(u_texture1, v_texCoord0)* vec4(opacityX, opacityX, opacityX, 1.0);

    gl_FragColor =  v_color;

    gl_FragColor = texture2D(u_texture1, v_texCoord0);*/



    /*vec2 res2 = vec2(200,200);
    	vec2 uv = v_texCoord0 * 2.0 - 1.0;

    	vec3 finalColor = vec3 ( 0.3, 0.5, 1.0 );

    	finalColor *= abs( 1.0 / (sin( uv.y ) * 40.0) );

    if( v_texCoord0.t > 0.5) {
        float opacityX = smoothstep(0.0,1.0,1.0 - v_texCoord0.t);
       gl_FragColor = vec4( finalColor, opacityX );
    } else {
        float opacityX = smoothstep(0.0,1.0,v_texCoord0.t);
       gl_FragColor = vec4( finalColor, opacityX );
    }*/

    /*
    eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee*/
    vec2 p = v_texCoord0 * 2.0 - 1.0;

    vec3 c = vec3( 0.0 );

    float glowT = 1.0; //sin(1.0) * 0.5 + 0.5;
    float glowFactor = mix( 0.15, 0.35, glowT );

    //c += vec3(0.02, 0.03, 0.13) * ( glowFactor * abs( 1.0 / sin(p.y) *4.0 ));
    c += vec3(v_color.r, v_color.g, v_color.b) * ( glowFactor * abs( 1.0 / sin(p.y) *4.0 ));

    gl_FragColor = vec4( c, 0.5 );
    /*eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
    */

    c = vec3(v_color.r, v_color.g, v_color.b) * (abs( 1.0 / 0.9*sin(p.y) ));
    gl_FragColor = vec4( c, 0.5 );

    /*if(v_texCoord0.t > 0.4 && v_texCoord0.t < 0.6)
        gl_FragColor = vec4( c, 1.0 );
    else
    {
        //gl_FragColor = texture2D(u_texture1, v_texCoord0);
        //gl_FragColor = vec4(0.5, 0.5, 0.5, 0.1);
        vec4 alpha = texture2D(u_texture1, v_texCoord0).aaaa;
            vec4 negalpha = alpha*vec4(-1,-1,-1,1)+vec4(1,1,1,0);
            gl_FragColor = alpha*gl_Color+negalpha*vec4(1,1,1,0);
    }*/
}
