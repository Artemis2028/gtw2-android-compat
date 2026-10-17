package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
public final class Cp extends com.invertbit.games.gtw.c.Cb {
    com.invertbit.games.gtw.b.e.Cc p;
    com.invertbit.games.gtw.a.Cg q;
    int r;
    java.util.List<com.badlogic.gdx.graphics.a.Cg> s;
    com.badlogic.gdx.f.a.Ce t;
    com.badlogic.gdx.f.a.a.Cc u;
    com.badlogic.gdx.f.a.a.Cc v;

    static int a(com.invertbit.games.gtw.c.Cp cp) {
        return cp.r;
    }

    static com.invertbit.games.gtw.a.Cg b(com.invertbit.games.gtw.c.Cp cp) {
        return cp.q;
    }

    static int c(com.invertbit.games.gtw.c.Cp cp) {
        int i = cp.r;
        cp.r = i + 1;
        return i;
    }

    static com.badlogic.gdx.f.a.Ce d(com.invertbit.games.gtw.c.Cp cp) {
        return cp.t;
    }

    static com.badlogic.gdx.f.a.a.Cc e(com.invertbit.games.gtw.c.Cp cp) {
        return cp.u;
    }

    public Cp(com.invertbit.games.gtw.Ca ca) {
        super(ca);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void c() {
        super.c();
        this.p = com.invertbit.games.gtw.b.e.Cc.a();
        this.s = new java.util.ArrayList();
        java.util.List<com.badlogic.gdx.graphics.a.Ce> listD = com.invertbit.games.gtw.b.a.Ca.a().d();
        java.util.Map<java.lang.String, com.invertbit.games.gtw.b.Cf> mapB = com.invertbit.games.gtw.b.a.Ca.a().b();
        for (com.badlogic.gdx.graphics.a.Ce ce : listD) {
            com.invertbit.games.gtw.b.Cf cf = mapB.get(((com.badlogic.gdx.graphics.a.c.Cb) ce.e.a(java.lang.Object.class)[0]).a);
            com.badlogic.gdx.graphics.a.Cg cg = new com.badlogic.gdx.graphics.a.Cg(ce);
            cg.g = cf;
            this.s.add(cg);
        }
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void a(java.lang.Object... objArr) {
        super.a(objArr);
        if (objArr != null && (objArr[0] instanceof com.invertbit.games.gtw.a.Cg)) {
            this.q = (com.invertbit.games.gtw.a.Cg) objArr[0];
            this.o.d();
            this.r = this.q.a().f() - 1;
            com.badlogic.gdx.f.a.a.Ce ce = new com.badlogic.gdx.f.a.a.Ce();
            ce.J = true;
            this.u = new com.badlogic.gdx.f.a.a.Cc("Choose alliance", new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.invertbit.games.gtw.b.e.Cg.s37.h));
            if (!this.q.e()) {
                this.u.a("Alliances map");
            }
            ce.c(this.u).h().i();
            ce.i();
            if (!this.q.e()) {
                this.v = new com.badlogic.gdx.f.a.a.Cc("(No alliances avaiable at this defcon level)", new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s30), com.invertbit.games.gtw.b.e.Cg.s30.h));
                ce.c(this.v).h().i();
                ce.i();
            }
            com.invertbit.games.gtw.c.a.Cc cc = new com.invertbit.games.gtw.c.a.Cc(100, 100, "<", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h);
            cc.a(new com.invertbit.games.gtw.c.Cq(this));
            ce.c(cc).m().k().j().f(20.0f).d().b(150.0f);
            this.o.a(ce);
            this.t = new com.badlogic.gdx.f.a.Ce();
            this.o.a(this.t);
            java.util.Iterator<com.invertbit.games.gtw.b.Cf.Ib> it = this.p.a(this.q.a().e, this.q.c).iterator();
            while (it.hasNext()) {
                com.invertbit.games.gtw.b.Cf cf = com.invertbit.games.gtw.b.a.Ca.a().b().get(it.next().name());
                if ((this.r >= this.q.c || this.q.a().d.contains(cf) || this.q.b().d.contains(cf) || cf.e.equalsIgnoreCase(this.q.b().e.name())) ? false : true) {
                    com.invertbit.games.gtw.c.a.Ce ce2 = new com.invertbit.games.gtw.c.a.Ce(cf, com.invertbit.games.gtw.b.e.Cg.s37, com.invertbit.games.gtw.b.e.Cg.s37.h);
                    com.invertbit.games.c.a.a.Cc cc2 = cf.f;
                    float f = (cc2.a.a * c) / 2880.0f;
                    float f2 = d - ((cc2.a.c * d) / 1920.0f);
                    if (ce2.f != f || ce2.g != f2) {
                        ce2.f = f;
                        ce2.g = f2;
                    }
                    ce2.p = cf;
                    ce2.a(new com.invertbit.games.gtw.c.Cr(this, ce2));
                    this.t.b(ce2);
                }
            }
            return;
        }
        throw new java.lang.IllegalArgumentException("No new game bean");
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        super.b(f);
        this.j.a(this.f);
        this.j.a(this.n, this.k.a(com.invertbit.games.gtw.d.Ca.Ia.background));
        this.j.a();
        this.j.c();
        this.j.a(this.f);
        a(false);
        a(true);
        this.j.a();
        this.j.c();
        this.o.a(f);
        this.o.a();
    }

    private void a(boolean z) {
        for (com.badlogic.gdx.graphics.a.Cg cg : this.s) {
            com.invertbit.games.gtw.b.Cf cf = (com.invertbit.games.gtw.b.Cf) cg.g;
            java.util.Iterator<com.invertbit.games.gtw.a.Ci> it = this.q.b.iterator();
            while (true) {
                if (it.hasNext()) {
                    com.invertbit.games.gtw.a.Ci next = it.next();
                    if (z && next.d.contains(cf)) {
                        this.j.a(cg, com.invertbit.games.gtw.c.b.Ca.a(next.e, this.k));
                        break;
                    } else if (!z) {
                        this.j.a(cg, this.k.a(com.invertbit.games.gtw.d.Ca.Ia.defaultBorder));
                    }
                }
            }
        }
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void d() {
        this.e.a(com.invertbit.games.e.Ca.game2, this.q);
    }
}
