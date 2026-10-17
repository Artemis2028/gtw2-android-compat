package com.invertbit.games.gtw.c.d;

/* loaded from: classes.dex */
public final class Cd {
    static com.invertbit.games.gtw.c.d.Cd a;
    java.util.Properties b;

    public enum Ia {
        TUTORIAL1,
        TUTORIAL2,
        TUTORIAL3,
        TUTORIAL4,
        TUTORIAL5,
        END
    }

    Cd() throws java.io.IOException {
        com.badlogic.gdx.c.Ca caB = com.badlogic.gdx.Cf.e.b("tutorial/tutorial.properties");
        try {
            this.b = new java.util.Properties();
            this.b.load(caB.b());
        } catch (java.lang.Exception e) {
            this.b = new java.util.Properties();
        }
    }

    final com.invertbit.games.gtw.c.d.Cc a() {
        com.invertbit.games.gtw.c.d.Cc cc = new com.invertbit.games.gtw.c.d.Cc();
        cc.f();
        cc.h = com.invertbit.games.gtw.c.d.Cd.Ia.TUTORIAL2;
        com.invertbit.games.gtw.a.Cf cf = new com.invertbit.games.gtw.a.Cf();
        cf.e = com.invertbit.games.gtw.a.Ci.Ia.USA;
        cf.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1520.0f, 0.0f, 1060.0f), cf.e, 5));
        cf.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1520.0f, 0.0f, 990.0f), cf.e, 5.0f, 5, true));
        com.invertbit.games.gtw.a.Ci ci = new com.invertbit.games.gtw.a.Ci();
        ci.e = com.invertbit.games.gtw.a.Ci.Ia.RUSSIA;
        ci.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1360.0f, 0.0f, 1060.0f), ci.e, 5));
        ci.c(com.invertbit.games.gtw.b.b.Ca.a(new com.badlogic.gdx.math.Cl(1360.0f, 0.0f, 990.0f), ci.e, 5.0f, 5, false));
        cc.a(cf);
        cc.a(ci);
        cc.e = this.b.getProperty("TUTORIAL1.title");
        cc.f = this.b.getProperty("TUTORIAL1.description1");
        cc.g = this.b.getProperty("TUTORIAL1.description2");
        return cc;
    }
}
