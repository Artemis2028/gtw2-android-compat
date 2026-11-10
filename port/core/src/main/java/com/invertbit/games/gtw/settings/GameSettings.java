package com.invertbit.games.gtw.settings;

import com.invertbit.games.gtw.sim.Speeds;

/**
 * Sound, volume and default battle speed. Plain Java so the rules can be tested on the JVM;
 * persisted under the existing {@code v1_} prefs prefix.
 *
 * <ul>
 *   <li>{@code v1_sound} — original on/off boolean</li>
 *   <li>{@code v1_volume} — 25, 50, 75 or 100 (percent of the original play loudness)</li>
 *   <li>{@code v1_speed} — 25/50/100/200/400 (0.25x…4x). Older saves stored 1, 2 or 4
 *       as the raw multiplier and are still accepted.</li>
 * </ul>
 */
public final class GameSettings {

    public static final float BUTTON_BASE = 0.45f;
    public static final float IMPACT_BASE = 1.0f;

    private static GameSettings INSTANCE = new GameSettings();

    private boolean soundOn = true;
    private int volumePercent = 100;
    private float defaultSpeed = 1f;

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

    public float defaultSpeed() {
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

    /** Accepts 0.25, 0.5, 1, 2 or 4. Other values are ignored. */
    public void setDefaultSpeed(float s) {
        if (Speeds.valid(s)) {
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

    /** Cycle 1x → 2x → 4x → 0.25x → 0.5x → 1x. Returns the new multiplier. */
    public float cycleDefaultSpeed() {
        defaultSpeed = Speeds.next(defaultSpeed);
        return defaultSpeed;
    }

    public String soundLabel() {
        return soundOn ? "Sound on" : "Sound off";
    }

    public String volumeLabel() {
        return "Volume: " + volumePercent + "%";
    }

    public String defaultSpeedLabel() {
        return "Speed: " + Speeds.label(defaultSpeed);
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
        Prefs prefs = Prefs.v1();
        if (prefs.contains("sound")) {
            gs.setSoundOn(prefs.getBoolean("sound"));
        }
        if (prefs.contains("volume")) {
            gs.setVolumePercent(prefs.getInt("volume"));
        }
        if (prefs.contains("speed")) {
            gs.setDefaultSpeed(Speeds.fromStored(prefs.getInt("speed")));
        }
    }

    public void saveSound() {
        Prefs.v1().putBoolean("sound", soundOn);
        Prefs.v1().flush();
    }

    public void saveVolume() {
        Prefs.v1().putInt("volume", volumePercent);
        Prefs.v1().flush();
    }

    public void saveSpeed() {
        Prefs.v1().putInt("speed", Speeds.toStored(defaultSpeed));
        Prefs.v1().flush();
    }
}
