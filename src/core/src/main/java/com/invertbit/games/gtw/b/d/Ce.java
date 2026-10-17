package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public final class Ce extends com.invertbit.games.gtw.b.d.Cb {
    final float b = 48.0f;
    final float c = 13.872f;
    com.badlogic.gdx.graphics.a.Ce d;

    @Override // com.invertbit.games.gtw.b.d.Cb
    protected final void c() {
        super.c();
        this.d = com.invertbit.games.gtw.b.a.Ca.a().a(com.invertbit.games.gtw.b.a.Ca.Ia.city);
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final java.lang.Class a() {
        return com.invertbit.games.gtw.b.Cd.class;
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void a(com.invertbit.games.gtw.b.Ca ca) {
        com.badlogic.gdx.graphics.a.Cg cg = new com.badlogic.gdx.graphics.a.Cg(this.d);
        cg.g = ca;
        cg.f.a(-24.0f, 0.0f, -13.872f);
        cg.f.b(ca.b);
        this.a.add(cg);
    }
}
