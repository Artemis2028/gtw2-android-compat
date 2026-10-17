package com.invertbit.games.gtw.a;

/* loaded from: classes.dex */
public final class Ce {
    public float d;
    public float e;
    public java.util.List<com.invertbit.games.gtw.a.Ci> a = new java.util.ArrayList();
    public java.util.List<java.lang.String> c = new java.util.ArrayList();
    public java.util.Map<com.invertbit.games.gtw.a.Ci.Ia, com.invertbit.games.gtw.a.Ci> b = new java.util.EnumMap(com.invertbit.games.gtw.a.Ci.Ia.class);

    public final void a(java.lang.String str) {
        this.c.add(str);
        if (this.c.size() > 5) {
            this.c.remove(0);
        }
    }

    public final void a(com.invertbit.games.gtw.a.Ci ci) {
        this.a.add(ci);
        this.b.put(ci.e, ci);
    }

    public final com.invertbit.games.gtw.a.Ci a(com.invertbit.games.gtw.a.Ci.Ia ia) {
        return this.b.get(ia);
    }

    public final boolean a() {
        return this.d >= 0.0f;
    }
}
