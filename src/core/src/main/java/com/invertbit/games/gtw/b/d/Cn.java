package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public final class Cn implements com.invertbit.games.gtw.b.c.Ca {
    com.badlogic.gdx.math.Cl a = new com.badlogic.gdx.math.Cl(0.0f, 0.0f, 0.0f);
    com.badlogic.gdx.graphics.a.Cg b = new com.badlogic.gdx.graphics.a.Cg(com.invertbit.games.gtw.b.a.Ca.a().b.a(new com.badlogic.gdx.graphics.a.Cd()));

    public Cn() {
        this.b.g = this.a;
    }

    public final void a(com.badlogic.gdx.graphics.a.Cg cg) {
        if (cg.g instanceof com.invertbit.games.gtw.b.Ca) {
            com.badlogic.gdx.math.Cl cl = (com.badlogic.gdx.math.Cl) this.b.g;
            this.b.f.a(cl.a * (-1.0f), 0.0f, cl.c * (-1.0f));
            this.b.f.b(((com.invertbit.games.gtw.b.Ca) cg.g).b);
            this.b.g = ((com.invertbit.games.gtw.b.Ca) cg.g).b;
        }
    }

    public final void a() {
        com.badlogic.gdx.math.Cl cl = (com.badlogic.gdx.math.Cl) this.b.g;
        this.b.f.a(cl.a * (-1.0f), 0.0f, cl.c * (-1.0f));
        this.b.g = this.a;
    }

    @Override // com.invertbit.games.gtw.b.c.Ca
    public final boolean a(long j) {
        return true;
    }

    @Override // com.invertbit.games.gtw.b.c.Ca
    public final void a(long j, com.invertbit.games.gtw.b.d.Cl cl) {
    }

    @Override // com.invertbit.games.gtw.b.c.Ca
    public final void a(com.badlogic.gdx.graphics.g2d.Ch ch, com.badlogic.gdx.graphics.Ca ca) {
    }

    @Override // com.invertbit.games.gtw.b.c.Ca
    public final void a(com.badlogic.gdx.graphics.a.Cf cf, com.invertbit.games.gtw.d.Ca ca) {
        if (this.b.g != this.a) {
            cf.a(this.b, ca.a(com.invertbit.games.gtw.d.Ca.Ia.pointer));
            this.b.f.a(2880.0f, 0.0f, 0.0f);
            cf.a(this.b, ca.a(com.invertbit.games.gtw.d.Ca.Ia.pointer));
            this.b.f.a(-2880.0f, 0.0f, 0.0f);
        }
    }
}
