package com.invertbit.games.gtw.c;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * The running HUD (Pause) must be resized with the overlay, and the button must sit on-screen
 * at common phone resolutions without covering the left-side map or the munitions counters.
 */
public class BattleHudLayoutTest {

    private static final int[][] SCREENS = {
        {1080, 2400},   // tall phone, portrait
        {1344, 2992},   // tall phone, portrait
        {2856, 1280},   // landscape tablet/phone
        {2400, 1080},   // Medium_Phone landscape (the API 37 emulator)
    };

    @Test
    public void stagesToResizeIncludesRunningHudAndOverlay() throws Exception {
        com.badlogic.gdx.f.a.Ci overlay = (com.badlogic.gdx.f.a.Ci) allocate(com.badlogic.gdx.f.a.Ci.class);
        com.badlogic.gdx.f.a.Ci hud = (com.badlogic.gdx.f.a.Ci) allocate(com.badlogic.gdx.f.a.Ci.class);
        com.badlogic.gdx.f.a.Ci[] stages = BattleHudLayout.stagesToResize(overlay, hud);
        assertEquals(2, stages.length);
        assertSame("overlay first", overlay, stages[0]);
        assertSame("running HUD must be resized too", hud, stages[1]);
    }

    @Test
    public void resizeUpdatesBothStagesWithoutThrowingWhenViewportsAreMissing() throws Exception {
        Caj c = (Caj) allocate(Caj.class);
        c.t = (com.badlogic.gdx.f.a.Ci) allocate(com.badlogic.gdx.f.a.Ci.class);
        c.u = (com.badlogic.gdx.f.a.Ci) allocate(com.badlogic.gdx.f.a.Ci.class);
        c.pause().start(1_000_000L);
        c.a(2400, 1080);
        com.badlogic.gdx.f.a.Ci[] stages = BattleHudLayout.stagesToResize(c.t, c.u);
        assertSame(c.t, stages[0]);
        assertSame(c.u, stages[1]);
    }

    @Test
    public void pauseButtonFitsCommonResolutions() {
        assertTrue(BattleHudLayout.fullyOnScreen(
                BattleHudLayout.pauseBoundsWorld(),
                BattleHudLayout.WORLD_WIDTH,
                BattleHudLayout.WORLD_HEIGHT));
        for (int[] screen : SCREENS) {
            int w = screen[0];
            int h = screen[1];
            float[] r = BattleHudLayout.pauseBoundsOnScreen(w, h);
            assertTrue(w + "x" + h + " off-screen: " + java.util.Arrays.toString(r),
                    BattleHudLayout.fullyOnScreen(r, w, h));
            assertTrue(w + "x" + h + " should sit in the right half (not over Alaska/US/stats)",
                    r[0] > w * 0.5f);
            assertTrue(w + "x" + h + " should sit in the bottom third",
                    r[1] + r[3] < h * 0.35f || r[1] < h * 0.25f);
        }
    }

    @Test
    public void pauseHitTestMatchesBoundsAndMissesTheMap() {
        float[] r = BattleHudLayout.pauseBoundsWorld();
        float cx = r[0] + r[2] / 2f;
        float cy = r[1] + r[3] / 2f;
        assertTrue(BattleHudLayout.hitPause(cx, cy,
                BattleHudLayout.WORLD_WIDTH, BattleHudLayout.WORLD_HEIGHT));
        assertFalse("a tap on the left-side munitions / US must miss the button",
                BattleHudLayout.hitPause(80f, 80f,
                        BattleHudLayout.WORLD_WIDTH, BattleHudLayout.WORLD_HEIGHT));
        assertFalse(BattleHudLayout.hitPause(0f, 0f,
                BattleHudLayout.WORLD_WIDTH, BattleHudLayout.WORLD_HEIGHT));
    }

    private static Object allocate(Class<?> type) throws Exception {
        java.lang.reflect.Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        return ((sun.misc.Unsafe) f.get(null)).allocateInstance(type);
    }
}
