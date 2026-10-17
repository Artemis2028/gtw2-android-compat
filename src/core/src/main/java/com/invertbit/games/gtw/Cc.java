package com.invertbit.games.gtw;

/* loaded from: classes.dex */
public final class Cc {
    static com.invertbit.games.gtw.Cc a;
    public com.badlogic.gdx.Cn b = com.badlogic.gdx.Cf.a.a("GTWGame");

    private Cc() {
    }

    public static com.invertbit.games.gtw.Cc a() {
        if (a == null) {
            a = new com.invertbit.games.gtw.Cc();
        }
        return a;
    }

    public final void b() {
        this.b.a();
    }

    public final boolean a(java.lang.String str) {
        return this.b.d("v1_" + str);
    }

    public final int b(java.lang.String str) {
        return this.b.c("v1_" + str);
    }

    public final java.lang.String c(java.lang.String str) {
        return this.b.b("v1_" + str);
    }

    public final void a(java.lang.String str, int i) {
        this.b.a("v1_" + str, i);
    }

    public final void a(java.lang.String str, java.lang.String str2) {
        this.b.a("v1_" + str, str2);
    }
}
