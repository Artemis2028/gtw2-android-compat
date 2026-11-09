package com.invertbit.games.gtw.c;

/**
 * Running-battle HUD geometry: the Pause button sits in the bottom-right, away from Alaska,
 * the contiguous US, Europe and the bottom-left munitions counters.
 *
 * <p>The battle stages use a stretch viewport whose world size is {@link #WORLD_WIDTH} x
 * {@link #WORLD_HEIGHT}. Scene2D tables with {@code fillParent} then pin the 160x100 button
 * at bottom-right with {@link #PAD} px of inset. Both the overlay stage and the running HUD
 * must receive {@code viewport.update(screenW, screenH)} on resize; historically only the
 * overlay did, so the Pause button was laid out for an un-updated (often 0x0) screen and
 * never appeared.
 *
 * <p>Plain Java, so the bounds can be checked on the JVM for common phone resolutions.
 */
public final class BattleHudLayout {

    public static final int WORLD_WIDTH = 1920;
    public static final int WORLD_HEIGHT = 1080;
    public static final int PAUSE_WIDTH = 160;
    public static final int PAUSE_HEIGHT = 100;
    public static final int PAD = 20;

    private BattleHudLayout() {
    }

    /**
     * Both battle stages: overlay (Resume / Quit) first, then the running HUD (Pause).
     * {@link Caj#a(int, int)} updates every returned stage's viewport.
     */
    public static com.badlogic.gdx.f.a.Ci[] stagesToResize(
            com.badlogic.gdx.f.a.Ci overlay, com.badlogic.gdx.f.a.Ci hud) {
        return new com.badlogic.gdx.f.a.Ci[] { overlay, hud };
    }

    /**
     * Pause button in stage/world coordinates (origin bottom-left): {@code {x, y, w, h}}.
     * Bottom-right, inset by {@link #PAD}.
     */
    public static float[] pauseBounds(float worldW, float worldH) {
        return new float[] {
            worldW - PAD - PAUSE_WIDTH,
            PAD,
            PAUSE_WIDTH,
            PAUSE_HEIGHT
        };
    }

    /** Pause button in the stretch-viewport world (1920x1080). */
    public static float[] pauseBoundsWorld() {
        return pauseBounds(WORLD_WIDTH, WORLD_HEIGHT);
    }

    /**
     * Pause button mapped through the stretch viewport onto the screen
     * ({@code {x, y, w, h}}, y-up, same origin as the stage).
     */
    public static float[] pauseBoundsOnScreen(int screenW, int screenH) {
        float[] world = pauseBoundsWorld();
        float sx = screenW / (float) WORLD_WIDTH;
        float sy = screenH / (float) WORLD_HEIGHT;
        return new float[] { world[0] * sx, world[1] * sy, world[2] * sx, world[3] * sy };
    }

    /** True when {@code r = {x, y, w, h}} lies entirely inside {@code [0, screenW] x [0, screenH]}. */
    public static boolean fullyOnScreen(float[] r, float screenW, float screenH) {
        if (r == null || r.length < 4) {
            return false;
        }
        return r[0] >= 0f
                && r[1] >= 0f
                && r[0] + r[2] <= screenW + 0.01f
                && r[1] + r[3] <= screenH + 0.01f
                && r[2] > 0f
                && r[3] > 0f;
    }

    /** Stage-space hit test against the Pause button at the given world size. */
    public static boolean hitPause(float stageX, float stageY, float worldW, float worldH) {
        float[] r = pauseBounds(worldW, worldH);
        return stageX >= r[0] && stageX <= r[0] + r[2]
                && stageY >= r[1] && stageY <= r[1] + r[3];
    }
}
