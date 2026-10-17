package com.invertbit.games.b.a;

/* loaded from: classes.dex */
public final class Cb extends com.invertbit.games.b.a.Ca {
    java.util.List<com.badlogic.gdx.math.Ck> d;

    public Cb(java.util.List<com.badlogic.gdx.math.Ck> list) {
        this.d = list;
        a(new com.invertbit.games.c.a.a.Cb());
    }

    @Override // com.invertbit.games.b.a.Ca
    protected final java.util.List<com.invertbit.games.b.a.Ca.Ia> b(com.invertbit.games.c.a.a.Cb cb) {
        java.util.ArrayList arrayList = new java.util.ArrayList(this.d.size());
        int i = 1;
        while (true) {
            int i2 = i;
            if (i2 >= this.d.size()) {
                return arrayList;
            }
            com.badlogic.gdx.math.Ck ck = this.d.get(i2 - 1);
            com.badlogic.gdx.math.Ck ck2 = this.d.get(i2);
            arrayList.add(new com.invertbit.games.b.a.Ca.Ia(new com.badlogic.gdx.math.Cl(ck.d, 0.0f, ck.e), new com.badlogic.gdx.math.Cl(ck2.d, 0.0f, ck2.e)));
            i = i2 + 1;
        }
    }
}
