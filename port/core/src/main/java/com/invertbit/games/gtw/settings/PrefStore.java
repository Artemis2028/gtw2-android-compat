package com.invertbit.games.gtw.settings;

/** Key/value store behind prefs (libGDX Preferences, or an in-memory map in tests). */
public interface PrefStore {
    void putString(String key, String value);
    void putInt(String key, int value);
    void putBoolean(String key, boolean value);
    String getString(String key);
    int getInt(String key);
    boolean getBoolean(String key);
    boolean contains(String key);
    void flush();
}
