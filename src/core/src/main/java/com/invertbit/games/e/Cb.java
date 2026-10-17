package com.invertbit.games.e;

/* loaded from: classes.dex */
public final class Cb {
    public java.util.List<com.invertbit.games.e.Cc> a = new java.util.ArrayList();
    public com.invertbit.games.e.Cc b;
    com.badlogic.gdx.Ce c;

    public Cb(com.badlogic.gdx.Ce ce) {
        this.c = ce;
    }

    public final void a(com.invertbit.games.e.Cc cc) {
        this.a.add(cc);
    }

    public final void a(java.lang.Enum<?> r4, java.lang.Object... objArr) {
        for (com.invertbit.games.e.Cc cc : this.a) {
            if (cc.a.equals(r4)) {
                this.b = cc;
                cc.c.a(objArr);
                this.c.a(cc.c);
                return;
            }
        }
    }
}
