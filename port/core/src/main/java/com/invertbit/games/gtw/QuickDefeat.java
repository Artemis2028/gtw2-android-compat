package com.invertbit.games.gtw;

/**
 * Debug-only: after the DEFCON countdown the opponent is marked defeated so the
 * post-match stats screen can be reached without playing out a round. Set from
 * the Android launcher extra {@code quick_defeat} (adb {@code --ez quick_defeat true}).
 */
public final class QuickDefeat {
    private static volatile boolean on;

    private QuickDefeat() {}

    public static void set(boolean value) {
        on = value;
    }

    public static boolean isOn() {
        return on;
    }

    public static void reset() {
        on = false;
    }
}
