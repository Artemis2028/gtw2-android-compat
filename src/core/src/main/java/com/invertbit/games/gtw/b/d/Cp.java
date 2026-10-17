package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public final class Cp {
    public static com.invertbit.games.gtw.b.d.Cp a;
    public boolean c;
    com.invertbit.games.gtw.Cc d = com.invertbit.games.gtw.Cc.a();
    public java.util.Map<com.invertbit.games.gtw.b.d.Cp.Ia, com.badlogic.gdx.b.Cb> b = new java.util.HashMap(com.invertbit.games.gtw.b.d.Cp.Ia.values().length);

    public enum Ia {
        impact_ogg,
        button_ogg
    }

    private Cp() {
        for (com.invertbit.games.gtw.b.d.Cp.Ia ia : com.invertbit.games.gtw.b.d.Cp.Ia.values()) {
            this.b.put(ia, com.badlogic.gdx.Cf.c.a(com.badlogic.gdx.Cf.e.b("sounds/" + ia.name().toLowerCase().replaceAll("_", "."))));
        }
        if (this.d.a("sound")) {
            this.c = this.d.b.a("v1_sound");
        } else {
            this.c = true;
        }
    }

    public static com.invertbit.games.gtw.b.d.Cp a() {
        if (a == null) {
            a = new com.invertbit.games.gtw.b.d.Cp();
        }
        return a;
    }

    public static void b() {
        com.invertbit.games.gtw.b.d.Cp cp = a;
        com.invertbit.games.gtw.b.d.Cp.Ia ia = com.invertbit.games.gtw.b.d.Cp.Ia.button_ogg;
        if (!cp.c || !cp.b.containsKey(ia)) {
            return;
        }
        cp.b.get(ia).a(0.45f);
    }
}
