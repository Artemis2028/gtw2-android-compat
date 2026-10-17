package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
final class Cr extends com.badlogic.gdx.f.a.Cg {
    final /* synthetic */ com.invertbit.games.gtw.c.a.Ce a;
    final /* synthetic */ com.invertbit.games.gtw.c.Cp b;

    Cr(com.invertbit.games.gtw.c.Cp cp, com.invertbit.games.gtw.c.a.Ce ce) {
        this.b = cp;
        this.a = ce;
    }

    @Override // com.badlogic.gdx.f.a.Cg
    public final boolean a() {
        com.invertbit.games.gtw.b.Cf cf = (com.invertbit.games.gtw.b.Cf) this.a.p;
        if (com.invertbit.games.gtw.c.Cp.a(this.b) > com.invertbit.games.gtw.c.Cp.b(this.b).c || com.invertbit.games.gtw.c.Cp.b(this.b).a().d.contains(cf)) {
            return true;
        }
        cf.c = com.invertbit.games.gtw.c.Cp.b(this.b).a().e;
        com.invertbit.games.gtw.c.Cp.b(this.b).a().c(cf);
        com.invertbit.games.gtw.c.Cp.c(this.b);
        com.invertbit.games.gtw.c.Cp.d(this.b).a();
        if (!com.invertbit.games.gtw.c.Cp.b(this.b).e()) {
            com.invertbit.games.gtw.c.Cp.e(this.b).a("Alliances map");
            return true;
        }
        return true;
    }
}
