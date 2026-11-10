package com.invertbit.games.gtw.settings;

import java.util.LinkedHashMap;
import java.util.Map;

/** In-memory Preferences stand-in used by JVM tests. */
public final class MemoryPrefStore implements PrefStore {
    public final Map<String, Object> data = new LinkedHashMap<String, Object>();

    @Override public void putString(String key, String value) { data.put(key, value); }
    @Override public void putInt(String key, int value) { data.put(key, Integer.valueOf(value)); }
    @Override public void putBoolean(String key, boolean value) { data.put(key, Boolean.valueOf(value)); }

    @Override
    public String getString(String key) {
        Object v = data.get(key);
        return v == null ? "" : String.valueOf(v);
    }

    @Override
    public int getInt(String key) {
        Object v = data.get(key);
        return v instanceof Number ? ((Number) v).intValue() : 0;
    }

    @Override
    public boolean getBoolean(String key) {
        Object v = data.get(key);
        return v instanceof Boolean ? ((Boolean) v).booleanValue() : false;
    }

    @Override public boolean contains(String key) { return data.containsKey(key); }
    @Override public void flush() { }
}
