package com.invertbit.games.gtw.d;

/* loaded from: classes.dex */
public final class Ca {
    static com.invertbit.games.gtw.d.Ca a;
    java.util.Map<com.invertbit.games.gtw.d.Ca.Ia, com.badlogic.gdx.graphics.a.Cj> b;

    public enum Ia {
        defaultBorder,
        russiaBorder,
        usaBorder,
        chinaBorder,
        russiaMapObject,
        usaMapObject,
        chinaMapObject,
        defaultMapObject,
        explosionObject,
        pointer,
        background,
        russiaInactiveMapObject,
        usaInactiveMapObject,
        chinaInactiveMapObject
    }

    public static com.invertbit.games.gtw.d.Ca a() {
        if (a == null) {
            a = new com.invertbit.games.gtw.d.Ca();
        }
        return a;
    }

    private Ca() {
        c();
    }

    private void c() {
        this.b = new java.util.HashMap(com.invertbit.games.gtw.d.Ca.Ia.values().length);
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.defaultBorder, new com.invertbit.games.d.Ca(new com.badlogic.gdx.graphics.Cb(0.2f, 0.2f, 0.2f, 0.5f)));
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.defaultMapObject, new com.invertbit.games.d.Ca(com.badlogic.gdx.graphics.Cb.c));
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.explosionObject, new com.invertbit.games.d.Ca(com.badlogic.gdx.graphics.Cb.c, "rocketobject"));
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.pointer, new com.invertbit.games.d.Ca(com.badlogic.gdx.graphics.Cb.c, "pointer"));
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.russiaBorder, new com.invertbit.games.d.Ca(com.badlogic.gdx.graphics.Cb.y));
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.usaBorder, new com.invertbit.games.d.Ca(com.badlogic.gdx.graphics.Cb.h));
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.chinaBorder, new com.invertbit.games.d.Ca(com.badlogic.gdx.graphics.Cb.l));
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.russiaMapObject, new com.invertbit.games.d.Ca(new com.badlogic.gdx.graphics.Cb(1.0f, 0.37f, 0.0f, 0.5f), "mapobject"));
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.usaMapObject, new com.invertbit.games.d.Ca(new com.badlogic.gdx.graphics.Cb(0.0f, 0.5f, 1.0f, 0.5f), "mapobject"));
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.chinaMapObject, new com.invertbit.games.d.Ca(new com.badlogic.gdx.graphics.Cb(0.0f, 1.0f, 0.5f, 0.5f), "mapobject"));
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.russiaInactiveMapObject, new com.invertbit.games.d.Ca(new com.badlogic.gdx.graphics.Cb(0.5f, 0.185f, 0.0f, 0.5f), "mapobject"));
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.usaInactiveMapObject, new com.invertbit.games.d.Ca(new com.badlogic.gdx.graphics.Cb(0.0f, 0.25f, 0.5f, 0.5f), "mapobject"));
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.chinaInactiveMapObject, new com.invertbit.games.d.Ca(new com.badlogic.gdx.graphics.Cb(0.0f, 0.5f, 0.25f, 0.5f), "mapobject"));
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.background, new com.invertbit.games.d.Ca(com.badlogic.gdx.graphics.Cb.c, "background"));
        for (com.badlogic.gdx.graphics.a.Cj cj : this.b.values()) {
            cj.a();
            ((com.invertbit.games.d.Ca) cj).h = new com.badlogic.gdx.math.Ck(2880.0f, 1920.0f);
        }
    }

    public final com.badlogic.gdx.graphics.a.Cj a(com.invertbit.games.gtw.d.Ca.Ia ia) {
        return this.b.get(ia);
    }

    public final void b() {
        java.util.Iterator<com.badlogic.gdx.graphics.a.Cj> it = this.b.values().iterator();
        while (it.hasNext()) {
            it.next().b();
        }
        a = null;
    }
}
