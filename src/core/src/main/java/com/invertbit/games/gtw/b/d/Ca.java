package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public final class Ca {
    com.invertbit.games.gtw.b.d.Cm a;
    com.invertbit.games.gtw.b.d.Cd b;
    java.util.List<com.invertbit.games.gtw.a.Ca> d = new java.util.ArrayList();
    java.util.Map<com.invertbit.games.gtw.a.Ci.Ia, com.invertbit.games.gtw.a.Ca> e = new java.util.EnumMap(com.invertbit.games.gtw.a.Ci.Ia.class);
    java.util.List<com.invertbit.games.gtw.a.Ci.Ia> c = new java.util.ArrayList();

    public final void a(com.invertbit.games.gtw.a.Ca ca) {
        this.d.add(ca);
        this.e.put(ca.e, ca);
        this.c.add(ca.e);
    }

    public final void a(com.invertbit.games.gtw.b.d.Cl cl) {
        com.invertbit.games.gtw.a.Cd next;
        com.invertbit.games.gtw.b.d.Cm cm = this.a;
        java.util.Iterator<com.invertbit.games.gtw.a.Ci> it = cm.d.b.a.iterator();
        while (it.hasNext()) {
            it.next().f = 0;
        }
        java.util.Iterator<com.invertbit.games.gtw.b.Ch> it2 = cm.b.iterator();
        while (it2.hasNext()) {
            cm.d.a(it2.next().e.b.c);
        }
        com.invertbit.games.gtw.b.d.Cd cd = this.b;
        java.util.Iterator<com.badlogic.gdx.graphics.a.Cg> it3 = cd.b.iterator();
        while (it3.hasNext()) {
            com.invertbit.games.gtw.b.Cc cc = (com.invertbit.games.gtw.b.Cc) it3.next().g;
            if (!cc.d) {
                cd.c.a(cc.c);
            }
        }
        for (com.invertbit.games.gtw.a.Ca ca : this.d) {
            ca.c.size();
            if (!ca.c()) {
                for (com.invertbit.games.gtw.a.Cd cd2 : ca.a) {
                    if ((cd2.a instanceof com.invertbit.games.gtw.b.Ce) && !cd2.a.d && !ca.a(cd2.a) && !cd2.b.d && !ca.b(cd2.b)) {
                        break;
                    }
                }
                java.util.Iterator<com.invertbit.games.gtw.a.Cd> it4 = ca.a.iterator();
                while (true) {
                    if (it4.hasNext()) {
                        next = it4.next();
                        if (next.a != null && !(next.a instanceof com.invertbit.games.gtw.b.Ce) && !next.a.d && !ca.a(next.a) && !ca.b(next.b) && !next.b.d && next.b.a(next.a)) {
                            break;
                        }
                    } else {
                        next = null;
                        break;
                    }
                }
                if (next != null) {
                    if (cl.a(next)) {
                        ca.c.add(next);
                    }
                }
            }
            java.util.Iterator<com.invertbit.games.gtw.a.Cd> it5 = ca.c.iterator();
            while (it5.hasNext()) {
                it5.next();
            }
        }
    }

    public final void a(com.invertbit.games.gtw.a.Cd cd) {
        if (this.c.contains(cd.b.c)) {
            this.e.get(cd.b.c).a(cd);
        }
        if (!this.c.contains(cd.a.c)) {
            return;
        }
        this.e.get(cd.a.c);
        if (!(cd.a instanceof com.invertbit.games.gtw.b.Ci)) {
            return;
        }
        java.util.Iterator<com.invertbit.games.gtw.b.Ch> it = ((com.invertbit.games.gtw.b.Ci) cd.a).h.iterator();
        while (it.hasNext()) {
            this.e.get(cd.a.c).a(it.next().e);
        }
    }
}
