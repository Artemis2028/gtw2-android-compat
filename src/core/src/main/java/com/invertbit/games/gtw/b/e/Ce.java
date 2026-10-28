package com.invertbit.games.gtw.b.e;

/* loaded from: classes.dex */
public final class Ce {
    public static java.util.List<com.badlogic.gdx.math.Cl> a(java.util.List<com.invertbit.games.c.a.a.Cc> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<com.invertbit.games.c.a.a.Cc> it = list.iterator();
        while (it.hasNext()) {
            arrayList.addAll(a(it.next()));
        }
        return arrayList;
    }

    /** Unit-placement grid of a map region (the battle map's b/d/Cg in game, world.svg in tests). */
    public interface Grid {
        java.util.List<com.badlogic.gdx.math.Cl> cells(com.invertbit.games.gtw.b.Cf.Ib region);
    }

    public static void a(com.invertbit.games.gtw.a.Ci ci, final com.invertbit.games.gtw.b.d.Cg cg, boolean z) {
        a(ci, new Grid() {
            @Override
            public java.util.List<com.badlogic.gdx.math.Cl> cells(com.invertbit.games.gtw.b.Cf.Ib region) {
                return cg.a(region);
            }
        }, z);
    }

    public static void a(com.invertbit.games.gtw.a.Ci ci, Grid cg, boolean z) {
        java.util.List<com.invertbit.games.gtw.b.Cf> listE = ci.e();
        int i = ci.i.a;
        int i2 = ci.i.f.a;
        java.util.ArrayList arrayList = new java.util.ArrayList(i);
        int i3 = i2;
        int i4 = 0;
        while (i4 < i) {
            arrayList.add(5);
            i4++;
            i3 -= 5;
        }
        while (i3 > 0) {
            int iA = com.badlogic.gdx.math.Cd.a(i - 1);
            arrayList.set(iA, java.lang.Integer.valueOf(((java.lang.Integer) arrayList.get(iA)).intValue() + 5));
            i3 -= 5;
        }
        java.util.Iterator it = arrayList.iterator();
        java.util.ArrayList arrayList2 = new java.util.ArrayList(listE.size());
        for (com.invertbit.games.gtw.b.Cf cf : listE) {
            java.util.List<com.badlogic.gdx.math.Cl> listA = cg.cells(com.invertbit.games.gtw.b.Cf.Ib.valueOf(cf.e));
            java.util.Collections.shuffle(listA);
            int iReal = a(cf, ci, (java.util.Iterator<java.lang.Integer>) it, listA);
            java.util.Iterator<com.badlogic.gdx.math.Cl> it2 = listA.iterator();
            a(cf, ci, (java.util.Iterator<java.lang.Integer>) it, it2, iReal);
            arrayList2.add(it2);
        }
        com.invertbit.games.gtw.a.Cb cb = ci.i;
        a(cb.g, ci, arrayList2, z);
        a(cb.h, ci, arrayList2, z);
    }

    private static void a(com.invertbit.games.gtw.a.Cj cj, com.invertbit.games.gtw.a.Ci ci, java.util.List<java.util.Iterator<com.badlogic.gdx.math.Cl>> list, boolean z) {
        switch (com.invertbit.games.gtw.b.e.Cf.a[cj.d.ordinal()]) {
            case 1:
                a(5, cj.a / 5, ci, list);
                a(10, cj.b / 10, ci, list);
                a(15, cj.c / 15, ci, list);
                break;
            case 2:
                a(5, cj.a / 5, ci, list, z);
                a(10, cj.b / 10, ci, list, z);
                a(15, cj.c / 15, ci, list, z);
                break;
            case 3:
                b(5, cj.a / 5, ci, list);
                b(10, cj.b / 10, ci, list);
                b(15, cj.c / 15, ci, list);
                break;
        }
    }

    private static void a(int i, int i2, com.invertbit.games.gtw.a.Ci ci, java.util.List<java.util.Iterator<com.badlogic.gdx.math.Cl>> list) {
        for (int i3 = 0; i3 < i2; i3++) {
            java.util.Collections.shuffle(list);
            for (java.util.Iterator<com.badlogic.gdx.math.Cl> it : list) {
                if (it.hasNext()) {
                    ci.c(com.invertbit.games.gtw.b.b.Ca.a(it.next(), ci.e, i));
                }
            }
        }
    }

    private static void a(int i, int i2, com.invertbit.games.gtw.a.Ci ci, java.util.List<java.util.Iterator<com.badlogic.gdx.math.Cl>> list, boolean z) {
        for (int i3 = 0; i3 < i2; i3++) {
            for (java.util.Iterator<com.badlogic.gdx.math.Cl> it : list) {
                if (it.hasNext()) {
                    ci.c(com.invertbit.games.gtw.b.b.Ca.a(it.next(), ci.e, i / 2.0f, i, z));
                }
            }
        }
    }

    private static void b(int i, int i2, com.invertbit.games.gtw.a.Ci ci, java.util.List<java.util.Iterator<com.badlogic.gdx.math.Cl>> list) {
        for (int i3 = 0; i3 < i2; i3++) {
            for (java.util.Iterator<com.badlogic.gdx.math.Cl> it : list) {
                if (it.hasNext()) {
                    ci.c(com.invertbit.games.gtw.b.b.Ca.a(it.next(), ci.e, i / 2.0f, i));
                }
            }
        }
    }

    public static void a(int i, int i2, com.invertbit.games.gtw.a.Ci ci, java.util.Iterator<com.badlogic.gdx.math.Cl> it, boolean z) {
        for (int i3 = 0; i3 < i2; i3++) {
            if (it.hasNext()) {
                ci.c(com.invertbit.games.gtw.b.b.Ca.b(it.next(), ci.e, i / 2.0f, i, z));
            }
        }
    }

    private static java.util.List<com.badlogic.gdx.math.Cl> a(com.invertbit.games.c.a.a.Cc cc) {
        int iMax = java.lang.Math.max(0, (int) ((cc.b - 32.0f) / 48.0f));
        int iMax2 = java.lang.Math.max(0, (int) ((cc.c - 32.0f) / 48.0f));
        java.util.ArrayList arrayList = new java.util.ArrayList(iMax * iMax2);
        com.badlogic.gdx.math.Cl cl = cc.a;
        for (int i = 0; i <= iMax; i++) {
            for (int i2 = 0; i2 <= iMax2; i2++) {
                arrayList.add(new com.badlogic.gdx.math.Cl(cl.a + (i * 32.0f) + ((i * 32.0f) / 2.0f), 0.0f, cl.c + (i2 * 32.0f) + ((i2 * 32.0f) / 2.0f)));
            }
        }
        return arrayList;
    }

    /**
     * Places a new side's real cities (Nations.homeCities) at their map positions and removes the
     * grid slots they cover, so silos/bases don't stack on them. Returns how many were placed.
     */
    private static int a(com.invertbit.games.gtw.b.Cf cf, com.invertbit.games.gtw.a.Ci ci, java.util.Iterator<java.lang.Integer> it, java.util.List<com.badlogic.gdx.math.Cl> grid) {
        int count = com.invertbit.games.gtw.nations.Nations.stats(cf).c;
        int placed = 0;
        for (com.invertbit.games.gtw.nations.Nations.City city : com.invertbit.games.gtw.nations.Nations.homeCities(cf)) {
            if (placed >= count || !it.hasNext()) {
                break;
            }
            ci.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(city.x, 0.0f, city.z), ci.e, it.next().intValue()));
            placed++;
            java.util.Iterator<com.badlogic.gdx.math.Cl> g = grid.iterator();
            while (g.hasNext()) {
                com.badlogic.gdx.math.Cl p = g.next();
                float dx = p.a - city.x;
                float dz = p.c - city.z;
                if ((dx * dx) + (dz * dz) < 1600.0f) {
                    g.remove();
                }
            }
        }
        return placed;
    }

    private static void a(com.invertbit.games.gtw.b.Cf cf, com.invertbit.games.gtw.a.Ci ci, java.util.Iterator<java.lang.Integer> it, java.util.Iterator<com.badlogic.gdx.math.Cl> it2, int alreadyPlaced) {
        int i = com.invertbit.games.gtw.nations.Nations.stats(cf).c - alreadyPlaced;
        for (int i2 = 0; i2 < i; i2++) {
            if (it2.hasNext()) {
                ci.c(com.invertbit.games.gtw.b.b.Ca.a(it2.next(), ci.e, it.next().intValue()));
                it2.remove();
            }
        }
    }
}
