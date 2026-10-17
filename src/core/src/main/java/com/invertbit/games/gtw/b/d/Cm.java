package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public final class Cm extends com.invertbit.games.gtw.b.d.Cb {
    java.util.List<com.invertbit.games.gtw.b.Ch> b;
    java.util.List<com.invertbit.games.gtw.b.Ch> c;
    com.invertbit.games.gtw.b.d.Ck d;
    long g;
    long h;
    java.util.List<com.invertbit.games.gtw.b.Ch> i;
    java.util.Set<com.invertbit.games.gtw.b.Ch> j;
    final long e = 200;
    final long f = 3000;
    java.util.List<com.badlogic.gdx.graphics.a.Cg> k = new java.util.ArrayList();

    @Override // com.invertbit.games.gtw.b.d.Cb
    protected final void c() {
        super.c();
        this.b = new java.util.ArrayList();
        this.c = new java.util.ArrayList();
        this.i = new java.util.ArrayList();
        this.j = new java.util.HashSet();
        this.g = 0L;
        this.h = 0L;
    }

    @Override // com.invertbit.games.gtw.b.d.Cb, com.invertbit.games.gtw.b.c.Ca
    public final boolean a(long j) {
        this.g += j;
        this.h += j;
        return this.g > 200;
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final java.lang.Class a() {
        return com.invertbit.games.gtw.b.Ch.class;
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void a(com.invertbit.games.gtw.b.Ca ca) {
    }

    @Override // com.invertbit.games.gtw.b.d.Cb, com.invertbit.games.gtw.b.c.Ca
    public final void a(long j, com.invertbit.games.gtw.b.d.Cl cl) {
        java.util.List<com.badlogic.gdx.math.Ck> listSubList;
        super.a(j, cl);
        this.a = new java.util.ArrayList(this.b.size());
        this.j = new java.util.HashSet(this.b.size());
        for (com.invertbit.games.gtw.b.Ch ch : this.b) {
            com.invertbit.games.b.Ca ca = ch.f;
            if (!(ca.e >= ca.f)) {
                this.j.add(ch);
                java.util.List<com.badlogic.gdx.graphics.a.Cg> list = this.a;
                ca.e = (this.g / 1000.0f) + ca.e;
                if (ca.e > ca.f) {
                    ca.e = ca.f;
                }
                float f = ca.e / ca.f;
                com.badlogic.gdx.math.Ck ck = new com.badlogic.gdx.math.Ck();
                com.badlogic.gdx.math.Ck ck2 = ca.d.get(ca.d.size() - 1);
                com.badlogic.gdx.math.Ck ck3 = ca.a;
                com.badlogic.gdx.math.Ck ck4 = ca.c;
                com.badlogic.gdx.math.Ck ck5 = ca.c;
                com.badlogic.gdx.math.Ck ck6 = ca.b;
                com.badlogic.gdx.math.Ck ck7 = ca.g;
                float f2 = 1.0f - f;
                float f3 = f2 * f2;
                float f4 = f * f;
                com.badlogic.gdx.math.Ck ckB = (com.badlogic.gdx.math.Ck) ck.a(ck3).a(f3 * f2).b(ck7.a(ck4).a(3.0f * f3 * f)).b(ck7.a(ck5).a(3.0f * f2 * f4)).b(ck7.a(ck6).a(f * f4));
                if (ckB.d > 0.0f && ckB.d < 2880.0f) {
                    ca.d.add(ckB);
                } else if (ckB.d > 2880.0f && ck2.d < 2880.0f && ck2.d > 2160.0f) {
                    float f5 = ckB.d - 2880.0f;
                    float f6 = 1.0f - (f5 / (ckB.d - ck2.d));
                    ca.d.add(new com.badlogic.gdx.math.Ck(2880.0f, ck2.e + (java.lang.Math.abs(ckB.e - ck2.e) * f6)));
                    ca.d.add(new com.badlogic.gdx.math.Ck(0.0f, (java.lang.Math.abs(ckB.e - ck2.e) * f6) + ck2.e));
                    ca.d.add(new com.badlogic.gdx.math.Ck(f5, ckB.e));
                } else if (ckB.d > 2880.0f) {
                    ckB.d -= 2880.0f;
                    ca.d.add(ckB);
                } else if (ckB.d < 0.0f && ck2.d >= 0.0f && ck2.d < 720.0f) {
                    float f7 = 2880.0f + ckB.d;
                    float fAbs = 1.0f - java.lang.Math.abs(ckB.d / (ck2.d + java.lang.Math.abs(ckB.d)));
                    ca.d.add(new com.badlogic.gdx.math.Ck(0.0f, ck2.e - (java.lang.Math.abs((-ckB.e) + ck2.e) * fAbs)));
                    ca.d.add(new com.badlogic.gdx.math.Ck(2880.0f, ck2.e - (java.lang.Math.abs(ck2.e + (-ckB.e)) * fAbs)));
                    ca.d.add(new com.badlogic.gdx.math.Ck(f7, ckB.e));
                } else if (ckB.d < 0.0f) {
                    ckB.d += 2880.0f;
                    ca.d.add(com.invertbit.games.b.Ca.a(ckB));
                }
                ca.d.get(ca.d.size() - 1);
                ca.d.get(ca.d.size() - 2);
                java.util.ArrayList arrayList = new java.util.ArrayList();
                arrayList.addAll(ca.j);
                int i = ca.m;
                if (10 > ca.d.size()) {
                    listSubList = ca.d;
                } else {
                    listSubList = ca.d.subList(i * 10, ca.d.size());
                }
                ca.l = new com.invertbit.games.b.a.Cb(listSubList);
                com.invertbit.games.b.a.Cb cb = ca.l;
                cb.a = ca.h;
                cb.b = ca.i;
                com.badlogic.gdx.graphics.a.Ce ceA = cb.a(ca.m + " ");
                ca.k.add(ceA);
                com.badlogic.gdx.graphics.a.Cg cg = new com.badlogic.gdx.graphics.a.Cg(ceA);
                cg.f.a(2880.0f, 0.0f, 0.0f);
                arrayList.add(cg);
                if (listSubList.size() > 10) {
                    ca.j.add(cg);
                    ca.m++;
                }
                list.addAll(arrayList);
            } else {
                cl.b(ch.e);
                this.c.add(ch);
            }
        }
        this.g = 0L;
        this.i.addAll(this.c);
        this.b.removeAll(this.c);
        this.c.clear();
    }

    @Override // com.invertbit.games.gtw.b.d.Cb, com.invertbit.games.gtw.b.c.Ca
    public final void a(com.badlogic.gdx.graphics.a.Cf cf, com.invertbit.games.gtw.d.Ca ca) {
        if (this.a != null) {
            java.util.Iterator<com.badlogic.gdx.graphics.a.Cg> it = this.a.iterator();
            while (it.hasNext()) {
                cf.a(it.next(), ca.a(com.invertbit.games.gtw.d.Ca.Ia.defaultMapObject));
            }
        }
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void f() {
        super.f();
        if (this.h <= 3000) {
            return;
        }
        if (this.i != null && this.i.size() > 0) {
            for (com.invertbit.games.gtw.b.Ch ch : this.i) {
                if (!this.j.contains(ch) && ch.f != null) {
                    com.invertbit.games.b.Ca ca = ch.f;
                    if (ca.k != null) {
                        java.util.Iterator<com.badlogic.gdx.graphics.a.Ce> it = ca.k.iterator();
                        while (it.hasNext()) {
                            try {
                                it.next().b();
                            } catch (java.lang.Exception e) {
                            }
                        }
                    }
                    ch.f = null;
                }
            }
            this.i.clear();
        }
        this.h = 0L;
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void a(com.invertbit.games.gtw.a.Cd cd) {
        com.invertbit.games.gtw.b.Cb cb = cd.b;
        com.invertbit.games.gtw.b.Ca ca = cd.a;
        com.badlogic.gdx.math.Ck ck = new com.badlogic.gdx.math.Ck(cb.b.a, cb.b.c);
        com.badlogic.gdx.math.Ck ckA = com.invertbit.games.gtw.b.e.Ck.a(new com.badlogic.gdx.math.Ck(cb.b.a, cb.b.c), new com.badlogic.gdx.math.Ck(ca.b.a, ca.b.c));
        com.invertbit.games.gtw.b.Ch ch = new com.invertbit.games.gtw.b.Ch(cd, new com.invertbit.games.b.Ca(ck, ckA, (ck.b(ckA) * cb.g) / 100.0f, com.invertbit.games.gtw.b.a.Ca.a().c, com.invertbit.games.gtw.b.a.Ca.a().b));
        if (cb instanceof com.invertbit.games.gtw.b.Ci) {
            ((com.invertbit.games.gtw.b.Ci) cd.b).h.add(ch);
        }
        this.b.add(ch);
    }
}
