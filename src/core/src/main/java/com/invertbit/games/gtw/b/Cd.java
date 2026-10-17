package com.invertbit.games.gtw.b;

/* loaded from: classes.dex */
public class Cd extends com.invertbit.games.gtw.b.Ca {
    public int e;
    public int f;

    public Cd(com.badlogic.gdx.math.Cl cl, int i) {
        super(cl);
        this.e = i;
        this.a = new com.badlogic.gdx.math.Cl(24.0f, 0.0f, 13.87f);
    }

    @Override // com.invertbit.games.gtw.b.Ca
    public final void a(com.invertbit.games.gtw.b.Cb cb) {
        super.a(cb);
        this.f = java.lang.Math.min(cb.f, this.e);
        this.e -= cb.f;
        if (this.e < 0) {
            this.e = 0;
        }
        if (this.e <= 0) {
            this.d = true;
        } else {
            this.d = false;
        }
    }

    public java.lang.String toString() {
        return java.lang.String.format("%s mln", java.lang.Integer.valueOf(this.e));
    }
}
