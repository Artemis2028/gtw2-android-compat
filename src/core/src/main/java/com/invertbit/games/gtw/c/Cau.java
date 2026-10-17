package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
final class Cau extends com.badlogic.gdx.f.a.Cg {
    final /* synthetic */ com.badlogic.gdx.f.a.a.Cc a;
    final /* synthetic */ com.invertbit.games.gtw.c.Cas b;

    Cau(com.invertbit.games.gtw.c.Cas cas, com.badlogic.gdx.f.a.a.Cc cc) {
        this.b = cas;
        this.a = cc;
    }

    @Override // com.badlogic.gdx.f.a.Cg
    public final boolean a() {
        if (com.invertbit.games.gtw.Cb.b().a()) {
            com.invertbit.games.gtw.Cb.b().a(com.invertbit.games.gtw.Cb.Ia.HARD);
            this.a.a("Level: HARD");
            return true;
        }
        com.invertbit.games.gtw.Cb.b().a(com.invertbit.games.gtw.Cb.Ia.EASY);
        this.a.a("Level: EASY");
        return true;
    }
}
