package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
public final class Cc extends com.invertbit.games.gtw.c.Cb {
    final int p;
    final int q;
    java.util.Map<com.invertbit.games.gtw.a.Ci.Ia, com.badlogic.gdx.graphics.a.Cg> r;

    public Cc(com.invertbit.games.gtw.Ca ca) {
        super(ca);
        this.p = 300;
        this.q = 140;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void c() {
        super.c();
        this.r = new java.util.HashMap();
        java.util.List<com.badlogic.gdx.graphics.a.Ce> listD = com.invertbit.games.gtw.b.a.Ca.a().d();
        java.util.Map<java.lang.String, com.invertbit.games.gtw.b.Cf> mapB = com.invertbit.games.gtw.b.a.Ca.a().b();
        for (com.badlogic.gdx.graphics.a.Ce ce : listD) {
            com.invertbit.games.gtw.b.Cf cf = mapB.get(((com.badlogic.gdx.graphics.a.c.Cb) ce.e.a(java.lang.Object.class)[0]).a);
            if (com.invertbit.games.gtw.a.Ci.Ia.RUSSIA.name().equalsIgnoreCase(cf.e)) {
                com.badlogic.gdx.graphics.a.Cg cg = new com.badlogic.gdx.graphics.a.Cg(ce);
                cg.g = cf;
                cg.f.a(-800.0f, 0.0f, 750.0f);
                if (!this.r.containsKey(com.invertbit.games.gtw.a.Ci.Ia.RUSSIA)) {
                    this.r.put(com.invertbit.games.gtw.a.Ci.Ia.RUSSIA, cg);
                }
            } else if (com.invertbit.games.gtw.a.Ci.Ia.USA.name().equalsIgnoreCase(cf.e)) {
                com.badlogic.gdx.graphics.a.Cg cg2 = new com.badlogic.gdx.graphics.a.Cg(ce);
                cg2.g = cf;
                cg2.f.a(0.0f, 0.0f, 80.0f);
                this.r.put(com.invertbit.games.gtw.a.Ci.Ia.USA, cg2);
            } else if (com.invertbit.games.gtw.a.Ci.Ia.CHINA.name().equalsIgnoreCase(cf.e)) {
                com.badlogic.gdx.graphics.a.Cg cg3 = new com.badlogic.gdx.graphics.a.Cg(ce);
                cg3.g = cf;
                cg3.f.a(-200.0f, 0.0f, 50.0f);
                this.r.put(com.invertbit.games.gtw.a.Ci.Ia.CHINA, cg3);
            }
        }
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void a(java.lang.Object... objArr) {
        super.a(objArr);
        this.o.d();
        com.badlogic.gdx.f.a.a.Ce ce = new com.badlogic.gdx.f.a.a.Ce();
        ce.J = true;
        ce.c(new com.badlogic.gdx.f.a.a.Cc("Choose player:", new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.invertbit.games.gtw.b.e.Cg.s37.h))).m().a(3).i().e(10.0f);
        ce.i();
        ((com.invertbit.games.gtw.c.a.Cc) ce.c(new com.invertbit.games.gtw.c.a.Cc(300, 140, com.invertbit.games.gtw.a.Ci.Ia.USA.name(), com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).a(640.0f).b(170.0f).c().c(170.0f).w).a(new com.invertbit.games.gtw.c.Cd(this));
        ((com.invertbit.games.gtw.c.a.Cc) ce.c(new com.invertbit.games.gtw.c.a.Cc(300, 140, com.invertbit.games.gtw.a.Ci.Ia.RUSSIA.name(), com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).a(640.0f).n().b(170.0f).c().c(170.0f).w).a(new com.invertbit.games.gtw.c.Ce(this));
        ((com.invertbit.games.gtw.c.a.Cc) ce.c(new com.invertbit.games.gtw.c.a.Cc(300, 140, com.invertbit.games.gtw.a.Ci.Ia.CHINA.name(), com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).a(640.0f).n().b(170.0f).c().c(170.0f).w).a(new com.invertbit.games.gtw.c.Cf(this));
        this.o.a(ce);
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        super.b(f);
        this.j.a(this.f);
        for (com.invertbit.games.gtw.a.Ci.Ia ia : com.invertbit.games.gtw.a.Ci.Ia.values()) {
            this.j.a(this.r.get(ia), com.invertbit.games.gtw.c.b.Ca.a(ia, this.k));
        }
        this.j.c();
    }

    static void a(com.invertbit.games.gtw.c.Cc cc, com.invertbit.games.gtw.a.Ci.Ia ia) {
        com.invertbit.games.gtw.Ca ca = cc.e;
        com.invertbit.games.e.Ca ca2 = com.invertbit.games.e.Ca.game1;
        com.invertbit.games.gtw.a.Cg cg = new com.invertbit.games.gtw.a.Cg();
        cg.c();
        cg.a(ia, com.invertbit.games.gtw.b.Cf.Ib.valueOf(ia.name().toLowerCase()), true);
        ca.a(ca2, cg);
    }
}
