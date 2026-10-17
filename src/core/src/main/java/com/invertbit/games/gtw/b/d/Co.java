package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public class Co extends com.invertbit.games.gtw.b.d.Cb {
    protected com.badlogic.gdx.graphics.a.Ce b;
    com.invertbit.games.gtw.b.d.Cm c;

    public Co(com.invertbit.games.gtw.b.d.Cm cm) {
        this.c = cm;
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    protected void c() {
        super.c();
        this.b = com.invertbit.games.gtw.b.a.Ca.a().a(com.invertbit.games.gtw.b.a.Ca.Ia.silo);
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public java.lang.Class a() {
        return com.invertbit.games.gtw.b.Ci.class;
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void a(com.invertbit.games.gtw.b.Ca ca) {
        com.badlogic.gdx.graphics.a.Cg cg = new com.badlogic.gdx.graphics.a.Cg(this.b);
        a(cg);
        cg.g = ca;
        cg.f.b(ca.b);
        this.a.add(cg);
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void b(com.invertbit.games.gtw.a.Cd cd) {
        super.b(cd);
        com.invertbit.games.gtw.b.d.Cm cm = this.c;
        if (!(cd.a instanceof com.invertbit.games.gtw.b.Ci)) {
            return;
        }
        cm.c.addAll(((com.invertbit.games.gtw.b.Ci) cd.a).h);
    }

    protected void a(com.badlogic.gdx.graphics.a.Cg cg) {
        cg.f.a(-12.5f, 0.0f, -19.0f);
    }

    @Override // com.invertbit.games.gtw.b.d.Cb, com.invertbit.games.gtw.b.c.Ca
    public final void a(long j, com.invertbit.games.gtw.b.d.Cl cl) {
        super.a(j, cl);
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public void a(com.invertbit.games.gtw.a.Cd cd) {
        com.invertbit.games.gtw.b.Cb cb = cd.b;
        cb.e--;
        if (cb.e < 0) {
            cb.e = 0;
        }
        this.c.a(cd);
    }
}
