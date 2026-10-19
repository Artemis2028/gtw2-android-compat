package com.invertbit.games.gtw.b;

/* loaded from: classes.dex */
public class Cf extends com.invertbit.games.gtw.b.Ca implements java.lang.Comparable {
    public java.lang.String e;
    public com.invertbit.games.c.a.a.Cc f;
    public int g;
    public com.invertbit.games.gtw.b.Cf.Ia h;
    public java.util.List<com.badlogic.gdx.math.Cl> i;

    public enum Ib {
        usa,
        russia,
        china,
        alaska,
        canada,
        mexico,
        greenland,
        europe,
        easteurope,
        brazil,
        chile,
        sahara,
        egipt,
        africa,
        middleeast,
        kazakhstan,
        india,
        indonesia,
        australia,
        cuba,
        japan
    }

    public Cf() {
        super(null);
    }

    @Override // com.invertbit.games.gtw.b.Ca
    public final float a(com.badlogic.gdx.math.Cl cl) {
        return Float.MAX_VALUE;
    }

    @Override // java.lang.Comparable
    public int compareTo(java.lang.Object obj) {
        return equals(obj) ? 1 : -1;
    }

    @Override // com.invertbit.games.gtw.b.Ca
    public int hashCode() {
        if (this.e != null) {
            return this.e.hashCode();
        }
        return 1;
    }

    public static class Ia {
        public int a;
        public int b;
        public int c;

        public Ia(int i, int i2, int i3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
        }
    }

    @Override // com.invertbit.games.gtw.b.Ca
    public boolean equals(java.lang.Object obj) {
        if (this.e != null && (obj instanceof com.invertbit.games.gtw.b.Cf)) {
            return this.e.equals(((com.invertbit.games.gtw.b.Cf) obj).e);
        }
        return false;
    }
}
