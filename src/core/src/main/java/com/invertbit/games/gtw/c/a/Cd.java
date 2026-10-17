package com.invertbit.games.gtw.c.a;

/* loaded from: classes.dex */
final class Cd extends com.badlogic.gdx.f.a.Cg {
    final /* synthetic */ com.invertbit.games.gtw.c.a.Cc a;

    Cd(com.invertbit.games.gtw.c.a.Cc cc) {
        this.a = cc;
    }

    @Override // com.badlogic.gdx.f.a.Cg
    public final void a(com.badlogic.gdx.f.a.Cf cf, float f, float f2, int i, int i2) {
        super.a(cf, f, f2, i, i2);
        this.a.M = false;
    }

    @Override // com.badlogic.gdx.f.a.Cg
    public final boolean a() {
        this.a.M = true;
        if (com.invertbit.games.gtw.c.a.Cc.a(this.a)) {
            com.invertbit.games.gtw.b.d.Cp.a();
            com.invertbit.games.gtw.b.d.Cp.b();
        }
        return true;
    }
}
