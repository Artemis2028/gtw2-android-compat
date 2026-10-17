package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
public final class Can extends com.invertbit.games.gtw.c.Cb {
    com.invertbit.games.gtw.b.d.Cj p;

    public Can(com.invertbit.games.gtw.Ca ca) {
        super(ca);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void c() {
        super.c();
        this.p = com.invertbit.games.gtw.b.d.Cj.a();
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void a(java.lang.Object... objArr) {
        super.a(objArr);
        this.o.d();
        float f = c / 2.0f;
        com.badlogic.gdx.f.a.a.Ce ce = new com.badlogic.gdx.f.a.a.Ce();
        ce.J = true;
        com.badlogic.gdx.f.a.a.Cc.Ia ia = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.invertbit.games.gtw.b.e.Cg.s37.h);
        ce.c(new com.badlogic.gdx.f.a.a.Cc("Global Thermonuclear War II", ia)).m().a(2).k();
        ce.i();
        ((com.invertbit.games.gtw.c.a.Cc) ce.c(new com.invertbit.games.gtw.c.a.Cc(600, 400, "New game", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).o().n().k().l().d(5.0f).w).a(new com.invertbit.games.gtw.c.Cao(this));
        ((com.invertbit.games.gtw.c.a.Cc) ce.c(new com.invertbit.games.gtw.c.a.Cc(600, 400, "Continue game", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).o().k().j().d(5.0f).w).a(new com.invertbit.games.gtw.c.Cap(this));
        ce.i();
        ((com.invertbit.games.gtw.c.a.Cc) ce.c(new com.invertbit.games.gtw.c.a.Cc(600, 400, "Settings", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).o().n().i().l().d(5.0f).w).a(new com.invertbit.games.gtw.c.Caq(this));
        ((com.invertbit.games.gtw.c.a.Cc) ce.c(new com.invertbit.games.gtw.c.a.Cc(600, 400, "Help", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).o().i().j().d(5.0f).a(600.0f).w).a(new com.invertbit.games.gtw.c.Car(this));
        ce.i();
        ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia)).a(f);
        ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia)).a(f);
        this.o.a(ce);
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        super.b(f);
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.badlogic.gdx.Co
    public final void b() {
        this.o.b();
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca, com.badlogic.gdx.Co
    public final void a() {
        super.a();
        com.badlogic.gdx.Cf.d.a(this.o);
        com.badlogic.gdx.Cf.d.a(false);
    }
}
