package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
public final class Cg extends com.invertbit.games.gtw.c.Cb {
    final int p;
    final int q;
    com.invertbit.games.gtw.a.Cg r;
    com.invertbit.games.gtw.a.Ci.Ia s;
    java.util.Map<com.invertbit.games.gtw.a.Ci.Ia, com.badlogic.gdx.graphics.a.Cg> t;

    public Cg(com.invertbit.games.gtw.Ca ca) {
        super(ca);
        this.p = 300;
        this.q = 140;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void c() {
        super.c();
        this.t = new java.util.HashMap();
        java.util.List<com.badlogic.gdx.graphics.a.Ce> listD = com.invertbit.games.gtw.b.a.Ca.a().d();
        java.util.Map<java.lang.String, com.invertbit.games.gtw.b.Cf> mapB = com.invertbit.games.gtw.b.a.Ca.a().b();
        for (com.badlogic.gdx.graphics.a.Ce ce : listD) {
            com.invertbit.games.gtw.b.Cf cf = mapB.get(((com.badlogic.gdx.graphics.a.c.Cb) ce.e.a(java.lang.Object.class)[0]).a);
            if (com.invertbit.games.gtw.a.Ci.Ia.RUSSIA.name().equalsIgnoreCase(cf.e)) {
                com.badlogic.gdx.graphics.a.Cg cg = new com.badlogic.gdx.graphics.a.Cg(ce);
                cg.g = cf;
                cg.f.a(-800.0f, 0.0f, 750.0f);
                if (!this.t.containsKey(com.invertbit.games.gtw.a.Ci.Ia.RUSSIA)) {
                    this.t.put(com.invertbit.games.gtw.a.Ci.Ia.RUSSIA, cg);
                }
            } else if (com.invertbit.games.gtw.a.Ci.Ia.USA.name().equalsIgnoreCase(cf.e)) {
                com.badlogic.gdx.graphics.a.Cg cg2 = new com.badlogic.gdx.graphics.a.Cg(ce);
                cg2.g = cf;
                cg2.f.a(0.0f, 0.0f, 80.0f);
                this.t.put(com.invertbit.games.gtw.a.Ci.Ia.USA, cg2);
            } else if (com.invertbit.games.gtw.a.Ci.Ia.CHINA.name().equalsIgnoreCase(cf.e)) {
                com.badlogic.gdx.graphics.a.Cg cg3 = new com.badlogic.gdx.graphics.a.Cg(ce);
                cg3.g = cf;
                cg3.f.a(-200.0f, 0.0f, 50.0f);
                this.t.put(com.invertbit.games.gtw.a.Ci.Ia.CHINA, cg3);
            }
        }
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void a(java.lang.Object... objArr) {
        super.a(objArr);
        if (objArr != null && (objArr[0] instanceof com.invertbit.games.gtw.a.Cg)) {
            this.r = (com.invertbit.games.gtw.a.Cg) objArr[0];
            this.o.d();
            com.badlogic.gdx.f.a.a.Ce ce = new com.badlogic.gdx.f.a.a.Ce();
            ce.J = true;
            com.badlogic.gdx.f.a.a.Cc.Ia ia = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.invertbit.games.gtw.b.e.Cg.s37.h);
            ce.c(new com.badlogic.gdx.f.a.a.Cc("Choose your opponent:", ia)).m().a(3).i().e(10.0f);
            ce.i();
            this.s = this.r.b.get(0).e;
            if (!this.s.equals(com.invertbit.games.gtw.a.Ci.Ia.USA)) {
                ((com.invertbit.games.gtw.c.a.Cc) ce.c(new com.invertbit.games.gtw.c.a.Cc(300, 140, com.invertbit.games.gtw.a.Ci.Ia.USA.name(), com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).a(640.0f).b(170.0f).c().c(170.0f).w).a(new com.invertbit.games.gtw.c.Ch(this));
            } else {
                ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia)).n().b().b(170.0f).c().c(170.0f);
            }
            if (!this.s.equals(com.invertbit.games.gtw.a.Ci.Ia.RUSSIA)) {
                ((com.invertbit.games.gtw.c.a.Cc) ce.c(new com.invertbit.games.gtw.c.a.Cc(300, 140, com.invertbit.games.gtw.a.Ci.Ia.RUSSIA.name(), com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).a(640.0f).b(170.0f).c().c(170.0f).w).a(new com.invertbit.games.gtw.c.Ci(this));
            } else {
                ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia)).n().a(2).b(170.0f).c();
            }
            if (!this.s.equals(com.invertbit.games.gtw.a.Ci.Ia.CHINA)) {
                ((com.invertbit.games.gtw.c.a.Cc) ce.c(new com.invertbit.games.gtw.c.a.Cc(300, 140, com.invertbit.games.gtw.a.Ci.Ia.CHINA.name(), com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).a(640.0f).b(170.0f).c().c(170.0f).w).a(new com.invertbit.games.gtw.c.Cj(this));
            } else {
                ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia)).n().b().b(300.0f).c().c(170.0f);
            }
            this.o.a(ce);
            return;
        }
        throw new java.lang.IllegalArgumentException("No new game bean");
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        super.b(f);
        this.j.a(this.f);
        for (com.invertbit.games.gtw.a.Ci.Ia ia : com.invertbit.games.gtw.a.Ci.Ia.values()) {
            if (!this.s.equals(ia)) {
                this.j.a(this.t.get(ia), com.invertbit.games.gtw.c.b.Ca.a(ia, this.k));
            } else {
                this.j.a(this.t.get(ia), this.k.a(com.invertbit.games.gtw.d.Ca.Ia.defaultBorder));
            }
        }
        this.j.c();
    }

    static void a(com.invertbit.games.gtw.c.Cg cg, com.invertbit.games.gtw.a.Ci.Ia ia) {
        cg.r.a(ia, com.invertbit.games.gtw.a.Cg.a(ia), false);
        cg.e.a(com.invertbit.games.e.Ca.game2, cg.r);
    }
}
