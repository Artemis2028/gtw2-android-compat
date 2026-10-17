package com.invertbit.games.gtw;

/**
 * Difficulty. The original game had a global EASY/HARD setting (prefs key "v1_level", default EASY).
 * NORMAL was added in between. The level used by the game in progress is {@link #c}: it is set from
 * the new-game picker, or from the save slot on Continue; when unset the global setting applies.
 */
public final class Cb {
    static com.invertbit.games.gtw.Cb a;
    com.invertbit.games.gtw.Cc b = com.invertbit.games.gtw.Cc.a();
    com.invertbit.games.gtw.Cb.Ia c;

    public enum Ia {
        EASY,
        HARD,
        NORMAL;

        public static com.invertbit.games.gtw.Cb.Ia parse(java.lang.String s, com.invertbit.games.gtw.Cb.Ia def) {
            if (s != null) {
                for (com.invertbit.games.gtw.Cb.Ia ia : values()) {
                    if (ia.name().equals(s)) {
                        return ia;
                    }
                }
            }
            return def;
        }

        /** EASY -> NORMAL -> HARD -> EASY */
        public com.invertbit.games.gtw.Cb.Ia next() {
            return this == EASY ? NORMAL : this == NORMAL ? HARD : EASY;
        }
    }

    /** Global setting (default for new games). Missing key = EASY, as in the original. */
    public final com.invertbit.games.gtw.Cb.Ia d() {
        return this.b.a("level") ? com.invertbit.games.gtw.Cb.Ia.parse(this.b.c("level"), com.invertbit.games.gtw.Cb.Ia.EASY) : com.invertbit.games.gtw.Cb.Ia.EASY;
    }

    /** Level of the game in progress. */
    public final com.invertbit.games.gtw.Cb.Ia e() {
        return this.c != null ? this.c : d();
    }

    /** Sets the level of the game in progress (not persisted here; the save slot stores it). */
    public final void b(com.invertbit.games.gtw.Cb.Ia ia) {
        this.c = ia;
    }

    /** Original API: true when the game in progress is EASY. */
    public final boolean a() {
        return e() == com.invertbit.games.gtw.Cb.Ia.EASY;
    }

    /** Original API: stores the global setting. */
    public final void a(com.invertbit.games.gtw.Cb.Ia ia) {
        this.b.a("level", ia.name());
        this.b.b();
    }

    public static com.invertbit.games.gtw.Cb b() {
        if (a == null) {
            a = new com.invertbit.games.gtw.Cb();
        }
        return a;
    }
}
