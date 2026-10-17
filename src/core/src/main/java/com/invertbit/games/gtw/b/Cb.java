package com.invertbit.games.gtw.b;

/* loaded from: classes.dex */
public abstract class Cb extends com.invertbit.games.gtw.b.Ca {
    public int e;
    public int f;
    public float g;

    public Cb(com.badlogic.gdx.math.Cl cl) {
        super(cl);
    }

    public final boolean a(com.invertbit.games.gtw.b.Ca ca) {
        if (ca == null || this.c.equals(ca.c) || (ca instanceof com.invertbit.games.gtw.b.Cc)) {
            return false;
        }
        return (!(ca instanceof com.invertbit.games.gtw.b.Cj) || ((com.invertbit.games.gtw.b.Cj) ca).j) && this.e > 0;
    }

    public java.lang.String toString() {
        return java.lang.String.format("%s x %s MT", java.lang.Integer.valueOf(this.e), java.lang.Integer.valueOf(this.f));
    }
}
