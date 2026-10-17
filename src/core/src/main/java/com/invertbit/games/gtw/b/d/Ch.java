package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
final class Ch implements java.util.Comparator<com.badlogic.gdx.graphics.a.Cg> {
    final /* synthetic */ com.invertbit.games.gtw.b.d.Cg a;

    Ch(com.invertbit.games.gtw.b.d.Cg cg) {
        this.a = cg;
    }

    @Override // java.util.Comparator
    public final int compare(com.badlogic.gdx.graphics.a.Cg cg, com.badlogic.gdx.graphics.a.Cg cg2) {
        com.badlogic.gdx.graphics.a.Cg cg3 = cg;
        com.badlogic.gdx.graphics.a.Cg cg4 = cg2;
        if (cg3.g != null) {
            if (cg4.g == null) {
                return 1;
            }
            com.invertbit.games.gtw.b.Cf cf = (com.invertbit.games.gtw.b.Cf) cg3.g;
            com.invertbit.games.gtw.b.Cf cf2 = (com.invertbit.games.gtw.b.Cf) cg4.g;
            if (cf.c != null && (cf2.c == null || this.a.b.e.equals(cf))) {
                return 1;
            }
        }
        return -1;
    }
}
