package com.invertbit.games.b;

/* loaded from: classes.dex */
public final class Ca {
    public com.badlogic.gdx.math.Ck a;
    public com.badlogic.gdx.math.Ck b;
    public com.badlogic.gdx.math.Ck c;
    public java.util.List<com.badlogic.gdx.math.Ck> d;
    public float e;
    public float f;
    public com.badlogic.gdx.math.Ck g = new com.badlogic.gdx.math.Ck();
    public com.badlogic.gdx.graphics.a.Cd h;
    public com.badlogic.gdx.graphics.a.e.Ci i;
    public java.util.List<com.badlogic.gdx.graphics.a.Cg> j;
    public java.util.List<com.badlogic.gdx.graphics.a.Ce> k;
    public com.invertbit.games.b.a.Cb l;
    public int m;

    public Ca(com.badlogic.gdx.math.Ck ck, com.badlogic.gdx.math.Ck ck2, float f, com.badlogic.gdx.graphics.a.Cd cd, com.badlogic.gdx.graphics.a.e.Ci ci) {
        com.badlogic.gdx.math.Ck ck3;
        this.a = a(ck);
        this.b = ck2;
        this.h = cd;
        this.i = ci;
        float f2 = ck2.d - ck.d;
        float fAbs = ck2.e - (java.lang.Math.abs(f2) / 2.0f);
        if (fAbs < 0.0f) {
            ck3 = new com.badlogic.gdx.math.Ck((f2 / 2.0f) + ck.d, 10.0f);
        } else {
            ck3 = new com.badlogic.gdx.math.Ck((f2 / 2.0f) + ck.d, fAbs);
        }
        this.c = ck3;
        this.d = new java.util.ArrayList();
        this.d.add(new com.badlogic.gdx.math.Ck(ck));
        this.e = 0.0f;
        this.f = f;
        this.j = new java.util.ArrayList();
        this.k = new java.util.ArrayList();
    }

    public static com.badlogic.gdx.math.Ck a(com.badlogic.gdx.math.Ck ck) {
        if (ck.d < 0.0f) {
            ck.a(0.0f, ck.e);
        }
        return ck;
    }
}
