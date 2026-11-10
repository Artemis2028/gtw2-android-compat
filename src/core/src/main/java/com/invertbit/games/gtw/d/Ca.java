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
        chinaInactiveMapObject,
        europeBorder,
        africaBorder,
        southAmericaBorder,
        europeMapObject,
        africaMapObject,
        southAmericaMapObject,
        europeInactiveMapObject,
        africaInactiveMapObject,
        southAmericaInactiveMapObject
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
        a(com.invertbit.games.gtw.a.Ci.Ia.EUROPE, com.invertbit.games.gtw.d.Ca.Ia.europeBorder, com.invertbit.games.gtw.d.Ca.Ia.europeMapObject, com.invertbit.games.gtw.d.Ca.Ia.europeInactiveMapObject);
        a(com.invertbit.games.gtw.a.Ci.Ia.AFRICA, com.invertbit.games.gtw.d.Ca.Ia.africaBorder, com.invertbit.games.gtw.d.Ca.Ia.africaMapObject, com.invertbit.games.gtw.d.Ca.Ia.africaInactiveMapObject);
        a(com.invertbit.games.gtw.a.Ci.Ia.SOUTH_AMERICA, com.invertbit.games.gtw.d.Ca.Ia.southAmericaBorder, com.invertbit.games.gtw.d.Ca.Ia.southAmericaMapObject, com.invertbit.games.gtw.d.Ca.Ia.southAmericaInactiveMapObject);
        this.b.put(com.invertbit.games.gtw.d.Ca.Ia.background, new com.invertbit.games.d.Ca(com.badlogic.gdx.graphics.Cb.c, "background"));
        for (com.badlogic.gdx.graphics.a.Cj cj : this.b.values()) {
            cj.a();
            ((com.invertbit.games.d.Ca) cj).h = new com.badlogic.gdx.math.Ck(2880.0f, 1920.0f);
        }
    }

    /** Materials of an added side, built like the originals: opaque border, 0.5-alpha units, half-bright inactive units. */
    private void a(com.invertbit.games.gtw.a.Ci.Ia side, com.invertbit.games.gtw.d.Ca.Ia border, com.invertbit.games.gtw.d.Ca.Ia mapObject, com.invertbit.games.gtw.d.Ca.Ia inactive) {
        float[] c = com.invertbit.games.gtw.nations.Nations.rgb(side);
        this.b.put(border, new com.invertbit.games.d.Ca(new com.badlogic.gdx.graphics.Cb(c[0], c[1], c[2], 1.0f)));
        this.b.put(mapObject, new com.invertbit.games.d.Ca(new com.badlogic.gdx.graphics.Cb(c[0], c[1], c[2], 0.5f), "mapobject"));
        this.b.put(inactive, new com.invertbit.games.d.Ca(new com.badlogic.gdx.graphics.Cb(c[0] * 0.5f, c[1] * 0.5f, c[2] * 0.5f, 0.5f), "mapobject"));
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
