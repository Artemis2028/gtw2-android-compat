package com.badlogic.gdx.backends.android.a;

/**
 * Drop-in replacement for libGDX 1.7.0's {@code GdxEglConfigChooser}
 * (obfuscated as this class). See {@link com.invertbit.games.gtw.egl.EglCompat}.
 *
 * <p>Constructor and type name stay identical so {@code backends.android.Ci}
 * (AndroidGraphics) can still {@code new Cg(r,g,b,a,depth,stencil,samples)}.
 */
public final class Cg implements android.opengl.GLSurfaceView.EGLConfigChooser {
    private static final java.lang.String TAG = "GTW-EGL";

    protected int a;
    protected int b;
    protected int c;
    protected int d;
    protected int e;
    protected int f;
    protected int g;
    protected final int[] h;
    private final int[] i = new int[1];

    public Cg(int r, int green, int blue, int alpha, int depth, int stencil, int samples) {
        this.a = r;
        this.b = green;
        this.c = blue;
        this.d = alpha;
        this.e = depth;
        this.f = stencil;
        this.g = samples;
        this.h = com.invertbit.games.gtw.egl.EglCompat.chooseSpecs()[0];
    }

    private int attrib(javax.microedition.khronos.egl.EGL10 egl,
                       javax.microedition.khronos.egl.EGLDisplay display,
                       javax.microedition.khronos.egl.EGLConfig config, int attr) {
        if (egl.eglGetConfigAttrib(display, config, attr, this.i)) {
            return this.i[0];
        }
        return 0;
    }

    @Override
    public final javax.microedition.khronos.egl.EGLConfig chooseConfig(
            javax.microedition.khronos.egl.EGL10 egl,
            javax.microedition.khronos.egl.EGLDisplay display) {
        int[][] specs = com.invertbit.games.gtw.egl.EglCompat.chooseSpecs();
        int[] num = new int[1];
        javax.microedition.khronos.egl.EGLConfig[] found = null;
        int specUsed = -1;
        for (int s = 0; s < specs.length; s++) {
            egl.eglChooseConfig(display, specs[s], null, 0, num);
            if (num[0] > 0) {
                found = new javax.microedition.khronos.egl.EGLConfig[num[0]];
                egl.eglChooseConfig(display, specs[s], found, num[0], num);
                specUsed = s;
                break;
            }
        }
        if (found == null || found.length == 0) {
            throw new java.lang.IllegalArgumentException(
                    "No EGL configs match any GLES2 fallback spec");
        }
        int[][] attrs = new int[found.length][6];
        for (int k = 0; k < found.length; k++) {
            attrs[k][0] = attrib(egl, display, found[k],
                    com.invertbit.games.gtw.egl.EglCompat.EGL_RED_SIZE);
            attrs[k][1] = attrib(egl, display, found[k],
                    com.invertbit.games.gtw.egl.EglCompat.EGL_GREEN_SIZE);
            attrs[k][2] = attrib(egl, display, found[k],
                    com.invertbit.games.gtw.egl.EglCompat.EGL_BLUE_SIZE);
            attrs[k][3] = attrib(egl, display, found[k],
                    com.invertbit.games.gtw.egl.EglCompat.EGL_ALPHA_SIZE);
            attrs[k][4] = attrib(egl, display, found[k],
                    com.invertbit.games.gtw.egl.EglCompat.EGL_DEPTH_SIZE);
            attrs[k][5] = attrib(egl, display, found[k],
                    com.invertbit.games.gtw.egl.EglCompat.EGL_STENCIL_SIZE);
        }
        int idx = com.invertbit.games.gtw.egl.EglCompat.pick(
                attrs, this.a, this.b, this.c, this.d, this.e, this.f);
        if (idx < 0 || idx >= found.length || found[idx] == null) {
            throw new java.lang.IllegalArgumentException(
                    "EGL chooser fallback produced no config");
        }
        android.util.Log.i(TAG, "chose config "
                + com.invertbit.games.gtw.egl.EglCompat.describe(attrs[idx])
                + " from " + found.length + " candidates (spec #" + specUsed
                + ", requested " + this.a + "/" + this.b + "/" + this.c + "/" + this.d
                + " depth>=" + this.e + " stencil>=" + this.f + ")");
        return found[idx];
    }
}
