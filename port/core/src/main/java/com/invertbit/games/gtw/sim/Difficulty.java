package com.invertbit.games.gtw.sim;

/**
 * Difficulty. Original prefs default is EASY; HARD was the other shipped value;
 * NORMAL was added between them. Enum order is EASY, HARD, NORMAL to match the
 * original {@code gtw.Cb.Ia}.
 */
public enum Difficulty {
    EASY,
    HARD,
    NORMAL;

    private static Difficulty inProgress;

    public static Difficulty current() {
        return inProgress != null ? inProgress : EASY;
    }

    /** Level of the game in progress (not persisted here). */
    public static void setInProgress(Difficulty level) {
        inProgress = level;
    }

    public static void reset() {
        inProgress = null;
    }

    public Difficulty next() {
        return this == EASY ? NORMAL : this == NORMAL ? HARD : EASY;
    }
}
