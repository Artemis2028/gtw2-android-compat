package com.invertbit.games.gtw;

/* loaded from: classes.dex */
public final class Cb {
    static com.invertbit.games.gtw.Cb a;
    com.invertbit.games.gtw.Cc b = com.invertbit.games.gtw.Cc.a();

    public enum Ia {
        EASY,
        HARD
    }

    public final boolean a() {
        if (this.b.a("level")) {
            return com.invertbit.games.gtw.Cb.Ia.EASY.name().equals(this.b.c("level"));
        }
        return true;
    }

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
