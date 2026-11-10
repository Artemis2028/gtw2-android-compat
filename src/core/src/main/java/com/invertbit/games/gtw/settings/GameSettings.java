package com.invertbit.games.gtw.settings;

/**
 * Sound, volume and default battle speed. Plain Java so the rules can be tested on the JVM;
 * {@link com.invertbit.games.gtw.Cc} persists them under the existing {@code v1_} prefs prefix.
 *
 * <ul>
 *   <li>{@code v1_sound} — original on/off boolean</li>
 *   <li>{@code v1_volume} — 25, 50, 75 or 100 (percent of the original play loudness)</li>
 *   <li>{@code v1_speed} — 1, 2 or 4 (applied by a new battle via {@code BattlePause.setDefaultSpeed})</li>
 * </ul>
 */
public final class GameSettings {

    public static final float BUTTON_BASE = 0.45f;
    public static final float IMPACT_BASE = 1.0f;

    private static GameSettings INSTANCE = new GameSettings();

    private boolean soundOn = true;
    private int volumePercent = 100;
    private int defaultSpeed = 1;

    public static GameSettings get() {
        return INSTANCE;
    }

    /** Tests only: drop any leftover singleton state. */
    public static void resetForTest() {
        INSTANCE = new GameSettings();
    }

    public boolean soundOn() {
        return soundOn;
    }

    public int volumePercent() {
        return volumePercent;
    }

    public int defaultSpeed() {
        return defaultSpeed;
    }

    public void setSoundOn(boolean on) {
        soundOn = on;
    }

    /** Accepts 25, 50, 75 or 100. Other values are ignored. */
    public void setVolumePercent(int p) {
        if (p == 25 || p == 50 || p == 75 || p == 100) {
            volumePercent = p;
        }
    }

    /** Accepts 1, 2 or 4. Other values are ignored. */
    public void setDefaultSpeed(int s) {
        if (s == 1 || s == 2 || s == 4) {
            defaultSpeed = s;
        }
    }

    public boolean toggleSound() {
        soundOn = !soundOn;
        return soundOn;
    }

    /** Cycle 100% → 25% → 50% → 75% → 100%. Returns the new percent. */
    public int cycleVolume() {
        if (volumePercent == 100) {
            volumePercent = 25;
        } else if (volumePercent == 25) {
            volumePercent = 50;
        } else if (volumePercent == 50) {
            volumePercent = 75;
        } else {
            volumePercent = 100;
        }
        return volumePercent;
    }

    /** Cycle 1x → 2x → 4x → 1x. Returns the new multiplier. */
    public int cycleDefaultSpeed() {
        if (defaultSpeed == 1) {
            defaultSpeed = 2;
        } else if (defaultSpeed == 2) {
            defaultSpeed = 4;
        } else {
            defaultSpeed = 1;
        }
        return defaultSpeed;
    }

    public String soundLabel() {
        return soundOn ? "Sound on" : "Sound off";
    }

    public String volumeLabel() {
        return "Volume: " + volumePercent + "%";
    }

    public String defaultSpeedLabel() {
        return "Speed: " + defaultSpeed + "x";
    }

    /**
     * Volume passed to libGDX {@code Sound.play}. {@code base} is the original hardcoded
     * loudness (0.45 for the button click, 1.0 for impacts). Muted sound yields 0.
     */
    public float scaledVolume(float base) {
        if (!soundOn) {
            return 0.0f;
        }
        return base * (volumePercent / 100.0f);
    }

    /** Read prefs if present; missing keys keep the built-in defaults. */
    public static void loadFromPrefs() {
        GameSettings gs = get();
        com.invertbit.games.gtw.Cc prefs = com.invertbit.games.gtw.Cc.a();
        if (prefs.a("sound")) {
            gs.setSoundOn(prefs.b.a("v1_sound"));
        }
        if (prefs.a("volume")) {
            gs.setVolumePercent(prefs.b("volume"));
        }
        if (prefs.a("speed")) {
            gs.setDefaultSpeed(prefs.b("speed"));
        }
    }

    public void saveSound() {
        com.invertbit.games.gtw.Cc.a().b.a("v1_sound", soundOn);
        com.invertbit.games.gtw.Cc.a().b();
    }

    public void saveVolume() {
        com.invertbit.games.gtw.Cc.a().a("volume", volumePercent);
        com.invertbit.games.gtw.Cc.a().b();
    }

    public void saveSpeed() {
        com.invertbit.games.gtw.Cc.a().a("speed", defaultSpeed);
        com.invertbit.games.gtw.Cc.a().b();
    }
}
