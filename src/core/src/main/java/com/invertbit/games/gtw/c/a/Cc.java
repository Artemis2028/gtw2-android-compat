package com.invertbit.games.gtw.c.a;

/* loaded from: classes.dex */
public class Cc extends com.badlogic.gdx.f.a.a.Ce {
    protected com.badlogic.gdx.graphics.Co K;
    protected com.badlogic.gdx.graphics.Co L;
    protected boolean M;
    protected com.invertbit.games.gtw.b.d.Cj N;
    protected int O;
    protected int P;
    protected com.invertbit.games.gtw.b.e.Cg Q;
    protected com.badlogic.gdx.graphics.Cb R;
    protected java.lang.String S;
    protected com.badlogic.gdx.f.a.a.Cc T;
    public boolean U;
    com.invertbit.games.gtw.b.a.Ca V;
    com.invertbit.games.gtw.b.a.Cb W;
    boolean X;

    static boolean a(com.invertbit.games.gtw.c.a.Cc cc) {
        return cc.U;
    }

    public Cc(java.lang.String str, com.invertbit.games.gtw.b.e.Cg cg, com.badlogic.gdx.graphics.Cb cb, com.badlogic.gdx.graphics.Cb cb2) {
        this(550, 250, str, cg, cb, cb2);
        this.X = false;
    }

    private Cc(int i, int i2, java.lang.String str, com.invertbit.games.gtw.b.e.Cg cg, com.badlogic.gdx.graphics.Cb cb, com.badlogic.gdx.graphics.Cb cb2) {
        this.X = true;
        this.U = true;
        this.O = i;
        this.P = i2;
        this.S = str;
        this.Q = cg;
        this.R = cb;
        a(0.0f, 0.0f, i, i2);
        a(i, i2);
        this.N = com.invertbit.games.gtw.b.d.Cj.a();
        this.W = com.invertbit.games.gtw.b.a.Cb.a();
        this.V = com.invertbit.games.gtw.b.a.Ca.a();
        this.K = com.invertbit.games.gtw.b.a.Cb.a(i, i2, cb2);
        this.L = com.invertbit.games.gtw.b.a.Cb.a(i, i2, new com.badlogic.gdx.graphics.Cb(0.0f, 0.5f, 1.0f, 0.6f));
        l();
    }

    public Cc(int i, int i2, java.lang.String str, com.invertbit.games.gtw.b.e.Cg cg, com.badlogic.gdx.graphics.Cb cb) {
        this(i, i2, str, cg, cb, new com.badlogic.gdx.graphics.Cb(0.0f, 0.52f, 1.0f, 0.3f));
    }

    protected void l() {
        this.T = new com.badlogic.gdx.f.a.a.Cc(this.S, new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(this.Q), this.R));
        this.T.i();
        c(this.T).a(this.O).b(this.P).m().h().g();
        m();
    }

    protected final void m() {
        a(new com.invertbit.games.gtw.c.a.Cd(this));
    }

    @Override // com.badlogic.gdx.f.a.a.Ce, com.badlogic.gdx.f.a.a.Cs, com.badlogic.gdx.f.a.Ce, com.badlogic.gdx.f.a.Cb
    public final void a(com.badlogic.gdx.graphics.g2d.Ca ca, float f) {
        c(ca, f);
        super.a(ca, f);
    }

    protected void c(com.badlogic.gdx.graphics.g2d.Ca ca, float f) {
        com.badlogic.gdx.graphics.Cb cb = this.o;
        ca.a(cb.H, cb.I, cb.J, cb.K * f);
        if (this.X && this.M) {
            ca.a(this.L, this.f + this.T.f, this.g + this.T.g);
        } else {
            ca.a(this.K, this.f + this.T.f, this.g + this.T.g);
        }
    }
}
