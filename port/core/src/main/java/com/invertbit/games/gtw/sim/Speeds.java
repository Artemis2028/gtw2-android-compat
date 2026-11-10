package com.invertbit.games.gtw.sim;

/**
 * Battle speed multipliers. The original shipped 1x/2x/4x; 0.25x and 0.5x were
 * added because the ported match felt too quick. Cycle order:
 * 0.25x → 0.5x → 1x → 2x → 4x → 0.25x.
 */
public final class Speeds {
    private Speeds() {}

    public static boolean valid(float s) {
        return s == 0.25f || s == 0.5f || s == 1f || s == 2f || s == 4f;
    }

    /** Next value in the HUD / Settings cycle. */
    public static float next(float s) {
        if (s == 0.25f) {
            return 0.5f;
        }
        if (s == 0.5f) {
            return 1f;
        }
        if (s == 1f) {
            return 2f;
        }
        if (s == 2f) {
            return 4f;
        }
        return 0.25f;
    }

    public static String label(float s) {
        if (s == 0.25f) {
            return "0.25x";
        }
        if (s == 0.5f) {
            return "0.5x";
        }
        if (s == 1f) {
            return "1x";
        }
        if (s == 2f) {
            return "2x";
        }
        if (s == 4f) {
            return "4x";
        }
        return s + "x";
    }

    /** Caption on the battle HUD / pause overlay so the control is easy to spot. */
    public static String hudLabel(float s) {
        return "Speed " + label(s);
    }

    /**
     * Persistence encoding: 25/50/100/200/400. Older saves stored 1, 2 or 4
     * as the raw multiplier and are still accepted.
     */
    public static int toStored(float s) {
        if (s == 0.25f) {
            return 25;
        }
        if (s == 0.5f) {
            return 50;
        }
        return Math.round(s * 100f);
    }

    public static float fromStored(int v) {
        if (v == 1 || v == 2 || v == 4) {
            return v;
        }
        if (v == 25) {
            return 0.25f;
        }
        if (v == 50) {
            return 0.5f;
        }
        if (v == 100) {
            return 1f;
        }
        if (v == 200) {
            return 2f;
        }
        if (v == 400) {
            return 4f;
        }
        return 1f;
    }
}
