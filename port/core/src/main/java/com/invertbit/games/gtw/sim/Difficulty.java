package com.invertbit.games.gtw.sim;

import com.invertbit.games.gtw.settings.Prefs;

/**
 * Difficulty. Original prefs default is EASY; HARD was the other shipped value;
 * NORMAL was added between them. Enum order is EASY, HARD, NORMAL to match the
 * original {@code gtw.Cb.Ia}. The global choice persists as {@code v1_level}.
 */
public enum Difficulty {
    EASY,
    HARD,
    NORMAL;

    private static Difficulty inProgress;

    public static Difficulty current() {
        return inProgress != null ? inProgress : global();
    }

    /** Level of the game in progress. */
    public static void setInProgress(Difficulty level) {
        inProgress = level;
    }

    public static void reset() {
        inProgress = null;
    }

    public Difficulty next() {
        return this == EASY ? NORMAL : this == NORMAL ? HARD : EASY;
    }

    public static Difficulty parse(String name, Difficulty fallback) {
        if (name == null) {
            return fallback;
        }
        for (Difficulty d : values()) {
            if (d.name().equals(name)) {
                return d;
            }
        }
        return fallback;
    }

    /** Global setting from {@code v1_level}; EASY when unset (original default). */
    public static Difficulty global() {
        try {
            Prefs p = Prefs.v1();
            return p.contains("level") ? parse(p.getString("level"), EASY) : EASY;
        } catch (RuntimeException e) {
            return EASY;
        }
    }

    public static void setGlobal(Difficulty level) {
        Prefs.v1().putString("level", level.name()).flush();
    }
}
