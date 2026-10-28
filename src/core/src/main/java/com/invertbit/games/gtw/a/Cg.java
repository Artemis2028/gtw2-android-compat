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
        return com.invertbit.games.gtw.nations.Nations.home(ia);
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
        for (com.invertbit.games.gtw.b.Cf.Ib extra : com.invertbit.games.gtw.nations.Nations.extraHomes(ia)) {
            com.invertbit.games.gtw.b.Cf more = new com.invertbit.games.gtw.b.Cf();
            more.c = ia;
            more.e = extra.name();
            ca.c(more);
        }
        if (z) {
            releaseMap();                            // new game: the player side is created first
        }
        claimOnMap(ia, ib);
        for (com.invertbit.games.gtw.b.Cf.Ib extra : com.invertbit.games.gtw.nations.Nations.extraHomes(ia)) {
            claimOnMap(ia, extra);
        }
        a(ca);
        return ca;
    }

    private static void releaseMap() {
        java.util.Map<java.lang.String, com.invertbit.games.gtw.b.Cf> map = com.invertbit.games.gtw.b.a.Ca.a().loadedRegions();
        if (map != null) {
            for (com.invertbit.games.gtw.b.Cf cf : map.values()) {
                cf.c = null;
            }
        }
    }

    /**
     * Marks the map's own region object as owned, as loading a saved game does (a/Ci.a). The AI
     * picks allies only among map regions with no owner (b/e/Ca); original homes are left out of
     * every alliance table, but extra homes (chile, alaska) are not, so without this the AI
     * opponent could take a region the other side starts with.
     */
    private static void claimOnMap(com.invertbit.games.gtw.a.Ci.Ia ia, com.invertbit.games.gtw.b.Cf.Ib region) {
        java.util.Map<java.lang.String, com.invertbit.games.gtw.b.Cf> map = com.invertbit.games.gtw.b.a.Ca.a().loadedRegions();
        com.invertbit.games.gtw.b.Cf onMap = map == null ? null : map.get(region.name());
        if (onMap != null) {
            onMap.c = ia;
        }
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
                        // AI target order. Original: EASY = random order, HARD = nearest first.
                        // NORMAL (new): nearest first, but each distance is jittered by +/-35%.
                        switch (com.invertbit.games.gtw.Cb.b().e()) {
                            case EASY:
                                java.util.Collections.shuffle(ca.a);
                                break;
                            case NORMAL:
                                a(ca.a, 0.35f);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    /** Stable sort by distance * random factor in [1-jitter, 1+jitter]. */
    static void a(java.util.List<com.invertbit.games.gtw.a.Cd> list, float jitter) {
        final java.util.IdentityHashMap<com.invertbit.games.gtw.a.Cd, java.lang.Float> key = new java.util.IdentityHashMap<>();
        java.util.Random random = new java.util.Random();
        for (com.invertbit.games.gtw.a.Cd cd : list) {
            key.put(cd, cd.c * (1.0f - jitter + (2.0f * jitter * random.nextFloat())));
        }
        java.util.Collections.sort(list, new java.util.Comparator<com.invertbit.games.gtw.a.Cd>() {
            @Override
            public int compare(com.invertbit.games.gtw.a.Cd x, com.invertbit.games.gtw.a.Cd y) {
                return java.lang.Float.compare(key.get(x), key.get(y));
            }
        });
    }
}
