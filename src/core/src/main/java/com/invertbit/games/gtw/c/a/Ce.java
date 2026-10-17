package com.invertbit.games.gtw.c.a;

/* loaded from: classes.dex */
public final class Ce extends com.invertbit.games.gtw.c.a.Cc {
    com.invertbit.games.gtw.b.Cf V;
    com.badlogic.gdx.f.a.a.Cc W;

    public Ce(com.invertbit.games.gtw.b.Cf cf, com.invertbit.games.gtw.b.e.Cg cg, com.badlogic.gdx.graphics.Cb cb) {
        super(240, 120, cf.e, cg, cb);
        this.V = cf;
        this.V.h = com.invertbit.games.gtw.b.e.Cc.a(com.invertbit.games.gtw.b.Cf.Ib.valueOf(cf.e));
        this.T = new com.badlogic.gdx.f.a.a.Cc(this.S, new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(this.Q), this.R));
        this.T.i();
        c(this.T).a(this.O).m().h().g();
        i();
        this.W = new com.badlogic.gdx.f.a.a.Cc(this.V.h.c + "/" + this.V.h.a + "/" + this.V.h.b, new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s30), com.invertbit.games.gtw.b.e.Cg.s30.h));
        this.W.i();
        c(this.W).a(this.O).m().h().g().e();
        m();
    }

    @Override // com.invertbit.games.gtw.c.a.Cc
    protected final void l() {
    }

    @Override // com.invertbit.games.gtw.c.a.Cc
    protected final void c(com.badlogic.gdx.graphics.g2d.Ca ca, float f) {
        com.badlogic.gdx.graphics.Cb cb = this.o;
        ca.a(cb.H, cb.I, cb.J, cb.K * f);
        if (this.M) {
            ca.a(this.L, this.f + this.W.f, this.g + this.W.g);
        } else {
            ca.a(this.K, this.f + this.W.f, this.g + this.W.g);
        }
    }
}
