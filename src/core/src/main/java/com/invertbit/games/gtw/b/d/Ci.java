package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public final class Ci extends com.invertbit.games.gtw.b.d.Cb {
    com.badlogic.gdx.graphics.a.Ce b;

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void c() {
        super.c();
        com.badlogic.gdx.graphics.a.e.Ci ci = com.invertbit.games.gtw.b.a.Ca.a().b;
        com.badlogic.gdx.graphics.a.Cd cd = com.invertbit.games.gtw.b.a.Ca.a().c;
        ci.a();
        ci.a("cylinder", 4, 25L, cd).f();
        this.b = ci.b();
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final java.lang.Class a() {
        return com.invertbit.games.gtw.b.Cg.class;
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void a(com.invertbit.games.gtw.b.Ca ca) {
        com.badlogic.gdx.graphics.a.Cg cg = new com.badlogic.gdx.graphics.a.Cg(this.b);
        cg.g = ca;
        cg.f.b(ca.b);
        cg.f.a(2880.0f, 0.0f, 0.0f);
        this.a.add(cg);
    }

    @Override // com.invertbit.games.gtw.b.d.Cb, com.invertbit.games.gtw.b.c.Ca
    public final void a(long j, com.invertbit.games.gtw.b.d.Cl cl) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (com.badlogic.gdx.graphics.a.Cg cg : this.a) {
            com.invertbit.games.gtw.b.Cg cg2 = (com.invertbit.games.gtw.b.Cg) cg.g;
            cg2.g = (j / 1000.0f) + cg2.g;
            if (cg2.g > cg2.h) {
                cg2.g = cg2.h;
            }
            cg2.f = cg2.e * (cg2.g / cg2.h);
            if (cg2.g >= cg2.h) {
                arrayList.add(cg);
            } else {
                cg.c.a(0).f.a(cg2.f, 1.0f, cg2.f);
                cg.a();
            }
        }
        this.a.removeAll(arrayList);
    }

    @Override // com.invertbit.games.gtw.b.d.Cb, com.invertbit.games.gtw.b.c.Ca
    public final void a(com.badlogic.gdx.graphics.a.Cf cf, com.invertbit.games.gtw.d.Ca ca) {
        if (this.a != null) {
            java.util.Iterator<com.badlogic.gdx.graphics.a.Cg> it = this.a.iterator();
            while (it.hasNext()) {
                cf.a(it.next(), ca.a(com.invertbit.games.gtw.d.Ca.Ia.explosionObject));
            }
        }
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void e() {
        super.e();
        try {
            this.b.b();
        } catch (java.lang.Exception e) {
        }
    }
}
