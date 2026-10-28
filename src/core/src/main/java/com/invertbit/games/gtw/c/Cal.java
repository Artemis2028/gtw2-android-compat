package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
final class Cal extends com.badlogic.gdx.f.a.Cg {
    final /* synthetic */ com.invertbit.games.gtw.c.a.Cc a;
    final /* synthetic */ com.invertbit.games.gtw.c.Caj b;

    Cal(com.invertbit.games.gtw.c.Caj caj, com.invertbit.games.gtw.c.a.Cc cc) {
        this.b = caj;
        this.a = cc;
    }

    @Override // com.badlogic.gdx.f.a.Cg
    public final boolean a() {
        if (!this.b.pause().isPaused()) {
            this.a.U = false;
            return false;
        }
        this.a.U = true;
        this.b.a(4);
        this.b.quitToMenu();
        return true;
    }
}
