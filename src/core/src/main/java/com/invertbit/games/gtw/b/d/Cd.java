package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public final class Cd extends com.invertbit.games.gtw.b.d.Cb {
    java.util.List<com.badlogic.gdx.graphics.a.Cg> b;
    com.invertbit.games.gtw.b.d.Ck c;
    java.util.Map<com.invertbit.games.gtw.b.a.Ca.Ia, com.badlogic.gdx.graphics.a.Ce> d;
    com.badlogic.gdx.math.Cl e = new com.badlogic.gdx.math.Cl();

    @Override // com.invertbit.games.gtw.b.d.Cb
    protected final void c() {
        super.c();
        this.d = new java.util.EnumMap(com.invertbit.games.gtw.b.a.Ca.Ia.class);
        this.d.put(com.invertbit.games.gtw.b.a.Ca.Ia.chinabomber, com.invertbit.games.gtw.b.a.Ca.a().a(com.invertbit.games.gtw.b.a.Ca.Ia.chinabomber));
        this.d.put(com.invertbit.games.gtw.b.a.Ca.Ia.russiabomber, com.invertbit.games.gtw.b.a.Ca.a().a(com.invertbit.games.gtw.b.a.Ca.Ia.russiabomber));
        this.d.put(com.invertbit.games.gtw.b.a.Ca.Ia.usabomber, com.invertbit.games.gtw.b.a.Ca.a().a(com.invertbit.games.gtw.b.a.Ca.Ia.usabomber));
        this.b = new java.util.ArrayList();
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final java.lang.Class a() {
        return com.invertbit.games.gtw.b.Cc.class;
    }

    @Override // com.invertbit.games.gtw.b.d.Cb, com.invertbit.games.gtw.b.c.Ca
    public final void a(long j, com.invertbit.games.gtw.b.d.Cl cl) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (com.badlogic.gdx.graphics.a.Cg cg : this.b) {
            com.invertbit.games.gtw.b.Cc cc = (com.invertbit.games.gtw.b.Cc) cg.g;
            this.c.a(cc.c);
            if (cc.m >= cc.n) {
                arrayList.add(cg);
                cl.b(new com.invertbit.games.gtw.a.Cd(cc, cc.k));
            } else {
                com.invertbit.games.gtw.b.Cc cc2 = (com.invertbit.games.gtw.b.Cc) cg.g;
                cc2.m = (j / 1000.0f) + cc2.m;
                if (cc2.m > cc2.n) {
                    cc2.m = cc2.n;
                }
                cg.f.a(cc2.l);
                com.badlogic.gdx.math.Cl cl2 = new com.badlogic.gdx.math.Cl();
                float f = cc2.m / cc2.n;
                com.badlogic.gdx.math.Cl clB = (com.badlogic.gdx.math.Cl) cl2.a(cc2.h).a(1.0f - f).b(this.e.a(cc2.i).a(f));
                if (clB.a > 2880.0f) {
                    clB.a -= 2880.0f;
                } else if (clB.a < 0.0f) {
                    clB.a += 2880.0f;
                }
                float f2 = clB.a - cc2.j.a;
                float f3 = clB.c - cc2.j.c;
                cc2.j.a += f2;
                cc2.j.c += f3;
                if (cc2.j.a > 2880.0f) {
                    cc2.j.a -= 2880.0f;
                } else if (cc2.j.a < 0.0f) {
                    cc2.j.a += 2880.0f;
                }
                cg.f.a(f2, 0.0f, f3);
                cg.f.a(cc2.l * (-1.0f));
            }
        }
        this.a.removeAll(arrayList);
        this.b.removeAll(arrayList);
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void a(boolean z) {
        if (this.a != null) {
            for (com.badlogic.gdx.graphics.a.Cg cg : this.a) {
                com.invertbit.games.gtw.b.Cc cc = (com.invertbit.games.gtw.b.Cc) cg.g;
                if (cc.l != 0.0f) {
                    cg.f.a(cc.l);
                }
                if (z) {
                    cg.f.a(-2880.0f, 0.0f, 0.0f);
                } else {
                    cg.f.a(2880.0f, 0.0f, 0.0f);
                }
                if (cc.l != 0.0f) {
                    cg.f.a(cc.l * (-1.0f));
                }
            }
        }
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void a(com.invertbit.games.gtw.b.Ca ca) {
        com.badlogic.gdx.graphics.a.Cg cg = new com.badlogic.gdx.graphics.a.Cg(this.d.get(com.invertbit.games.gtw.b.a.Ca.Ia.valueOf(ca.c.name().toLowerCase() + "bomber")));
        cg.f.a(-15.0f, 0.0f, -19.0f);
        cg.g = ca;
        cg.f.b(ca.b);
        this.a.add(cg);
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void a(com.invertbit.games.gtw.a.Cd cd) {
        com.badlogic.gdx.graphics.a.Cg cg;
        float degrees;
        com.invertbit.games.gtw.b.Cb cb = cd.b;
        com.invertbit.games.gtw.b.Ca ca = cd.a;
        java.util.Iterator<com.badlogic.gdx.graphics.a.Cg> it = this.a.iterator();
        while (true) {
            if (it.hasNext()) {
                com.badlogic.gdx.graphics.a.Cg next = it.next();
                if (cb.equals(next.g)) {
                    cg = next;
                    break;
                }
            } else {
                cg = null;
                break;
            }
        }
        if (cg != null) {
            com.invertbit.games.gtw.b.Cc cc = (com.invertbit.games.gtw.b.Cc) cb;
            cc.k = ca;
            com.badlogic.gdx.math.Cl cl = cc.h;
            com.badlogic.gdx.math.Cl cl2 = ca.b;
            com.badlogic.gdx.math.Ck ckA = com.invertbit.games.gtw.b.e.Ck.a(new com.badlogic.gdx.math.Ck(cl.a, cl.c), new com.badlogic.gdx.math.Ck(cl2.a, cl2.c));
            com.badlogic.gdx.math.Cl cl3 = new com.badlogic.gdx.math.Cl(ckA.d, 0.0f, ckA.e);
            cc.i = cl3;
            cc.n = cc.b.d(cl3) / cc.o;
            cc.i.a += ca.b().a;
            cc.i.c = ca.b().c + cc.i.c;
            float fAbs = java.lang.Math.abs(cc.h.a - cc.i.a);
            float fAbs2 = java.lang.Math.abs(cc.h.c - cc.i.c);
            float fSqrt = (float) java.lang.Math.sqrt((fAbs2 * fAbs2) + (fAbs * fAbs));
            if (cc.h.a > cc.i.a) {
                if (cc.h.c < cc.i.c) {
                    degrees = ((float) java.lang.Math.toDegrees(java.lang.Math.acos(fAbs / fSqrt))) + 90.0f;
                } else {
                    degrees = 90.0f - ((float) java.lang.Math.toDegrees(java.lang.Math.acos(fAbs / fSqrt)));
                }
            } else if (cc.h.c < cc.i.c) {
                degrees = (-90.0f) - ((float) java.lang.Math.toDegrees(java.lang.Math.acos(fAbs / fSqrt)));
            } else {
                degrees = ((float) java.lang.Math.toDegrees(java.lang.Math.acos(fAbs / fSqrt))) - 90.0f;
            }
            cc.l = degrees;
            cg.f.a(cc.l * (-1.0f));
            this.b.add(cg);
        }
    }
}
