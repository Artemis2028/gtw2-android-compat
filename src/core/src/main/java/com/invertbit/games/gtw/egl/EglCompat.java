package com.invertbit.games.gtw.egl;

/**
 * Shared EGL helpers for the libGDX 1.7.0 Android backend.
 *
 * <p>The obfuscated {@code backends.android.a.Cg} chooser (GdxEglConfigChooser) asked
 * {@code eglChooseConfig} for any GLES2 RGB4+ config, then kept only an <em>exact</em>
 * match of the requested channel sizes (default RGB565). API 35+ emulators typically
 * advertise only RGBA8888, so the chooser returned {@code null} and
 * {@code Cb$Ib.createContext} threw {@code IllegalArgumentException} from
 * {@code eglCreateContext}. Software GLES still lists RGB565, which is why switching
 * the AVD "fixed" it.
 *
 * <p>Plain Java (no Android / libGDX), so the attrib list and the fallback order can
 * be tested on the JVM.
 */
public final class EglCompat {

    public static final int EGL_NONE = 0x3038;                  // 12344
    public static final int EGL_CONTEXT_CLIENT_VERSION = 0x3098; // 12440
    public static final int EGL_OPENGL_ES2_BIT = 4;
    public static final int EGL_RENDERABLE_TYPE = 0x3040;        // 12352
    public static final int EGL_RED_SIZE = 0x3024;               // 12324
    public static final int EGL_GREEN_SIZE = 0x3023;             // 12323
    public static final int EGL_BLUE_SIZE = 0x3022;              // 12322
    public static final int EGL_ALPHA_SIZE = 0x3021;             // 12321
    public static final int EGL_DEPTH_SIZE = 0x3025;             // 12325
    public static final int EGL_STENCIL_SIZE = 0x3026;           // 12326

    private EglCompat() {
    }

    /** {@code {EGL_CONTEXT_CLIENT_VERSION, 2, EGL_NONE}} — what createContext must pass. */
    public static int[] contextAttribList() {
        return new int[] {EGL_CONTEXT_CLIENT_VERSION, 2, EGL_NONE};
    }

    public static boolean isWellFormedContextAttribs(int[] attribs) {
        return attribs != null
                && attribs.length >= 3
                && attribs[0] == EGL_CONTEXT_CLIENT_VERSION
                && attribs[1] == 2
                && attribs[attribs.length - 1] == EGL_NONE;
    }

    /**
     * Specs tried in order by the chooser until {@code eglChooseConfig} finds any:
     * original RGB4+GLES2, then RGBA8888+GLES2, then RGB565+GLES2, then any GLES2.
     */
    public static int[][] chooseSpecs() {
        return new int[][] {
            {EGL_RED_SIZE, 4, EGL_GREEN_SIZE, 4, EGL_BLUE_SIZE, 4,
                    EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT, EGL_NONE},
            {EGL_RED_SIZE, 8, EGL_GREEN_SIZE, 8, EGL_BLUE_SIZE, 8, EGL_ALPHA_SIZE, 8,
                    EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT, EGL_NONE},
            {EGL_RED_SIZE, 5, EGL_GREEN_SIZE, 6, EGL_BLUE_SIZE, 5,
                    EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT, EGL_NONE},
            {EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT, EGL_NONE},
        };
    }

    /**
     * Pick a config from {@code configs}, each row {@code {r,g,b,a,depth,stencil}}.
     * Order: exact requested channels with enough depth/stencil; then RGBA8888 or RGB888;
     * then RGB565; then any deep-enough config; then the first row. Returns {@code -1}
     * only when the list is empty, never {@code null}-equivalent when something exists.
     */
    public static int pick(int[][] configs, int reqR, int reqG, int reqB, int reqA,
                           int minDepth, int minStencil) {
        if (configs == null || configs.length == 0) {
            return -1;
        }
        int exact = -1;
        int rgba8888 = -1;
        int rgb565 = -1;
        int deep = -1;
        for (int i = 0; i < configs.length; i++) {
            int[] c = configs[i];
            if (c == null || c.length < 6) {
                continue;
            }
            boolean enough = c[4] >= minDepth && c[5] >= minStencil;
            if (deep < 0 && enough) {
                deep = i;
            }
            if (exact < 0 && enough && c[0] == reqR && c[1] == reqG && c[2] == reqB && c[3] == reqA) {
                exact = i;
            }
            if (rgba8888 < 0 && enough && c[0] == 8 && c[1] == 8 && c[2] == 8 && (c[3] == 8 || c[3] == 0)) {
                rgba8888 = i;
            }
            if (rgb565 < 0 && enough && c[0] == 5 && c[1] == 6 && c[2] == 5 && c[3] == 0) {
                rgb565 = i;
            }
        }
        if (exact >= 0) {
            return exact;
        }
        if (rgba8888 >= 0) {
            return rgba8888;
        }
        if (rgb565 >= 0) {
            return rgb565;
        }
        if (deep >= 0) {
            return deep;
        }
        return 0;
    }

    public static String describe(int[] c) {
        if (c == null || c.length < 6) {
            return "(none)";
        }
        return "r=" + c[0] + " g=" + c[1] + " b=" + c[2] + " a=" + c[3]
                + " depth=" + c[4] + " stencil=" + c[5];
    }
}
