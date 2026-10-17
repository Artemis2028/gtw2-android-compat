package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public abstract class Cb implements com.invertbit.games.gtw.b.c.Ca {
    protected java.util.List<com.badlogic.gdx.graphics.a.Cg> a;
    final float b = 120.0f;
    com.badlogic.gdx.math.Cl c = new com.badlogic.gdx.math.Cl();
    com.badlogic.gdx.math.Cl d = new com.badlogic.gdx.math.Cl();
    com.invertbit.games.gtw.b.d.Cp e = com.invertbit.games.gtw.b.d.Cp.a();

    public abstract java.lang.Class a();

    public abstract void a(com.invertbit.games.gtw.b.Ca ca);

    public Cb() {
        c();
    }

    public void b() {
    }

    protected void c() {
        this.a = new java.util.ArrayList();
    }

    public void a(boolean z) {
        if (this.a != null) {
            for (com.badlogic.gdx.graphics.a.Cg cg : this.a) {
                if (z) {
                    cg.f.a(-2880.0f, 0.0f, 0.0f);
                } else {
                    cg.f.a(2880.0f, 0.0f, 0.0f);
                }
            }
        }
    }

    @Override // com.invertbit.games.gtw.b.c.Ca
    public boolean a(long j) {
        return true;
    }

    @Override // com.invertbit.games.gtw.b.c.Ca
    public void a(long j, com.invertbit.games.gtw.b.d.Cl cl) {
    }

    public void a(com.invertbit.games.gtw.a.Cd cd) {
    }

    public final java.util.List<com.invertbit.games.gtw.a.Ck> a(com.badlogic.gdx.math.Cl cl) {
        if (this.a == null) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (com.badlogic.gdx.graphics.a.Cg cg : this.a) {
            if (cg.g instanceof com.invertbit.games.gtw.b.Ca) {
                float fA = ((com.invertbit.games.gtw.b.Ca) cg.g).a(cl);
                if (fA < 120.0f) {
                    arrayList.add(new com.invertbit.games.gtw.a.Ck(cg, fA));
                }
            }
        }
        return arrayList;
    }

    public void d() {
        if (this.a != null) {
            java.util.Iterator<com.badlogic.gdx.graphics.a.Cg> it = this.a.iterator();
            while (it.hasNext()) {
                it.next().g = null;
            }
        }
    }

    public boolean a(com.badlogic.gdx.graphics.Ca ca, com.badlogic.gdx.graphics.a.Cg cg) {
        int i;
        cg.f.a(this.c);
        if (this.c.a > 4320.0f) {
            this.c.a -= 2880.0f;
        }
        com.badlogic.gdx.math.Cb cb = ca.l;
        com.badlogic.gdx.math.Cl cl = this.c;
        for (int i2 = 0; i2 < cb.c.length; i2++) {
            com.badlogic.gdx.math.Cf cf = cb.c[i2];
            float f = cf.b + cf.a.f(cl);
            if (f == 0.0f) {
                i = com.badlogic.gdx.math.Cf.Ia.a;
            } else if (f < 0.0f) {
                i = com.badlogic.gdx.math.Cf.Ia.b;
            } else {
                i = com.badlogic.gdx.math.Cf.Ia.c;
            }
            if (i == com.badlogic.gdx.math.Cf.Ia.b) {
                return false;
            }
        }
        return true;
    }

    @Override // com.invertbit.games.gtw.b.c.Ca
    public void a(com.badlogic.gdx.graphics.a.Cf cf, com.invertbit.games.gtw.d.Ca ca) {
        if (this.a != null) {
            java.util.Iterator<com.badlogic.gdx.graphics.a.Cg> it = this.a.iterator();
            while (it.hasNext()) {
                a(it.next(), cf, ca);
            }
        }
    }

    public void a(com.badlogic.gdx.graphics.a.Cg cg, com.badlogic.gdx.graphics.a.Cf cf, com.invertbit.games.gtw.d.Ca ca) {
        com.badlogic.gdx.graphics.a.Cj cjA;
        com.badlogic.gdx.graphics.a.Cj cjA2;
        com.invertbit.games.gtw.b.Ca ca2 = (com.invertbit.games.gtw.b.Ca) cg.g;
        switch (com.invertbit.games.gtw.b.d.Cc.a[ca2.c.ordinal()]) {
            case 1:
                cjA = ca.a(com.invertbit.games.gtw.d.Ca.Ia.usaMapObject);
                break;
            case 2:
                cjA = ca.a(com.invertbit.games.gtw.d.Ca.Ia.russiaMapObject);
                break;
            case 3:
                cjA = ca.a(com.invertbit.games.gtw.d.Ca.Ia.chinaMapObject);
                break;
            default:
                cjA = ca.a(com.invertbit.games.gtw.d.Ca.Ia.defaultMapObject);
                break;
        }
        if (ca2 instanceof com.invertbit.games.gtw.b.Ci) {
            com.invertbit.games.gtw.b.Ci ci = (com.invertbit.games.gtw.b.Ci) ca2;
            if (ci.i) {
                if (((com.invertbit.games.gtw.b.Cb) ci).e == 0) {
                    switch (com.invertbit.games.gtw.b.d.Cc.a[ca2.c.ordinal()]) {
                        case 1:
                            cjA2 = ca.a(com.invertbit.games.gtw.d.Ca.Ia.usaInactiveMapObject);
                            break;
                        case 2:
                            cjA2 = ca.a(com.invertbit.games.gtw.d.Ca.Ia.russiaInactiveMapObject);
                            break;
                        case 3:
                            cjA2 = ca.a(com.invertbit.games.gtw.d.Ca.Ia.chinaInactiveMapObject);
                            break;
                        default:
                            cjA2 = ca.a(com.invertbit.games.gtw.d.Ca.Ia.defaultMapObject);
                            break;
                    }
                    cjA = cjA2;
                }
            }
        }
        cf.a(cg, cjA);
    }

    protected void a(com.badlogic.gdx.graphics.a.Cg cg, com.badlogic.gdx.graphics.g2d.Ch ch, com.badlogic.gdx.graphics.Ca ca) {
        com.badlogic.gdx.graphics.g2d.Cb cbA;
        if ((cg.g instanceof com.invertbit.games.gtw.b.Cd) || (cg.g instanceof com.invertbit.games.gtw.b.Ci)) {
            float f = ((com.badlogic.gdx.graphics.Cl) ca).n;
            com.invertbit.games.gtw.b.Ca ca2 = (com.invertbit.games.gtw.b.Ca) cg.g;
            if (f > 0.2f) {
                com.invertbit.games.gtw.b.d.Cj.a();
                cbA = com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s20);
            } else {
                com.invertbit.games.gtw.b.d.Cj.a();
                cbA = com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s30);
            }
            this.d.a = ca2.b.a;
            this.d.c = ca2.b.c;
            this.d.c += 26.0f;
            this.d.a -= 16.0f;
            if (ca.a.a > 2880.0f) {
                this.d.a += 2880.0f;
            }
            ca.a(this.d, 0.0f, 0.0f, com.badlogic.gdx.Cf.b.a(), com.badlogic.gdx.Cf.b.b());
            cbA.a(ch, ca2.toString(), this.d.a, this.d.b);
        }
    }

    @Override // com.invertbit.games.gtw.b.c.Ca
    public final void a(com.badlogic.gdx.graphics.g2d.Ch ch, com.badlogic.gdx.graphics.Ca ca) {
        for (com.badlogic.gdx.graphics.a.Cg cg : this.a) {
            if (a(ca, cg)) {
                a(cg, ch, ca);
            }
        }
    }

    public void e() {
    }

    public void f() {
    }

    public void b(com.invertbit.games.gtw.a.Cd cd) {
        com.badlogic.gdx.graphics.a.Cg next;
        com.invertbit.games.gtw.b.Cb cb = cd.b;
        com.invertbit.games.gtw.b.Ca ca = cd.a;
        ca.a(cb);
        if (ca.d) {
            if (this.a != null) {
                java.util.Iterator<com.badlogic.gdx.graphics.a.Cg> it = this.a.iterator();
                while (it.hasNext()) {
                    next = it.next();
                    if ((next.g instanceof com.invertbit.games.gtw.b.Ca) && ((com.invertbit.games.gtw.b.Ca) next.g) == ca) {
                        break;
                    }
                }
                next = null;
                this.a.remove(next);
            } else {
                next = null;
                this.a.remove(next);
            }
        }
        com.invertbit.games.gtw.b.d.Cp.Ia ia = com.invertbit.games.gtw.b.d.Cp.Ia.impact_ogg;
        com.invertbit.games.gtw.b.d.Cp cp = this.e;
        if (!cp.c || !cp.b.containsKey(ia)) {
            return;
        }
        cp.b.get(ia).a(1.0f);
    }
}
