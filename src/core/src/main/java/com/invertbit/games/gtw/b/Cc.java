package com.invertbit.games.gtw.b;

/* loaded from: classes.dex */
public class Cc extends com.invertbit.games.gtw.b.Cb {
    public com.badlogic.gdx.math.Cl h;
    public com.badlogic.gdx.math.Cl i;
    public com.badlogic.gdx.math.Cl j;
    public com.invertbit.games.gtw.b.Ca k;
    public float l;
    public float m;
    public float n;
    public float o;

    public Cc(com.badlogic.gdx.math.Cl cl) {
        super(cl);
        this.o = 30.0f;
        this.j = new com.badlogic.gdx.math.Cl(cl);
        this.a = new com.badlogic.gdx.math.Cl(15.0f, 0.0f, 19.0f);
        this.h = cl.a();
        this.h.a += this.a.a;
        this.h.c += this.a.c;
    }

    @Override // com.invertbit.games.gtw.b.Ca
    public final boolean a() {
        return true;
    }

    @Override // com.invertbit.games.gtw.b.Ca
    public final float a(com.badlogic.gdx.math.Cl cl) {
        if (this.k != null) {
            return Float.MAX_VALUE;
        }
        return super.a(cl);
    }
}
