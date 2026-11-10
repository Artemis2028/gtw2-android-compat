package com.invertbit.games.gtw.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.invertbit.games.gtw.settings.GameSettings;

/**
 * Select/button click ({@code sounds/select.ogg}), silo and submarine launch
 * cues, and impact ({@code impact.ogg}). Launch samples are throttled so a
 * salvo cannot stack dozens of overlapping voices.
 */
public final class Sounds {
    static final long LAUNCH_THROTTLE_MS = 150L;

    private static Sound button;
    private static Sound impact;
    private static Sound launchSilo;
    private static Sound launchSub;
    private static long lastLaunchSoundMs;

    private Sounds() {}

    public static void load() {
        try {
            if (Gdx.files == null || Gdx.audio == null) {
                return;
            }
            button = loadOne("sounds/select.ogg");
            impact = loadOne("sounds/impact.ogg");
            launchSilo = loadOne("sounds/launch_silo.ogg");
            launchSub = loadOne("sounds/launch_sub.ogg");
        } catch (Exception ignored) {
            // Headless / missing assets: stay silent.
        }
    }

    private static Sound loadOne(String path) {
        if (Gdx.files.internal(path).exists()) {
            return Gdx.audio.newSound(Gdx.files.internal(path));
        }
        return null;
    }

    public static void playButton() {
        play(button, GameSettings.get().scaledVolume(GameSettings.BUTTON_BASE));
    }

    public static void playImpact() {
        play(impact, GameSettings.get().scaledVolume(GameSettings.IMPACT_BASE));
    }

    public static void playSiloLaunch() {
        playLaunch(launchSilo);
    }

    public static void playSubLaunch() {
        playLaunch(launchSub);
    }

    private static void playLaunch(Sound sound) {
        float volume = GameSettings.get().scaledVolume(GameSettings.LAUNCH_BASE);
        if (sound == null || volume <= 0f) {
            return;
        }
        if (!tryAcquireLaunchSlot(System.currentTimeMillis())) {
            return;
        }
        play(sound, volume);
    }

    /** Visible for tests: true if a launch cue may start at {@code nowMs}. */
    static boolean tryAcquireLaunchSlot(long nowMs) {
        if (lastLaunchSoundMs != 0L && nowMs - lastLaunchSoundMs < LAUNCH_THROTTLE_MS) {
            return false;
        }
        lastLaunchSoundMs = nowMs;
        return true;
    }

    static void resetLaunchThrottle() {
        lastLaunchSoundMs = 0L;
    }

    private static void play(Sound sound, float volume) {
        if (sound == null || volume <= 0f) {
            return;
        }
        try {
            sound.play(volume);
        } catch (Exception ignored) {
        }
    }

    public static void dispose() {
        button = disposeOne(button);
        impact = disposeOne(impact);
        launchSilo = disposeOne(launchSilo);
        launchSub = disposeOne(launchSub);
        lastLaunchSoundMs = 0L;
    }

    private static Sound disposeOne(Sound sound) {
        if (sound != null) {
            sound.dispose();
        }
        return null;
    }
}
