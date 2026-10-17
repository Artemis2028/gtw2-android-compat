package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public final class Cl {
    public java.util.Map<java.lang.Class, com.invertbit.games.gtw.b.d.Cb> a;
    com.invertbit.games.gtw.a.Cf b;
    com.invertbit.games.gtw.b.d.Ck c;
    com.invertbit.games.gtw.b.d.Ca d;
    com.invertbit.games.gtw.b.d.Cn e;
    java.util.List<com.invertbit.games.gtw.b.c.Ca> f = new java.util.ArrayList();
    com.badlogic.gdx.graphics.a.Cg g;

    public Cl(java.util.List<com.invertbit.games.gtw.b.d.Cb> list) {
        this.a = new java.util.HashMap(list.size());
        b(list);
        this.e = new com.invertbit.games.gtw.b.d.Cn();
        a(this.e);
        c();
    }

    public final void a(com.invertbit.games.gtw.a.Ci ci) {
        this.b = (com.invertbit.games.gtw.a.Cf) ci;
        if (!this.a.containsKey(com.invertbit.games.gtw.b.Cf.class)) {
            return;
        }
        ((com.invertbit.games.gtw.b.d.Cg) this.a.get(com.invertbit.games.gtw.b.Cf.class)).b = (com.invertbit.games.gtw.a.Cf) ci;
    }

    public final void a(com.invertbit.games.gtw.b.d.Ck ck) {
        this.c = ck;
        a((com.invertbit.games.gtw.b.c.Ca) ck);
        ((com.invertbit.games.gtw.b.d.Cm) this.a.get(com.invertbit.games.gtw.b.Ch.class)).d = ck;
        ((com.invertbit.games.gtw.b.d.Cd) this.a.get(com.invertbit.games.gtw.b.Cc.class)).c = ck;
    }

    public final void a(com.invertbit.games.gtw.b.d.Ca ca) {
        this.d = ca;
        ca.b = (com.invertbit.games.gtw.b.d.Cd) this.a.get(com.invertbit.games.gtw.b.Cc.class);
        ca.a = (com.invertbit.games.gtw.b.d.Cm) this.a.get(com.invertbit.games.gtw.b.Ch.class);
    }

    private void b(java.util.List<com.invertbit.games.gtw.b.d.Cb> list) {
        for (com.invertbit.games.gtw.b.d.Cb cb : list) {
            this.a.put(cb.a(), cb);
            a(cb);
        }
    }

    private void a(com.invertbit.games.gtw.b.c.Ca ca) {
        this.f.add(ca);
    }

    private void c() {
        java.util.Iterator<com.invertbit.games.gtw.b.d.Cb> it = this.a.values().iterator();
        while (it.hasNext()) {
            it.next().d();
        }
    }

    public final void a(java.util.List<com.invertbit.games.gtw.b.Ca> list) {
        for (com.invertbit.games.gtw.b.Ca ca : list) {
            this.a.get(ca.getClass()).a(ca);
        }
        java.util.Iterator<com.invertbit.games.gtw.b.d.Cb> it = this.a.values().iterator();
        while (it.hasNext()) {
            it.next().b();
        }
    }

    public final void a(com.badlogic.gdx.graphics.a.Cf cf, com.invertbit.games.gtw.d.Ca ca) {
        for (com.invertbit.games.gtw.b.c.Ca ca2 : this.f) {
            ca2.a(cf, ca);
            if (ca2 instanceof com.invertbit.games.gtw.b.d.Cg) {
                cf.a();
            }
        }
    }

    public final void a(com.badlogic.gdx.graphics.g2d.Ch ch, com.badlogic.gdx.graphics.Ca ca) {
        java.util.Iterator<com.invertbit.games.gtw.b.c.Ca> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().a(ch, ca);
        }
    }

    public final void a(long j) {
        for (com.invertbit.games.gtw.b.c.Ca ca : this.f) {
            if (ca.a(j)) {
                ca.a(j, this);
            }
        }
    }

    public final void a(boolean z) {
        java.util.Iterator<com.invertbit.games.gtw.b.d.Cb> it = this.a.values().iterator();
        while (it.hasNext()) {
            it.next().a(z);
        }
    }

    public final void a(com.badlogic.gdx.math.Cl cl) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<com.invertbit.games.gtw.b.d.Cb> it = this.a.values().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().a(cl));
        }
        java.util.Collections.sort(arrayList);
        java.util.Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            com.invertbit.games.gtw.a.Ck ck = (com.invertbit.games.gtw.a.Ck) it2.next();
            if (!a(ck.a) || !(ck.a.g instanceof com.invertbit.games.gtw.b.Cd)) {
                com.badlogic.gdx.graphics.a.Cg cg = ck.a;
                if (this.g == null && a(cg)) {
                    this.g = cg;
                    this.e.a(this.g);
                    return;
                }
                if (this.g != cg) {
                    boolean zEquals = (this.g == null || this.g.g == null || cg == null || cg.g == null) ? false : ((com.invertbit.games.gtw.b.Ca) this.g.g).c.equals(((com.invertbit.games.gtw.b.Ca) cg.g).c);
                    if (zEquals) {
                        this.g = cg;
                    }
                    if (d()) {
                        com.invertbit.games.gtw.b.Cb cb = (com.invertbit.games.gtw.b.Cb) this.g.g;
                        this.e.a(this.g);
                        if (this.c.b.a()) {
                            this.c.a(java.lang.String.format(com.invertbit.games.gtw.b.e.Cj.X_SEC_TO_START.f, java.lang.Float.valueOf(this.c.b.d)));
                            return;
                        }
                        if (this.b.c()) {
                            this.c.a(java.lang.String.format(com.invertbit.games.gtw.b.e.Cj.TOO_MUCH_ICBM.f, java.lang.Integer.valueOf(this.b.f)));
                            return;
                        }
                        a(new com.invertbit.games.gtw.a.Cd(cb, (com.invertbit.games.gtw.b.Ca) cg.g));
                        if (!cb.a()) {
                            return;
                        }
                        this.e.a();
                        this.g = null;
                        return;
                    }
                    return;
                }
                return;
            }
        }
    }

    private boolean d() {
        return this.g != null && (this.g.g instanceof com.invertbit.games.gtw.b.Cb);
    }

    private boolean a(com.badlogic.gdx.graphics.a.Cg cg) {
        return ((com.invertbit.games.gtw.b.Ca) cg.g).c.equals(this.b.e);
    }

    public final boolean a() {
        java.util.Iterator<com.invertbit.games.gtw.a.Ci> it = this.c.b.a.iterator();
        int i = 0;
        while (it.hasNext()) {
            i = it.next().g() ? i + 1 : i;
        }
        return i > 0;
    }

    public final void b() {
        this.c.a.b();
        java.util.Iterator<com.invertbit.games.gtw.b.d.Cb> it = this.a.values().iterator();
        while (it.hasNext()) {
            it.next().e();
        }
    }

    public final boolean a(com.invertbit.games.gtw.a.Cd cd) {
        if (!cd.b.a(cd.a)) {
            return false;
        }
        this.a.get(cd.b.getClass()).a(cd);
        return true;
    }

    public final void b(com.invertbit.games.gtw.a.Cd cd) {
        com.invertbit.games.gtw.b.Cb cb = cd.b;
        com.invertbit.games.gtw.b.Ca ca = cd.a;
        if (((cb instanceof com.invertbit.games.gtw.b.Cc) || !cb.d) && !ca.d) {
            if (cd.b instanceof com.invertbit.games.gtw.b.Ci) {
                com.invertbit.games.gtw.b.Ci ci = (com.invertbit.games.gtw.b.Ci) cd.b;
                java.util.Iterator<com.invertbit.games.gtw.b.Ch> it = ci.h.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    com.invertbit.games.gtw.b.Ch next = it.next();
                    if (next.e.equals(cd)) {
                        ci.h.remove(next);
                        break;
                    }
                }
            }
            this.a.get(ca.getClass()).b(cd);
            this.a.get(com.invertbit.games.gtw.b.Cg.class).a(new com.invertbit.games.gtw.b.Cg(cb, ca));
            if ((ca instanceof com.invertbit.games.gtw.b.Cb) && !ca.d) {
                throw new java.lang.IllegalStateException("target instanceof BaseUnitModel && !target.isDestroyed() " + cb + " " + ca);
            }
            this.c.a(cd);
            this.d.a(cd);
        }
        if (!d() || !cd.a.d || !cd.a.equals(this.g.g)) {
            return;
        }
        this.e.a();
        this.g = null;
    }
}
