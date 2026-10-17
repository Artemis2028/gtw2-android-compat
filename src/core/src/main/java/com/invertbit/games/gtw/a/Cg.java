package com.invertbit.games.gtw.a;

/* loaded from: classes.dex */
public class Cg {
    public java.util.List<com.invertbit.games.gtw.b.d.Cb> a;
    public java.util.List<com.invertbit.games.gtw.a.Ci> b = new java.util.ArrayList();
    public int c;
    public java.util.List<java.lang.String> d;

    public final void a(com.invertbit.games.gtw.a.Ci ci) {
        this.b.add(ci);
    }

    public final com.invertbit.games.gtw.a.Ci a() {
        return this.b.get(0);
    }

    public final com.invertbit.games.gtw.a.Ci b() {
        return this.b.get(1);
    }

    public final void c() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.invertbit.games.gtw.b.d.Cm cm = new com.invertbit.games.gtw.b.d.Cm();
        arrayList.add(new com.invertbit.games.gtw.b.d.Cg());
        arrayList.add(cm);
        arrayList.add(new com.invertbit.games.gtw.b.d.Ce());
        arrayList.add(new com.invertbit.games.gtw.b.d.Cd());
        arrayList.add(new com.invertbit.games.gtw.b.d.Co(cm));
        arrayList.add(new com.invertbit.games.gtw.b.d.Cq(cm));
        arrayList.add(new com.invertbit.games.gtw.b.d.Cf(cm));
        arrayList.add(new com.invertbit.games.gtw.b.d.Ci());
        this.a = arrayList;
    }

    public static com.invertbit.games.gtw.b.Cf.Ib a(com.invertbit.games.gtw.a.Ci.Ia ia) {
        try {
            return com.invertbit.games.gtw.b.Cf.Ib.valueOf(ia.name().toLowerCase());
        } catch (java.lang.Exception e) {
            switch (com.invertbit.games.gtw.a.Ch.a[ia.ordinal()]) {
                case 1:
                    return com.invertbit.games.gtw.b.Cf.Ib.usa;
                case 2:
                    return com.invertbit.games.gtw.b.Cf.Ib.russia;
                case 3:
                    return com.invertbit.games.gtw.b.Cf.Ib.china;
                default:
                    return null;
            }
        }
    }

    public final com.invertbit.games.gtw.a.Ci a(com.invertbit.games.gtw.a.Ci.Ia ia, com.invertbit.games.gtw.b.Cf.Ib ib, boolean z) {
        com.invertbit.games.gtw.a.Ci ca;
        if (z) {
            ca = new com.invertbit.games.gtw.a.Cf();
        } else {
            ca = new com.invertbit.games.gtw.a.Ca();
        }
        ca.e = ia;
        java.lang.String strName = ib.name();
        com.invertbit.games.gtw.b.Cf cf = new com.invertbit.games.gtw.b.Cf();
        cf.c = ia;
        cf.e = strName;
        ca.c(cf);
        a(ca);
        return ca;
    }

    public final boolean e() {
        return a().f() + (-1) < this.c;
    }

    public final void d() {
        for (com.invertbit.games.gtw.a.Ci ci : this.b) {
            if (ci instanceof com.invertbit.games.gtw.a.Ca) {
                for (com.invertbit.games.gtw.a.Ci ci2 : this.b) {
                    if (ci != ci2) {
                        com.invertbit.games.gtw.a.Ca ca = (com.invertbit.games.gtw.a.Ca) ci;
                        com.invertbit.games.gtw.b.Ce ceD = ci2.d();
                        if (ceD != null) {
                            for (com.invertbit.games.gtw.b.Ca ca2 : ca.d) {
                                if (ca2 instanceof com.invertbit.games.gtw.b.Cb) {
                                    ca.b.add(new com.invertbit.games.gtw.a.Cd((com.invertbit.games.gtw.b.Cb) ca2, ceD));
                                }
                            }
                            java.util.Collections.sort(ca.b);
                        }
                        for (com.invertbit.games.gtw.b.Ca ca3 : ca.d) {
                            if (ca3 instanceof com.invertbit.games.gtw.b.Cb) {
                                for (com.invertbit.games.gtw.b.Ca ca4 : ci2.d) {
                                    if (!(ca4 instanceof com.invertbit.games.gtw.b.Cf)) {
                                        ca.a.add(new com.invertbit.games.gtw.a.Cd((com.invertbit.games.gtw.b.Cb) ca3, ca4));
                                    }
                                }
                            }
                        }
                        java.util.Collections.sort(ca.a);
                        if (com.invertbit.games.gtw.Cb.b().a()) {
                            java.util.Collections.shuffle(ca.a);
                        }
                    }
                }
            }
        }
    }
}
