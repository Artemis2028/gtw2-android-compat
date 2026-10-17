package com.invertbit.games.gtw;

/* loaded from: classes.dex */
public final class Cc {
    static com.invertbit.games.gtw.Cc a;
    public com.badlogic.gdx.Cn b;
    /** Key prefix. "v1_" is the original single-save/settings namespace; save slots use their own prefix. */
    final java.lang.String p;

    private Cc() {
        this.b = com.badlogic.gdx.Cf.a.a("GTWGame");
        this.p = "v1_";
    }

    /** A view of the same "GTWGame" preferences with a different key prefix (used for save slots). */
    public Cc(java.lang.String prefix) {
        this.b = a().b;
        this.p = prefix;
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
        return this.b.d(this.p + str);
    }

    public final int b(java.lang.String str) {
        return this.b.c(this.p + str);
    }

    public final java.lang.String c(java.lang.String str) {
        return this.b.b(this.p + str);
    }

    public final void a(java.lang.String str, int i) {
        this.b.a(this.p + str, i);
    }

    public final void a(java.lang.String str, java.lang.String str2) {
        this.b.a(this.p + str, str2);
    }
}
