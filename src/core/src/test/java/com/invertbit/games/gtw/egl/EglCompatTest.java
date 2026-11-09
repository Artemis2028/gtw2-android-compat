package com.invertbit.games.gtw.egl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EglCompatTest {

    @Test
    public void contextAttribListIsGles2TerminatedWithEglNone() {
        int[] attribs = EglCompat.contextAttribList();
        assertTrue(EglCompat.isWellFormedContextAttribs(attribs));
        assertEquals(EglCompat.EGL_CONTEXT_CLIENT_VERSION, attribs[0]);
        assertEquals(2, attribs[1]);
        assertEquals(EglCompat.EGL_NONE, attribs[attribs.length - 1]);
        assertArrayEquals(new int[] {0x3098, 2, 0x3038}, attribs);
    }

    @Test
    public void malformedAttribListsAreRejected() {
        assertFalse(EglCompat.isWellFormedContextAttribs(null));
        assertFalse(EglCompat.isWellFormedContextAttribs(new int[] {0x3098, 2}));
        assertFalse(EglCompat.isWellFormedContextAttribs(new int[] {0x3098, 2, 0}));
        assertFalse(EglCompat.isWellFormedContextAttribs(new int[] {0x3098, 3, EglCompat.EGL_NONE}));
    }

    @Test
    public void everyChooseSpecEndsInEglNoneAndRequestsGles2() {
        for (int[] spec : EglCompat.chooseSpecs()) {
            assertEquals(EglCompat.EGL_NONE, spec[spec.length - 1]);
            boolean gles2 = false;
            for (int i = 0; i + 1 < spec.length; i += 2) {
                if (spec[i] == EglCompat.EGL_RENDERABLE_TYPE && spec[i + 1] == EglCompat.EGL_OPENGL_ES2_BIT) {
                    gles2 = true;
                }
            }
            assertTrue("spec must request EGL_RENDERABLE_TYPE = GLES2", gles2);
        }
    }

    @Test
    public void pickPrefersExactRequestedRgb565() {
        int[][] list = {
            {8, 8, 8, 8, 24, 8},
            {5, 6, 5, 0, 16, 0},
            {8, 8, 8, 0, 16, 0},
        };
        assertEquals(1, EglCompat.pick(list, 5, 6, 5, 0, 16, 0));
    }

    @Test
    public void pickFallsBackToRgba8888WhenRgb565IsMissing() {
        // API 35+ emulator: only RGBA8888 GLES2 configs, the original chooser returned null
        int[][] list = {
            {8, 8, 8, 8, 24, 8},
            {8, 8, 8, 8, 16, 0},
        };
        int idx = EglCompat.pick(list, 5, 6, 5, 0, 16, 0);
        assertEquals(0, idx);
        assertEquals("r=8 g=8 b=8 a=8 depth=24 stencil=8", EglCompat.describe(list[idx]));
    }

    @Test
    public void pickNeverReturnsMinusOneWhenTheListIsNonEmpty() {
        int[][] list = {{2, 2, 2, 0, 0, 0}};
        assertEquals(0, EglCompat.pick(list, 5, 6, 5, 0, 16, 0));
    }

    @Test
    public void pickReturnsMinusOneOnlyForAnEmptyList() {
        assertEquals(-1, EglCompat.pick(new int[0][], 5, 6, 5, 0, 16, 0));
        assertEquals(-1, EglCompat.pick(null, 5, 6, 5, 0, 16, 0));
    }
}
