package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
final class Cak extends com.badlogic.gdx.f.a.Cg {
    final /* synthetic */ com.invertbit.games.gtw.c.a.Cc a;
    final /* synthetic */ com.invertbit.games.gtw.c.Caj b;

    Cak(com.invertbit.games.gtw.c.Caj caj, com.invertbit.games.gtw.c.a.Cc cc) {
        this.b = caj;
        this.a = cc;
    }

    @Override // com.badlogic.gdx.f.a.Cg
    public final boolean a() {
        if (!this.b.i) {
            this.a.U = false;
            return false;
        }
        this.a.U = true;
        this.b.i = false;
        return true;
    }
}
