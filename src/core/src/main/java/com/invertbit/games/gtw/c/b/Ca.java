package com.invertbit.games.gtw.c.b;

/* loaded from: classes.dex */
public final class Ca {
    public static com.badlogic.gdx.graphics.a.Cj a(com.invertbit.games.gtw.a.Ci.Ia ia, com.invertbit.games.gtw.d.Ca ca) {
        switch (ia) {
            case USA:
                return ca.a(com.invertbit.games.gtw.d.Ca.Ia.usaBorder);
            case RUSSIA:
                return ca.a(com.invertbit.games.gtw.d.Ca.Ia.russiaBorder);
            case CHINA:
                return ca.a(com.invertbit.games.gtw.d.Ca.Ia.chinaBorder);
            default:
                return ca.a(com.invertbit.games.gtw.d.Ca.Ia.defaultBorder);
        }
    }
}
