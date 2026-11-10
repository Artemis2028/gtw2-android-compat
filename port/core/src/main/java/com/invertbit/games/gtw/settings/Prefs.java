package com.invertbit.games.gtw.settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Views of the "GTWGame" preferences file with a key prefix. {@code v1_} is the
 * original single-save / settings namespace; save slots use {@code v1_sN_} / {@code v1_auto_}.
 */
public final class Prefs {
    private static PrefStore override;
    private final PrefStore store;
    private final String prefix;

    private Prefs(PrefStore store, String prefix) {
        this.store = store;
        this.prefix = prefix;
    }

    /** Tests: use this store for every subsequent {@link #v1()} / {@link #of(String)}. */
    public static void use(PrefStore store) {
        override = store;
    }

    public static void resetForTest() {
        override = null;
    }

    public static Prefs v1() {
        return of("v1_");
    }

    public static Prefs of(String prefix) {
        return new Prefs(active(), prefix);
    }

    static PrefStore active() {
        if (override != null) {
            return override;
        }
        return new GdxPrefStore();
    }

    public boolean contains(String key) {
        return store.contains(prefix + key);
    }

    public int getInt(String key) {
        return store.getInt(prefix + key);
    }

    public String getString(String key) {
        return store.getString(prefix + key);
    }

    public boolean getBoolean(String key) {
        return store.getBoolean(prefix + key);
    }

    public Prefs putInt(String key, int value) {
        store.putInt(prefix + key, value);
        return this;
    }

    public Prefs putString(String key, String value) {
        store.putString(prefix + key, value);
        return this;
    }

    public Prefs putBoolean(String key, boolean value) {
        store.putBoolean(prefix + key, value);
        return this;
    }

    public void flush() {
        store.flush();
    }

    private static final class GdxPrefStore implements PrefStore {
        /**
         * One shared instance: on Android every {@code getPreferences} call returns a new
         * wrapper with its own pending editor, so a put on one and a flush on another
         * would silently drop the write.
         */
        private static Preferences shared;

        private static synchronized Preferences prefs() {
            if (shared == null) {
                shared = Gdx.app.getPreferences("GTWGame");
            }
            return shared;
        }

        @Override public void putString(String key, String value) { prefs().putString(key, value); }
        @Override public void putInt(String key, int value) { prefs().putInteger(key, value); }
        @Override public void putBoolean(String key, boolean value) { prefs().putBoolean(key, value); }
        @Override public String getString(String key) { return prefs().getString(key, ""); }
        @Override public int getInt(String key) { return prefs().getInteger(key, 0); }
        @Override public boolean getBoolean(String key) { return prefs().getBoolean(key, false); }
        @Override public boolean contains(String key) { return prefs().contains(key); }
        @Override public void flush() { prefs().flush(); }
    }
}
