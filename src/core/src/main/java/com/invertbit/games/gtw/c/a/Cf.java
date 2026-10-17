package com.invertbit.games.gtw.c.a;

/* loaded from: classes.dex */
public final class Cf extends com.badlogic.gdx.f.a.Cb {
    com.badlogic.gdx.graphics.g2d.Cg A;
    com.badlogic.gdx.graphics.g2d.Cg B;
    com.badlogic.gdx.graphics.g2d.Cg C;
    com.badlogic.gdx.graphics.g2d.Cg D;
    com.badlogic.gdx.graphics.g2d.Cb q;
    com.badlogic.gdx.graphics.g2d.Cb r;
    com.badlogic.gdx.graphics.g2d.Cb s;
    com.invertbit.games.gtw.a.Ce t;
    com.badlogic.gdx.graphics.g2d.Cg w;
    com.badlogic.gdx.graphics.g2d.Cg x;
    com.badlogic.gdx.graphics.g2d.Cg y;
    com.badlogic.gdx.graphics.g2d.Cg z;
    int E = com.badlogic.gdx.Cf.b.a() - 200;
    int F = com.badlogic.gdx.Cf.b.b() - 10;
    com.invertbit.games.gtw.b.a.Ca u = com.invertbit.games.gtw.b.a.Ca.a();
    com.invertbit.games.gtw.b.a.Cb v = com.invertbit.games.gtw.b.a.Cb.a();

    public Cf(com.invertbit.games.gtw.a.Ce ce) {
        com.invertbit.games.gtw.b.d.Cj.a();
        this.s = com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s30);
        this.t = ce;
        com.badlogic.gdx.graphics.Cb cbA = a(ce.a.get(0).e);
        com.badlogic.gdx.graphics.Cb cbA2 = a(ce.a.get(1).e);
        this.w = this.u.b(com.invertbit.games.gtw.b.a.Ca.Ia.city);
        this.w.a();
        this.w.a(5.0f, 5.0f);
        this.x = this.u.b(com.invertbit.games.gtw.b.a.Ca.Ia.silo);
        this.x.a();
        this.x.a(11.0f, 59.0f);
        this.y = new com.badlogic.gdx.graphics.g2d.Cg(com.invertbit.games.gtw.b.a.Cb.a(50, 50, cbA));
        this.y.a(19.0f, 15.0f);
        this.z = new com.badlogic.gdx.graphics.g2d.Cg(com.invertbit.games.gtw.b.a.Cb.a(50, 50, cbA));
        this.z.a(19.0f, 70.0f);
        this.A = new com.badlogic.gdx.graphics.g2d.Cg(this.w);
        this.A.a();
        this.A.a(295.0f, 5.0f);
        this.B = new com.badlogic.gdx.graphics.g2d.Cg(this.x);
        this.B.a();
        this.B.a(301.0f, 59.0f);
        this.C = new com.badlogic.gdx.graphics.g2d.Cg(com.invertbit.games.gtw.b.a.Cb.a(50, 50, cbA2));
        this.C.a(309.0f, 15.0f);
        this.D = new com.badlogic.gdx.graphics.g2d.Cg(com.invertbit.games.gtw.b.a.Cb.a(50, 50, cbA2));
        this.D.a(309.0f, 70.0f);
        com.invertbit.games.gtw.b.d.Cj.a();
        this.q = com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s38);
        com.invertbit.games.gtw.b.d.Cj.a();
        this.r = com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s38);
    }

    @Override // com.badlogic.gdx.f.a.Cb
    public final void a(com.badlogic.gdx.graphics.g2d.Ca ca, float f) {
        float f2 = 140.0f;
        for (int size = this.t.c.size() - 1; size >= 0; size--) {
            this.s.a(ca, this.t.c.get(size), 20.0f, f2);
            f2 += 20.0f;
        }
        if (this.t.a()) {
            this.s.a(ca, java.lang.String.format("Defcon time: %4.2f", java.lang.Float.valueOf(this.t.d)), this.E, this.F);
        }
        this.w.a(ca);
        this.y.a(ca);
        this.q.a(ca, this.t.a.get(0).h + "MT", 80.0f, 100.0f);
        this.x.a(ca);
        this.z.a(ca);
        this.q.a(ca, this.t.a.get(0).g + "MLN", 80.0f, 45.0f);
        this.A.a(ca);
        this.C.a(ca);
        this.q.a(ca, this.t.a.get(1).h + "MT", 370.0f, 100.0f);
        this.B.a(ca);
        this.D.a(ca);
        this.q.a(ca, this.t.a.get(1).g + "MLN", 370.0f, 45.0f);
    }

    private static com.badlogic.gdx.graphics.Cb a(com.invertbit.games.gtw.a.Ci.Ia ia) {
        switch (com.invertbit.games.gtw.c.a.Cg.a[ia.ordinal()]) {
            case 1:
                return new com.badlogic.gdx.graphics.Cb(0.0f, 0.52f, 1.0f, 0.3f);
            case 2:
                return new com.badlogic.gdx.graphics.Cb(1.0f, 0.37f, 0.0f, 0.3f);
            case 3:
                return new com.badlogic.gdx.graphics.Cb(0.0f, 1.0f, 0.5f, 0.3f);
        }
        return new com.badlogic.gdx.graphics.Cb(0.0f, 0.52f, 1.0f, 0.3f);
    }
}
