package com.invertbit.games.gtw.b;

/* loaded from: classes.dex */
public abstract class Ca {
    protected com.badlogic.gdx.math.Cl a = new com.badlogic.gdx.math.Cl(0.0f, 0.0f, 0.0f);
    public com.badlogic.gdx.math.Cl b;
    public com.invertbit.games.gtw.a.Ci.Ia c;
    public boolean d;
    com.badlogic.gdx.math.Ck e;

    public Ca(com.badlogic.gdx.math.Cl cl) {
        this.b = cl;
        this.e = com.invertbit.games.gtw.b.e.Ch.a(cl);
    }

    public float a(com.badlogic.gdx.math.Cl cl) {
        return this.b.d(cl);
    }

    public boolean a() {
        return false;
    }

    public final com.badlogic.gdx.math.Cl b() {
        return this.a;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.b.equals(((com.invertbit.games.gtw.b.Ca) obj).b);
    }

    public int hashCode() {
        return this.b.hashCode();
    }

    public void a(com.invertbit.games.gtw.b.Cb cb) {
        this.d = true;
    }
}
