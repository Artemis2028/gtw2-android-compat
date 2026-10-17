package com.invertbit.games.gtw.a;

/* loaded from: classes.dex */
public final class Cd implements java.lang.Comparable<com.invertbit.games.gtw.a.Cd> {
    public com.invertbit.games.gtw.b.Ca a;
    public com.invertbit.games.gtw.b.Cb b;
    java.lang.Float c;

    @Override // java.lang.Comparable
    public final int compareTo(com.invertbit.games.gtw.a.Cd cd) {
        return this.c.compareTo(cd.c);
    }

    public Cd(com.invertbit.games.gtw.b.Cb cb, com.invertbit.games.gtw.b.Ca ca) {
        this.a = ca;
        this.b = cb;
        if (cb.c == null) {
            throw new java.lang.IllegalStateException("playerType is null " + cb);
        }
        float fD = cb.b.d(ca.b);
        if (fD > 1440.0f) {
            com.badlogic.gdx.math.Cl cl = new com.badlogic.gdx.math.Cl(cb.b);
            if (cb.b.a < ca.b.a) {
                cl.a += 2880.0f;
            } else {
                cl.a -= 2880.0f;
            }
            fD = cl.d(ca.b);
        }
        this.c = java.lang.Float.valueOf(fD);
    }

    public final java.lang.String toString() {
        return "AttackBean{distance=" + this.c + ", target=" + this.a + " target.isDestroyed=" + this.a.d + ", source=" + this.b + '}';
    }

    public final boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        com.invertbit.games.gtw.a.Cd cd = (com.invertbit.games.gtw.a.Cd) obj;
        return this.a.equals(cd.a) && this.b.equals(cd.b);
    }

    public final int hashCode() {
        return (this.a.hashCode() * 31) + this.b.hashCode();
    }
}
