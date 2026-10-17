package com.invertbit.games.gtw.c.a;

/* loaded from: classes.dex */
public final class Ca extends com.badlogic.gdx.f.a.a.Ce {
    public boolean K;
    com.invertbit.games.gtw.b.a.Ca.Ia L;
    com.badlogic.gdx.graphics.Co M;
    com.badlogic.gdx.graphics.Co N;
    com.badlogic.gdx.graphics.g2d.Cg O;
    com.invertbit.games.gtw.b.d.Cj P;
    com.invertbit.games.gtw.b.a.Ca Q;
    com.invertbit.games.gtw.b.a.Cb R;

    static boolean a(com.invertbit.games.gtw.c.a.Ca ca) {
        ca.K = true;
        return true;
    }

    public Ca(com.invertbit.games.gtw.b.a.Ca.Ia ia) {
        a(0.0f, 0.0f, 500.0f, 360.0f);
        a(500.0f, 360.0f);
        this.P = com.invertbit.games.gtw.b.d.Cj.a();
        this.R = com.invertbit.games.gtw.b.a.Cb.a();
        this.Q = com.invertbit.games.gtw.b.a.Ca.a();
        this.L = ia;
        this.M = com.invertbit.games.gtw.b.a.Cb.a(500, 360, new com.badlogic.gdx.graphics.Cb(0.0f, 0.52f, 1.0f, 0.3f));
        this.N = com.invertbit.games.gtw.b.a.Cb.a(500, 360, new com.badlogic.gdx.graphics.Cb(0.0f, 0.5f, 1.0f, 0.6f));
        this.O = this.Q.b(ia);
        com.badlogic.gdx.f.a.a.Cc cc = new com.badlogic.gdx.f.a.a.Cc(com.invertbit.games.gtw.b.e.Ch.b(this.L), new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.invertbit.games.gtw.b.e.Cg.s37.h));
        cc.i();
        c(cc).m().e().h().g();
        i();
        c(new com.badlogic.gdx.f.a.a.Cb(this.O)).h().f(30.0f);
        i();
        com.badlogic.gdx.f.a.a.Cc cc2 = new com.badlogic.gdx.f.a.a.Cc(com.invertbit.games.gtw.b.e.Ch.c(this.L), new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s30), com.invertbit.games.gtw.b.e.Cg.s30.h));
        cc2.r = false;
        cc2.k();
        cc2.i();
        c(cc2).m().e().h();
        a(new com.invertbit.games.gtw.c.a.Cb(this));
    }

    @Override // com.badlogic.gdx.f.a.a.Ce, com.badlogic.gdx.f.a.a.Cs, com.badlogic.gdx.f.a.Ce, com.badlogic.gdx.f.a.Cb
    public final void a(com.badlogic.gdx.graphics.g2d.Ca ca, float f) {
        super.a(ca, f);
        com.badlogic.gdx.graphics.Cb cb = this.o;
        ca.a(cb.H, cb.I, cb.J, cb.K * f);
        if (this.K) {
            ca.a(this.N, this.f, this.g);
        } else {
            ca.a(this.M, this.f, this.g);
        }
    }
}
