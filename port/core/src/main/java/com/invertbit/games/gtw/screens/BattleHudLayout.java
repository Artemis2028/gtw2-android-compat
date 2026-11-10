package com.invertbit.games.gtw.screens;

import com.invertbit.games.gtw.GtwGame;

/**
 * Battle HUD geometry. The pop/wpn status line lives in the top bar between
 * the Speed and Pause buttons so it stays in the padded chrome strip instead
 * of drawing across land (Alaska when the player is the USA, and any other
 * northern coast the old left-aligned second row used to cover).
 */
public final class BattleHudLayout {

    public static final float SPEED_WIDTH = 380f;
    public static final float SPEED_HEIGHT = 120f;
    public static final float PAUSE_WIDTH = 240f;
    public static final float TOP_PAD = 16f;
    public static final float STATUS_INSET = 12f;
    /** Nudge the caption up inside the top bar: Russia's Arctic coast can
     * graze the bottom of the 120px chrome on wide screens. */
    public static final float STATUS_PAD_TOP = 8f;
    public static final float STATUS_PAD_BOTTOM = 48f;

    private BattleHudLayout() {}

    /**
     * Status slot in 1920×1080 UI space: {@code x, yFromBottom, width, height}.
     */
    public static float[] statusSlotUi() {
        float x = TOP_PAD + SPEED_WIDTH + TOP_PAD + STATUS_INSET;
        float right = GtwGame.UI_WIDTH - (TOP_PAD + PAUSE_WIDTH + TOP_PAD + STATUS_INSET);
        float width = right - x;
        float height = SPEED_HEIGHT - STATUS_PAD_TOP - STATUS_PAD_BOTTOM;
        float yFromBottom = GtwGame.UI_HEIGHT - TOP_PAD - SPEED_HEIGHT + STATUS_PAD_BOTTOM;
        return new float[]{x, yFromBottom, width, height};
    }

    /**
     * Stretch-viewport mapping of a UI rect to screen pixels with y from the top:
     * {@code x, yFromTop, width, height}.
     */
    public static float[] uiRectToScreen(float[] ui, float screenW, float screenH) {
        float sx = screenW / GtwGame.UI_WIDTH;
        float sy = screenH / GtwGame.UI_HEIGHT;
        float x = ui[0] * sx;
        float w = ui[2] * sx;
        float h = ui[3] * sy;
        float topFromBottom = (ui[1] + ui[3]) * sy;
        float yFromTop = screenH - topFromBottom;
        return new float[]{x, yFromTop, w, h};
    }

    /** Old left-aligned second-row status, kept so tests can show the Alaska overlap we left. */
    public static float[] legacyStatusSlotUi() {
        float x = 20f;
        float height = 40f;
        float yFromBottom = GtwGame.UI_HEIGHT - TOP_PAD - SPEED_HEIGHT - height;
        return new float[]{x, yFromBottom, GtwGame.UI_WIDTH - x, height};
    }
}
