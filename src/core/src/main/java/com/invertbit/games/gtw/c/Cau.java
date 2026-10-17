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
        // Default difficulty for new games: EASY -> NORMAL -> HARD.
        com.invertbit.games.gtw.Cb.Ia next = com.invertbit.games.gtw.Cb.b().d().next();
        com.invertbit.games.gtw.Cb.b().a(next);
        this.a.a("Level: " + next.name());
        return true;
    }
}
