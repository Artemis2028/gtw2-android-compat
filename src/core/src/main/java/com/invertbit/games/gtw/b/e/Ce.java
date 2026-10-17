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

    public static void a(com.invertbit.games.gtw.a.Ci ci, com.invertbit.games.gtw.b.d.Cg cg, boolean z) {
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
            java.util.List<com.badlogic.gdx.math.Cl> listA = cg.a(com.invertbit.games.gtw.b.Cf.Ib.valueOf(cf.e));
            java.util.Collections.shuffle(listA);
            java.util.Iterator<com.badlogic.gdx.math.Cl> it2 = listA.iterator();
            a(cf, ci, (java.util.Iterator<java.lang.Integer>) it, it2);
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

    private static void a(com.invertbit.games.gtw.b.Cf cf, com.invertbit.games.gtw.a.Ci ci, java.util.Iterator<java.lang.Integer> it, java.util.Iterator<com.badlogic.gdx.math.Cl> it2) {
        int i = com.invertbit.games.gtw.b.e.Cc.a(com.invertbit.games.gtw.b.Cf.Ib.valueOf(cf.e)).c;
        for (int i2 = 0; i2 < i; i2++) {
            if (it2.hasNext()) {
                ci.c(com.invertbit.games.gtw.b.b.Ca.a(it2.next(), ci.e, it.next().intValue()));
                it2.remove();
            }
        }
    }
}
