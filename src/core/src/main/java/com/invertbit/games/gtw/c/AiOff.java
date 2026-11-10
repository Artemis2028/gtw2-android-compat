package com.invertbit.games.gtw.c;

/**
 * Test-only switch: when on, the AI opponent is passive (no launches, attacks or alliance
 * picks). The world still runs and the player's own actions are unchanged.
 *
 * <p>Default off, so normal play is identical. Turn it on from an intent extra or a debug
 * property, with no UI:
 * {@code adb shell am start -n com.invertbit.games.gtw.android.src/com.invertbit.games.gtw.android.AndroidLauncher --ez ai_off true}
 * or {@code adb shell setprop debug.gtw.ai_off true} (also {@code -Dgtw.ai_off=true}).
 *
 * <p>Plain Java (no libGDX / Android), so the rules can be tested on the JVM.
 */
public final class AiOff {

    private static volatile boolean on;

    private AiOff() {
    }

    public static boolean isOn() {
        return on;
    }

    public static void set(boolean value) {
        on = value;
    }

    /** Back to the default (off). Tests should call this so they do not leak into later cases. */
    public static void reset() {
        on = false;
    }
}
