package com.invertbit.games.gtw.b.a;

/* loaded from: classes.dex */
public final class Ca {
    public static com.invertbit.games.gtw.b.a.Ca a;
    public com.badlogic.gdx.graphics.a.e.Ci b = new com.badlogic.gdx.graphics.a.e.Ci();
    public com.badlogic.gdx.graphics.a.Cd c = new com.badlogic.gdx.graphics.a.Cd();
    public java.util.Map<com.invertbit.games.gtw.b.a.Ca.Ia, com.badlogic.gdx.graphics.g2d.Cg> d;
    java.util.List<com.badlogic.gdx.graphics.a.Ce> e;
    java.util.Map<java.lang.String, com.invertbit.games.gtw.b.Cf> f;
    java.util.Map<com.invertbit.games.gtw.b.a.Ca.Ia, com.badlogic.gdx.graphics.a.Ce> g;

    public enum Ia {
        silo,
        city,
        usabomber,
        russiabomber,
        chinabomber,
        submarine,
        trapeze1,
        trapeze2,
        trapeze3,
        trapeze4
    }

    private Ca() {
    }

    public static com.invertbit.games.gtw.b.a.Ca a() {
        if (a == null) {
            a = new com.invertbit.games.gtw.b.a.Ca();
        }
        return a;
    }

    public final java.util.Map<java.lang.String, com.invertbit.games.gtw.b.Cf> b() {
        d();
        return this.f;
    }

    public final com.badlogic.gdx.graphics.a.Ce a(com.invertbit.games.gtw.b.a.Ca.Ia ia) {
        return c().get(ia);
    }

    public final java.util.Map<com.invertbit.games.gtw.b.a.Ca.Ia, com.badlogic.gdx.graphics.a.Ce> c() {
        if (this.g != null) {
            return this.g;
        }
        this.g = new java.util.EnumMap(com.invertbit.games.gtw.b.a.Ca.Ia.class);
        try {
            com.invertbit.games.c.a.a.Cd cdA = new com.invertbit.games.c.a.Ca().a("objects.svg");
            for (com.invertbit.games.gtw.b.a.Ca.Ia ia : com.invertbit.games.gtw.b.a.Ca.Ia.values()) {
                com.invertbit.games.b.a.Ca ca = new com.invertbit.games.b.a.Ca();
                for (com.invertbit.games.c.a.a.Cb cb : cdA.c.a()) {
                    if (cb.a.equals(ia.name())) {
                        ca.a(cb);
                    }
                }
                java.util.Map<com.invertbit.games.gtw.b.a.Ca.Ia, com.badlogic.gdx.graphics.a.Ce> map = this.g;
                ca.c = 0.8f;
                ca.a = a().c;
                ca.b = a().b;
                map.put(ia, ca.a(ia.name()));
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return this.g;
    }

    public final java.util.List<com.badlogic.gdx.graphics.a.Ce> d() {
        if (this.e != null) {
            return this.e;
        }
        this.e = new java.util.ArrayList();
        this.f = new java.util.HashMap();
        try {
            com.invertbit.games.c.a.a.Cd cdA = new com.invertbit.games.c.a.Ca().a("world.svg");
            java.util.Map<java.lang.String, com.invertbit.games.c.a.a.Cc> map = cdA.c.b;
            for (com.invertbit.games.c.a.a.Cb cb : cdA.c.a()) {
                java.util.List<com.badlogic.gdx.graphics.a.Ce> list = this.e;
                com.invertbit.games.b.a.Ca caA = new com.invertbit.games.b.a.Ca().a(cb);
                caA.a = this.c;
                caA.b = this.b;
                list.add(caA.a(cb.a));
                com.invertbit.games.gtw.b.Cf cf = new com.invertbit.games.gtw.b.Cf();
                cf.e = cb.a;
                cf.f = map.get(cf.e);
                cf.g = 1;
                if (!this.f.containsKey(cb.a)) {
                    if (cdA.c.b().containsKey(cf.e)) {
                        cf.i = com.invertbit.games.gtw.b.e.Ce.a(cdA.c.b().get(cf.e));
                    }
                    this.f.put(cb.a, cf);
                }
            }
            com.invertbit.games.gtw.b.Cf cf2 = new com.invertbit.games.gtw.b.Cf();
            cf2.e = "submarine";
            if (cdA.c.b().containsKey(cf2.e)) {
                cf2.i = com.invertbit.games.gtw.b.e.Ce.a(cdA.c.b().get(cf2.e));
            }
            this.f.put("submarine", cf2);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
        return this.e;
    }

    public final com.badlogic.gdx.graphics.g2d.Cg b(com.invertbit.games.gtw.b.a.Ca.Ia ia) {
        return this.d.get(ia);
    }
}
