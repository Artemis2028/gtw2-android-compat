package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
final class Cap extends com.badlogic.gdx.f.a.Cg {
    final /* synthetic */ com.invertbit.games.gtw.c.Can a;

    Cap(com.invertbit.games.gtw.c.Can can) {
        this.a = can;
    }

    @Override // com.badlogic.gdx.f.a.Cg
    public final boolean a() {
        if (com.invertbit.games.gtw.Cc.a().b.d("v1_player.0")) {
            com.invertbit.games.gtw.Ca ca = this.a.e;
            com.invertbit.games.e.Ca ca2 = com.invertbit.games.e.Ca.game2;
            com.invertbit.games.gtw.a.Cg cg = new com.invertbit.games.gtw.a.Cg();
            cg.c();
            com.invertbit.games.gtw.a.Cf cf = new com.invertbit.games.gtw.a.Cf();
            cf.a(com.invertbit.games.gtw.Cc.a(), 0);
            cg.a(cf);
            com.invertbit.games.gtw.a.Ca ca3 = new com.invertbit.games.gtw.a.Ca();
            ca3.a(com.invertbit.games.gtw.Cc.a(), 1);
            cg.a(ca3);
            cg.c = com.invertbit.games.gtw.Cc.a().b("defcon");
            ca.a(ca2, cg);
        } else {
            this.a.e.a(com.invertbit.games.e.Ca.game0, new java.lang.Object[0]);
        }
        return true;
    }
}
