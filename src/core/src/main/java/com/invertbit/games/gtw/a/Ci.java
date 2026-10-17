package com.invertbit.games.gtw.a;

/* loaded from: classes.dex */
public class Ci {
    public com.invertbit.games.gtw.a.Ci.Ia e;
    public int g;
    public int h;
    public com.invertbit.games.gtw.a.Cb k;
    public java.util.List<com.invertbit.games.gtw.b.Ca> d = new java.util.ArrayList();
    java.util.Set<com.invertbit.games.gtw.b.Ca> c = new java.util.HashSet();
    int a = b();
    public int f = 0;
    boolean b = true;
    public com.invertbit.games.gtw.a.Cb i = new com.invertbit.games.gtw.a.Cb();
    public com.invertbit.games.gtw.a.Cb j = new com.invertbit.games.gtw.a.Cb();

    public enum Ia {
        USA,
        RUSSIA,
        CHINA,
        // Added sides (see com.invertbit.games.gtw.nations.Nations). Appended so the
        // ordinals of the original three stay unchanged; saves store name().
        EUROPE,
        AFRICA,
        SOUTH_AMERICA
    }

    public void a() {
        this.c = new java.util.HashSet();
        this.a = b();
        this.f = 0;
        this.b = true;
        this.j = new com.invertbit.games.gtw.a.Cb();
        if (this.i.j) {
            this.i = new com.invertbit.games.gtw.a.Cb();
            for (com.invertbit.games.gtw.b.Ca ca : this.d) {
                if (ca instanceof com.invertbit.games.gtw.b.Cf) {
                    this.i.a((com.invertbit.games.gtw.b.Cf) ca);
                    ((com.invertbit.games.gtw.b.Cf) ca).c = this.e;
                }
            }
        } else {
            this.i = this.k.e();
        }
        this.g = 0;
        this.h = 0;
        for (com.invertbit.games.gtw.b.Ca ca2 : this.d) {
            if ((ca2 instanceof com.invertbit.games.gtw.b.Cf) && !com.invertbit.games.gtw.nations.Nations.isHome(this.e, ((com.invertbit.games.gtw.b.Cf) ca2).e)) {
                this.a += 2;
            }
        }
        h();
    }

    private void h() {
        java.util.Iterator<com.invertbit.games.gtw.b.Ca> it = this.d.iterator();
        while (it.hasNext()) {
            if (!(it.next() instanceof com.invertbit.games.gtw.b.Cf)) {
                it.remove();
            }
        }
    }

    public final void c(com.invertbit.games.gtw.b.Ca ca) {
        this.d.add(ca);
        if (ca instanceof com.invertbit.games.gtw.b.Cd) {
            this.g += ((com.invertbit.games.gtw.b.Cd) ca).e;
            return;
        }
        if (ca instanceof com.invertbit.games.gtw.b.Ci) {
            this.h = (((com.invertbit.games.gtw.b.Ci) ca).f * ((com.invertbit.games.gtw.b.Cb) ((com.invertbit.games.gtw.b.Ci) ca)).e) + this.h;
            return;
        }
        if ((ca instanceof com.invertbit.games.gtw.b.Cf) && !com.invertbit.games.gtw.nations.Nations.isHome(this.e, ((com.invertbit.games.gtw.b.Cf) ca).e)) {
            this.a += 2;
            ((com.invertbit.games.gtw.b.Cf) ca).c = this.e;
            this.i.a((com.invertbit.games.gtw.b.Cf) ca);
            return;
        }
        if (ca instanceof com.invertbit.games.gtw.b.Cc) {
            this.h += ((com.invertbit.games.gtw.b.Cc) ca).f;
        } else if (ca instanceof com.invertbit.games.gtw.b.Cf) {
            this.i.a((com.invertbit.games.gtw.b.Cf) ca);
        }
    }

    public final void a(int i) {
        this.h -= i;
    }

    public final boolean c() {
        return this.f >= this.a;
    }

    public final java.util.List<com.invertbit.games.gtw.b.Cf> e() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (com.invertbit.games.gtw.b.Ca ca : this.d) {
            if (ca instanceof com.invertbit.games.gtw.b.Cf) {
                arrayList.add((com.invertbit.games.gtw.b.Cf) ca);
            }
        }
        return arrayList;
    }

    public final int f() {
        return e().size();
    }

    public final boolean g() {
        if (!this.b) {
            return true;
        }
        if (this.g != 0 && this.h != 0) {
            return false;
        }
        this.b = false;
        com.invertbit.games.gtw.b.Ce ceD = d();
        if (ceD != null) {
            ceD.d = true;
        }
        return true;
    }

    public final void a(com.invertbit.games.gtw.Cc cc, int i) {
        this.e = com.invertbit.games.gtw.a.Ci.Ia.valueOf(cc.c("player." + i));
        this.i.b(i + "." + this.e.name(), cc);
        for (java.lang.String str : cc.c("player." + i + ".country").split(",")) {
            com.invertbit.games.gtw.b.Cf cf = com.invertbit.games.gtw.b.a.Ca.a().b().get(str.toLowerCase());
            cf.c = this.e;
            c(cf);
        }
    }

    protected int b() {
        return 4;
    }

    public java.lang.String toString() {
        return java.lang.String.format("%-8s [%d/%d][Silo: %s MT Population: %s mln] Points: %d", this.e.name(), java.lang.Integer.valueOf(this.f), java.lang.Integer.valueOf(this.a), java.lang.Integer.valueOf(this.h), java.lang.Integer.valueOf(this.g), java.lang.Integer.valueOf(this.i.e));
    }

    public final com.invertbit.games.gtw.b.Ce d() {
        for (com.invertbit.games.gtw.b.Ca ca : this.d) {
            if (ca instanceof com.invertbit.games.gtw.b.Ce) {
                return (com.invertbit.games.gtw.b.Ce) ca;
            }
        }
        return null;
    }
}
