package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public final class Cq extends com.invertbit.games.gtw.b.d.Co {
    public Cq(com.invertbit.games.gtw.b.d.Cm cm) {
        super(cm);
    }

    @Override // com.invertbit.games.gtw.b.d.Co, com.invertbit.games.gtw.b.d.Cb
    protected final void c() {
        super.c();
        this.b = com.invertbit.games.gtw.b.a.Ca.a().a(com.invertbit.games.gtw.b.a.Ca.Ia.submarine);
    }

    @Override // com.invertbit.games.gtw.b.d.Co, com.invertbit.games.gtw.b.d.Cb
    public final java.lang.Class a() {
        return com.invertbit.games.gtw.b.Cj.class;
    }

    @Override // com.invertbit.games.gtw.b.d.Co
    protected final void a(com.badlogic.gdx.graphics.a.Cg cg) {
        cg.f.a(-22.51f, 0.0f, -19.0f);
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void a(com.badlogic.gdx.graphics.a.Cg cg, com.badlogic.gdx.graphics.a.Cf cf, com.invertbit.games.gtw.d.Ca ca) {
        if (((com.invertbit.games.gtw.b.Cj) cg.g).j || ((com.invertbit.games.gtw.b.Cj) cg.g).i) {
            super.a(cg, cf, ca);
        }
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    protected final void a(com.badlogic.gdx.graphics.a.Cg cg, com.badlogic.gdx.graphics.g2d.Ch ch, com.badlogic.gdx.graphics.Ca ca) {
        if (((com.invertbit.games.gtw.b.Cj) cg.g).j || ((com.invertbit.games.gtw.b.Cj) cg.g).i) {
            super.a(cg, ch, ca);
        }
    }

    @Override // com.invertbit.games.gtw.b.d.Co, com.invertbit.games.gtw.b.d.Cb
    public final void a(com.invertbit.games.gtw.a.Cd cd) {
        ((com.invertbit.games.gtw.b.Cj) cd.b).j = true;
        super.a(cd);
    }
}
