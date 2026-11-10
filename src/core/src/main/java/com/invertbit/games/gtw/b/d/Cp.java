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
        com.invertbit.games.gtw.settings.GameSettings.loadFromPrefs();
        this.c = com.invertbit.games.gtw.settings.GameSettings.get().soundOn();
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
        float vol = com.invertbit.games.gtw.settings.GameSettings.get().scaledVolume(com.invertbit.games.gtw.settings.GameSettings.BUTTON_BASE);
        if (vol <= 0.0f) {
            return;
        }
        cp.b.get(ia).a(vol);
    }

    /** Impact at the original 1.0 base, scaled by the user's volume and mute. */
    public static void playImpact() {
        com.invertbit.games.gtw.b.d.Cp cp = a;
        if (cp == null) {
            return;
        }
        com.invertbit.games.gtw.b.d.Cp.Ia ia = com.invertbit.games.gtw.b.d.Cp.Ia.impact_ogg;
        if (!cp.c || !cp.b.containsKey(ia)) {
            return;
        }
        float vol = com.invertbit.games.gtw.settings.GameSettings.get().scaledVolume(com.invertbit.games.gtw.settings.GameSettings.IMPACT_BASE);
        if (vol <= 0.0f) {
            return;
        }
        cp.b.get(ia).a(vol);
    }
}
