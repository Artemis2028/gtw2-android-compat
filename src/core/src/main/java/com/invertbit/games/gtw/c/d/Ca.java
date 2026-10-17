package com.invertbit.games.gtw.c.d;

/* loaded from: classes.dex */
public final class Ca extends com.invertbit.games.gtw.c.Cb implements com.invertbit.games.gtw.c.c.Ca {
    com.badlogic.gdx.f.a.a.Cc A;
    com.badlogic.gdx.f.a.a.Cc B;
    com.badlogic.gdx.f.a.a.Cc C;
    com.badlogic.gdx.f.a.a.Cc D;
    com.badlogic.gdx.graphics.a.Cf p;
    com.invertbit.games.gtw.d.Ca q;
    com.invertbit.games.gtw.b.d.Cl r;
    com.invertbit.games.gtw.b.d.Ca s;
    com.invertbit.games.gtw.b.d.Ck t;
    com.invertbit.games.gtw.c.d.Cd u;
    com.badlogic.gdx.Cj v;
    com.badlogic.gdx.d.Ca w;
    long x;
    com.invertbit.games.gtw.c.d.Cc y;
    com.badlogic.gdx.graphics.g2d.Ch z;

    static com.invertbit.games.gtw.c.d.Cc a(com.invertbit.games.gtw.c.d.Ca ca) {
        return ca.y;
    }

    static void a(com.invertbit.games.gtw.c.d.Ca ca, com.invertbit.games.gtw.c.d.Cd.Ia ia) {
        ca.a(ia);
    }

    public Ca(com.invertbit.games.gtw.Ca ca) {
        super(ca);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void c() {
        super.c();
        this.q = com.invertbit.games.gtw.d.Ca.a();
        if (com.invertbit.games.gtw.c.d.Cd.a == null) {
            try {
                com.invertbit.games.gtw.c.d.Cd.a = new com.invertbit.games.gtw.c.d.Cd();
            } catch (java.io.IOException e) {
                throw new java.io.UncheckedIOException(e);
            }
        }
        this.u = com.invertbit.games.gtw.c.d.Cd.a;
        this.p = new com.badlogic.gdx.graphics.a.Cf((byte) 0);
        this.v = new com.badlogic.gdx.Cj();
        this.w = new com.badlogic.gdx.d.Ca(new com.invertbit.games.gtw.c.c.Cb(this));
        this.v.a(this.w);
        this.v.a(new com.invertbit.games.e.a.Ca(this));
        this.v.a(this.o);
        this.t = new com.invertbit.games.gtw.b.d.Ck();
        ((com.badlogic.gdx.graphics.Cl) this.f).n = 0.2f;
        this.z = new com.badlogic.gdx.graphics.g2d.Ch();
        com.badlogic.gdx.math.Matrix4 matrix4 = new com.badlogic.gdx.math.Matrix4();
        new com.badlogic.gdx.math.Cl(1.0f, 0.0f, 0.0f);
        matrix4.a();
        this.z.b(matrix4);
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        this.f.a();
        long jCurrentTimeMillis = java.lang.System.currentTimeMillis() - this.x;
        this.x = java.lang.System.currentTimeMillis();
        if (this.r.a()) {
            this.o.d();
            com.badlogic.gdx.f.a.a.Ce ce = new com.badlogic.gdx.f.a.a.Ce();
            ce.J = true;
            this.C.a("All enemy cities or silos have been destroyed!\n           Click next to go to next tutorial");
            ce.c(this.C).n().h().i().d(30.0f);
            ce.i();
            com.invertbit.games.gtw.c.a.Cc cc = new com.invertbit.games.gtw.c.a.Cc(620, 100, ">", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h);
            cc.a(new com.invertbit.games.gtw.c.d.Cb(this));
            ce.c(cc).m().f(20.0f).g(20.0f).d().b(150.0f).k();
            this.o.a(ce);
        }
        this.r.a(jCurrentTimeMillis);
        if (!this.t.b.a()) {
            this.s.a(this.r);
        }
        a(true);
        e();
        a(false);
        e();
        this.o.c();
        this.o.a();
        if (((com.badlogic.gdx.graphics.Cl) this.f).n > 0.5f) {
            return;
        }
        this.z.a();
        this.r.a(this.z, this.f);
        this.z.c();
    }

    private void e() {
        this.p.a(this.f);
        this.r.a(this.p, this.q);
        this.p.c();
    }

    private void a(boolean z) {
        this.r.a(z);
    }

    @Override // com.invertbit.games.gtw.c.c.Ca
    public final void b(float f, float f2) {
    }

    @Override // com.invertbit.games.gtw.c.c.Ca
    public final boolean c(float f, float f2) {
        return true;
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void a(java.lang.Object... objArr) {
        super.a(objArr);
        a(com.invertbit.games.gtw.c.d.Cd.Ia.TUTORIAL1);
    }

    private void a(com.invertbit.games.gtw.c.d.Cd.Ia ia) {
        com.invertbit.games.gtw.c.d.Cc cc;
        if (com.invertbit.games.gtw.c.d.Cd.Ia.END.equals(ia)) {
            d();
            return;
        }
        com.invertbit.games.gtw.c.d.Cd cd = this.u;
        switch (ia) {
            case TUTORIAL1:
                cc = cd.a();
                break;
            case TUTORIAL2:
                cc = new com.invertbit.games.gtw.c.d.Cc();
                cc.f();
                cc.h = com.invertbit.games.gtw.c.d.Cd.Ia.TUTORIAL3;
                com.invertbit.games.gtw.a.Cf cf = new com.invertbit.games.gtw.a.Cf();
                cf.e = com.invertbit.games.gtw.a.Ci.Ia.USA;
                cf.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1520.0f, 0.0f, 1060.0f), cf.e, 5));
                cf.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1520.0f, 0.0f, 990.0f), cf.e, 5.0f, 15, true));
                com.invertbit.games.gtw.a.Ci ci = new com.invertbit.games.gtw.a.Ci();
                ci.e = com.invertbit.games.gtw.a.Ci.Ia.RUSSIA;
                ci.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1360.0f, 0.0f, 1060.0f), ci.e, 15));
                ci.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1360.0f, 0.0f, 990.0f), ci.e, 5.0f, 5, false));
                cc.a(cf);
                cc.a(ci);
                cc.e = cd.b.getProperty("TUTORIAL2.title");
                cc.f = cd.b.getProperty("TUTORIAL2.description1");
                cc.g = cd.b.getProperty("TUTORIAL2.description2");
                break;
            case TUTORIAL3:
                cc = new com.invertbit.games.gtw.c.d.Cc();
                cc.f();
                cc.h = com.invertbit.games.gtw.c.d.Cd.Ia.TUTORIAL4;
                com.invertbit.games.gtw.a.Cf cf2 = new com.invertbit.games.gtw.a.Cf();
                cf2.e = com.invertbit.games.gtw.a.Ci.Ia.USA;
                cf2.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1520.0f, 0.0f, 1060.0f), cf2.e, 5));
                cf2.c(com.invertbit.games.gtw.b.b.Ca.b(new com.badlogic.gdx.math.Cl(1520.0f, 0.0f, 990.0f), cf2.e, 5.0f, 5, true));
                com.invertbit.games.gtw.a.Ci ci2 = new com.invertbit.games.gtw.a.Ci();
                ci2.e = com.invertbit.games.gtw.a.Ci.Ia.RUSSIA;
                ci2.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1360.0f, 0.0f, 1060.0f), ci2.e, 5));
                ci2.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1360.0f, 0.0f, 990.0f), ci2.e, 5.0f, 5, false));
                cc.a(cf2);
                cc.a(ci2);
                cc.e = cd.b.getProperty("TUTORIAL3.title");
                cc.f = cd.b.getProperty("TUTORIAL3.description1");
                cc.g = cd.b.getProperty("TUTORIAL3.description2");
                break;
            case TUTORIAL4:
                cc = new com.invertbit.games.gtw.c.d.Cc();
                cc.f();
                cc.h = com.invertbit.games.gtw.c.d.Cd.Ia.TUTORIAL5;
                com.invertbit.games.gtw.a.Cf cf3 = new com.invertbit.games.gtw.a.Cf();
                cf3.e = com.invertbit.games.gtw.a.Ci.Ia.USA;
                cf3.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1520.0f, 0.0f, 1060.0f), cf3.e, 5));
                cf3.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1520.0f, 0.0f, 990.0f), cf3.e, 5.0f, 5));
                com.invertbit.games.gtw.a.Ci ci3 = new com.invertbit.games.gtw.a.Ci();
                ci3.e = com.invertbit.games.gtw.a.Ci.Ia.RUSSIA;
                ci3.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1360.0f, 0.0f, 1060.0f), ci3.e, 5));
                ci3.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1360.0f, 0.0f, 990.0f), ci3.e, 5.0f, 5, false));
                cc.a(cf3);
                cc.a(ci3);
                cc.e = cd.b.getProperty("TUTORIAL4.title");
                cc.f = cd.b.getProperty("TUTORIAL4.description1");
                cc.g = cd.b.getProperty("TUTORIAL4.description2");
                break;
            case TUTORIAL5:
                cc = new com.invertbit.games.gtw.c.d.Cc();
                cc.f();
                cc.h = com.invertbit.games.gtw.c.d.Cd.Ia.END;
                com.invertbit.games.gtw.a.Cf cf4 = new com.invertbit.games.gtw.a.Cf();
                cf4.e = com.invertbit.games.gtw.a.Ci.Ia.USA;
                cf4.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1520.0f, 0.0f, 1060.0f), cf4.e, 5));
                cf4.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1600.0f, 0.0f, 1060.0f), cf4.e, 5.0f, 5));
                cf4.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1520.0f, 0.0f, 990.0f), cf4.e, 5.0f, 5, true));
                cf4.c(com.invertbit.games.gtw.b.b.Ca.b(new com.badlogic.gdx.math.Cl(1600.0f, 0.0f, 990.0f), cf4.e, 5.0f, 5, true));
                com.invertbit.games.gtw.a.Ci ci4 = new com.invertbit.games.gtw.a.Ci();
                ci4.e = com.invertbit.games.gtw.a.Ci.Ia.RUSSIA;
                ci4.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1360.0f, 0.0f, 1060.0f), ci4.e, 5));
                ci4.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1360.0f, 0.0f, 990.0f), ci4.e, 5.0f, 5, false));
                cc.a(cf4);
                cc.a(ci4);
                cc.e = cd.b.getProperty("TUTORIAL5.title");
                cc.f = cd.b.getProperty("TUTORIAL5.description1");
                cc.g = cd.b.getProperty("TUTORIAL5.description2");
                break;
            default:
                cc = cd.a();
                break;
        }
        this.y = cc;
        com.invertbit.games.gtw.c.d.Cc cc2 = this.y;
        com.invertbit.games.gtw.a.Ce ce = new com.invertbit.games.gtw.a.Ce();
        java.util.Iterator<com.invertbit.games.gtw.a.Ci> it = cc2.b.iterator();
        while (it.hasNext()) {
            ce.a(it.next());
        }
        com.invertbit.games.gtw.b.d.Ck ck = this.t;
        ck.b = ce;
        ck.a.d();
        this.r = new com.invertbit.games.gtw.b.d.Cl(cc2.a);
        this.s = new com.invertbit.games.gtw.b.d.Ca();
        for (com.invertbit.games.gtw.a.Ci ci5 : cc2.b) {
            if (ci5 instanceof com.invertbit.games.gtw.a.Ca) {
                this.s.a((com.invertbit.games.gtw.a.Ca) ci5);
            } else if (ci5 instanceof com.invertbit.games.gtw.a.Cf) {
                this.r.a(ci5);
            }
            this.r.a(ci5.d);
        }
        this.r.a(this.t);
        this.r.a(this.s);
        a(false);
        this.o.d();
        com.badlogic.gdx.f.a.a.Ce ce2 = new com.badlogic.gdx.f.a.a.Ce();
        ce2.J = true;
        com.badlogic.gdx.f.a.a.Cc.Ia ia2 = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.invertbit.games.gtw.b.e.Cg.s37.h);
        com.badlogic.gdx.f.a.a.Cc.Ia ia3 = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s36), com.invertbit.games.gtw.b.e.Cg.s30.h);
        com.badlogic.gdx.f.a.a.Cc.Ia ia4 = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s36), com.invertbit.games.gtw.b.e.Cg.s37.h);
        this.A = new com.badlogic.gdx.f.a.a.Cc("", ia2);
        ce2.c(this.A).n().h().i().g().e(15.0f);
        ce2.i();
        this.B = new com.badlogic.gdx.f.a.a.Cc("", ia3);
        ce2.c(this.B).n().h().i().d(5.0f);
        ce2.i();
        this.C = new com.badlogic.gdx.f.a.a.Cc("", ia4);
        ce2.c(this.C).n().h().i().d(5.0f);
        ce2.i();
        this.D = new com.badlogic.gdx.f.a.a.Cc("", ia4);
        ce2.c(this.D).m().h().i();
        ce2.i();
        this.o.a(ce2);
        this.A.a(this.y.e);
        this.B.a(this.y.f);
        this.C.a(this.y.g);
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca, com.badlogic.gdx.Co
    public final void a() {
        super.a();
        com.badlogic.gdx.Cf.d.a(this.v);
        com.badlogic.gdx.Cf.d.a(true);
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.badlogic.gdx.Co
    public final void b() {
        if (this.p != null) {
            this.p.b();
        }
        if (this.q != null) {
            this.q.b();
        }
        if (this.r != null) {
            this.r.b();
        }
        com.invertbit.games.gtw.b.d.Cj.a().b();
    }

    @Override // com.invertbit.games.gtw.c.c.Ca
    public final void a(float f, float f2) {
        if (!this.i) {
            float f3 = ((com.badlogic.gdx.graphics.Cl) this.f).n;
            float f4 = ((f * f3) + this.f.a.a) - ((this.f.j * f3) / 2.0f);
            float f5 = ((f2 * f3) + this.f.a.c) - ((f3 * this.f.k) / 2.0f);
            if (f4 > 2880.0f) {
                f4 -= 2880.0f;
            }
            this.r.a(new com.badlogic.gdx.math.Cl(f4, 0.0f, f5));
        }
    }
}
