package com.invertbit.games.gtw.b.e;

/* loaded from: classes.dex */
public final class Ch {
    public static final com.badlogic.gdx.math.Ck a = new com.badlogic.gdx.math.Ck();
    public static final float b = com.badlogic.gdx.Cf.b.a() / 2880.0f;
    public static final float c = com.badlogic.gdx.Cf.b.b() / 1920.0f;

    public static com.badlogic.gdx.math.Ck a(com.badlogic.gdx.math.Cl cl) {
        if (cl == null) {
            return a;
        }
        return new com.badlogic.gdx.math.Ck(((720.0f - cl.a) * 180.0f) / 720.0f, ((960.0f - cl.c) * 180.0f) / 960.0f);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static float[] a(com.invertbit.games.gtw.b.a.Ca.Ia ia) {
        float[] fArr = new float[2];
        switch (com.invertbit.games.gtw.b.e.Ci.a[ia.ordinal()]) {
            case 1:
                fArr[0] = -12.5f;
                fArr[1] = -19.0f;
                return fArr;
            case 2:
                fArr[0] = -24.0f;
                fArr[1] = -6.936f;
                return fArr;
            case 3:
            case 4:
            case 5:
                fArr[0] = -15.0f;
                fArr[1] = -19.0f;
                return fArr;
            case 6:
                fArr[0] = -22.51f;
                fArr[1] = -19.0f;
                return fArr;
            default:
                return fArr;
        }
    }

    public static java.lang.String b(com.invertbit.games.gtw.b.a.Ca.Ia ia) {
        switch (com.invertbit.games.gtw.b.e.Ci.a[ia.ordinal()]) {
            case 1:
                return "ICBM silos";
            case 2:
                return "Population";
            case 3:
            case 4:
            case 5:
                return "Bombers";
            case 6:
                return "Submarines";
            default:
                return "";
        }
    }

    public static java.lang.String c(com.invertbit.games.gtw.b.a.Ca.Ia ia) {
        switch (com.invertbit.games.gtw.b.e.Ci.a[ia.ordinal()]) {
            case 1:
                return "Slots avaiable: 0/10";
            case 2:
                return "Slots avaiable: 0/10";
            case 3:
            case 4:
            case 5:
                return "Slots avaiable: 0/10";
            case 6:
                return "Slots avaiable: 0/10";
            default:
                return "";
        }
    }

    public static java.lang.String a(com.invertbit.games.gtw.a.Cj.Ia ia) {
        switch (com.invertbit.games.gtw.b.e.Ci.b[ia.ordinal()]) {
            case 1:
                return "Population";
            case 2:
                return "Intercontinental ballistic missiles";
            case 3:
                return "Strategic nuclear bombers";
            case 4:
                return "Tactical nuclear submarines";
            default:
                return "";
        }
    }

    public static java.lang.String a(int i, com.invertbit.games.gtw.a.Cj.Ia ia) {
        switch (com.invertbit.games.gtw.b.e.Ci.b[ia.ordinal()]) {
            case 1:
                return i + "mln x ";
            case 2:
            case 3:
            case 4:
                return i + "MT x ";
            default:
                return "";
        }
    }

    public static java.lang.String b(int i, com.invertbit.games.gtw.a.Cj.Ia ia) {
        switch (com.invertbit.games.gtw.b.e.Ci.b[ia.ordinal()]) {
            case 1:
                return i + " mln";
            case 2:
            case 3:
            case 4:
                return i + " MT";
            default:
                return "";
        }
    }
}
