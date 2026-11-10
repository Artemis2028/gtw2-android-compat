package com.invertbit.games.gtw.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.invertbit.games.gtw.settings.GameSettings;

/** Button click and impact samples (original {@code sounds/button.ogg} and {@code impact.ogg}). */
public final class Sounds {
    private static Sound button;
    private static Sound impact;

    private Sounds() {}

    public static void load() {
        try {
            if (Gdx.files == null || Gdx.audio == null) {
                return;
            }
            if (Gdx.files.internal("sounds/button.ogg").exists()) {
                button = Gdx.audio.newSound(Gdx.files.internal("sounds/button.ogg"));
            }
            if (Gdx.files.internal("sounds/impact.ogg").exists()) {
                impact = Gdx.audio.newSound(Gdx.files.internal("sounds/impact.ogg"));
            }
        } catch (Exception ignored) {
            // Headless / missing assets: stay silent.
        }
    }

    public static void playButton() {
        play(button, GameSettings.get().scaledVolume(GameSettings.BUTTON_BASE));
    }

    public static void playImpact() {
        play(impact, GameSettings.get().scaledVolume(GameSettings.IMPACT_BASE));
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
        if (button != null) {
            button.dispose();
            button = null;
        }
        if (impact != null) {
            impact.dispose();
            impact = null;
        }
    }
}
