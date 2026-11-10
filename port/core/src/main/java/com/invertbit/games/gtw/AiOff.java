package com.invertbit.games.gtw;

/**
 * Test-only switch: when on, the AI opponent is passive. Set from the Android
 * launcher extra {@code ai_off} (adb {@code --ez ai_off true}).
 */
public final class AiOff {
    private static volatile boolean on;

    private AiOff() {}

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
