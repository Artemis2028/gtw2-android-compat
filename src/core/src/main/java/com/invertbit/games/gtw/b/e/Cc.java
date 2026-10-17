package com.invertbit.games.gtw.b.e;

/* loaded from: classes.dex */
public final class Cc {
    static com.invertbit.games.gtw.b.e.Cc a;
    java.util.Map<com.invertbit.games.gtw.a.Ci.Ia, java.util.Map<java.lang.Integer, java.util.List<com.invertbit.games.gtw.b.Cf.Ib>>> b;

    private Cc() {
        b();
    }

    public static com.invertbit.games.gtw.b.e.Cc a() {
        if (a == null) {
            a = new com.invertbit.games.gtw.b.e.Cc();
        }
        return a;
    }

    private static boolean b(com.invertbit.games.gtw.b.Cf.Ib ib) {
        return com.invertbit.games.gtw.b.Cf.Ib.china.equals(ib) || com.invertbit.games.gtw.b.Cf.Ib.russia.equals(ib) || com.invertbit.games.gtw.b.Cf.Ib.usa.equals(ib);
    }

    private void b() {
        this.b = new java.util.HashMap(3);
        com.invertbit.games.gtw.a.Ci.Ia ia = com.invertbit.games.gtw.a.Ci.Ia.USA;
        java.util.HashMap map = new java.util.HashMap();
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (com.invertbit.games.gtw.b.Cf.Ib ib : com.invertbit.games.gtw.b.Cf.Ib.values()) {
            if (!b(ib)) {
                arrayList.add(ib);
            }
        }
        map.put(0, arrayList);
        java.util.ArrayList arrayList2 = new java.util.ArrayList();
        arrayList2.add(com.invertbit.games.gtw.b.Cf.Ib.china);
        arrayList2.add(com.invertbit.games.gtw.b.Cf.Ib.russia);
        map.put(9, arrayList2);
        this.b.put(ia, map);
        com.invertbit.games.gtw.a.Ci.Ia ia2 = com.invertbit.games.gtw.a.Ci.Ia.RUSSIA;
        java.util.HashMap map2 = new java.util.HashMap();
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        for (com.invertbit.games.gtw.b.Cf.Ib ib2 : com.invertbit.games.gtw.b.Cf.Ib.values()) {
            if (!b(ib2)) {
                arrayList3.add(ib2);
            }
        }
        map2.put(0, arrayList3);
        java.util.ArrayList arrayList4 = new java.util.ArrayList();
        arrayList4.add(com.invertbit.games.gtw.b.Cf.Ib.china);
        arrayList4.add(com.invertbit.games.gtw.b.Cf.Ib.usa);
        map2.put(9, arrayList4);
        this.b.put(ia2, map2);
        com.invertbit.games.gtw.a.Ci.Ia ia3 = com.invertbit.games.gtw.a.Ci.Ia.CHINA;
        java.util.HashMap map3 = new java.util.HashMap();
        java.util.ArrayList arrayList5 = new java.util.ArrayList();
        for (com.invertbit.games.gtw.b.Cf.Ib ib3 : com.invertbit.games.gtw.b.Cf.Ib.values()) {
            if (!b(ib3)) {
                arrayList5.add(ib3);
            }
        }
        map3.put(0, arrayList5);
        java.util.ArrayList arrayList6 = new java.util.ArrayList();
        arrayList6.add(com.invertbit.games.gtw.b.Cf.Ib.usa);
        arrayList6.add(com.invertbit.games.gtw.b.Cf.Ib.russia);
        map3.put(9, arrayList6);
        this.b.put(ia3, map3);
    }

    public final java.util.List<com.invertbit.games.gtw.b.Cf.Ib> a(com.invertbit.games.gtw.a.Ci.Ia ia, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.util.Map.Entry<java.lang.Integer, java.util.List<com.invertbit.games.gtw.b.Cf.Ib>> entry : this.b.get(ia).entrySet()) {
            if (i >= entry.getKey().intValue()) {
                arrayList.addAll(entry.getValue());
            }
        }
        return arrayList;
    }

    public static com.invertbit.games.gtw.b.Cf.Ia a(com.invertbit.games.gtw.b.Cf.Ib ib) {
        switch (ib) {
            case usa:
                return new com.invertbit.games.gtw.b.Cf.Ia(30, 26, 6);
            case russia:
                return new com.invertbit.games.gtw.b.Cf.Ia(30, 26, 6);
            case china:
                return new com.invertbit.games.gtw.b.Cf.Ia(30, 26, 6);
            case alaska:
                return new com.invertbit.games.gtw.b.Cf.Ia(8, 11, 1);
            case canada:
                return new com.invertbit.games.gtw.b.Cf.Ia(30, 37, 5);
            case mexico:
                return new com.invertbit.games.gtw.b.Cf.Ia(15, 14, 4);
            case greenland:
                return new com.invertbit.games.gtw.b.Cf.Ia(0, 27, 0);
            case europe:
                return new com.invertbit.games.gtw.b.Cf.Ia(30, 25, 5);
            case brazil:
                return new com.invertbit.games.gtw.b.Cf.Ia(20, 24, 5);
            case chile:
                return new com.invertbit.games.gtw.b.Cf.Ia(15, 20, 3);
            case sahara:
                return new com.invertbit.games.gtw.b.Cf.Ia(20, 24, 3);
            case egipt:
                return new com.invertbit.games.gtw.b.Cf.Ia(20, 24, 4);
            case africa:
                return new com.invertbit.games.gtw.b.Cf.Ia(30, 40, 3);
            case middleeast:
                return new com.invertbit.games.gtw.b.Cf.Ia(10, 13, 3);
            case kazakhstan:
                return new com.invertbit.games.gtw.b.Cf.Ia(8, 9, 2);
            case india:
                return new com.invertbit.games.gtw.b.Cf.Ia(30, 29, 5);
            case indonesia:
                return new com.invertbit.games.gtw.b.Cf.Ia(15, 14, 3);
            case australia:
                return new com.invertbit.games.gtw.b.Cf.Ia(15, 24, 3);
            case cuba:
                return new com.invertbit.games.gtw.b.Cf.Ia(2, 1, 1);
            case japan:
                return new com.invertbit.games.gtw.b.Cf.Ia(4, 3, 1);
            default:
                throw new java.lang.IllegalArgumentException("no countrytype defined");
        }
    }
}
