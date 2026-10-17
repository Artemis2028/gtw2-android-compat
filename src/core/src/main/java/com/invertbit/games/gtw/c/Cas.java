package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
public final class Cas extends com.invertbit.games.gtw.c.Cb {
    public Cas(com.invertbit.games.gtw.Ca ca) {
        super(ca);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void c() {
        super.c();
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void a(java.lang.Object... objArr) {
        super.a(objArr);
        this.o.d();
        com.badlogic.gdx.f.a.a.Ce ce = new com.badlogic.gdx.f.a.a.Ce();
        ce.J = true;
        com.badlogic.gdx.f.a.a.Cc.Ia ia = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.invertbit.games.gtw.b.e.Cg.s37.h);
        com.badlogic.gdx.f.a.a.Cc.Ia ia2 = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s72), com.invertbit.games.gtw.b.e.Cg.s30.h);
        ce.c(new com.badlogic.gdx.f.a.a.Cc("Settings", ia)).n().h().i().d(50.0f);
        ce.i();
        com.badlogic.gdx.f.a.a.Cc cc = new com.badlogic.gdx.f.a.a.Cc("Sound on", ia2);
        if (com.invertbit.games.gtw.b.d.Cp.a().c) {
            cc.a("Sound on");
        } else {
            cc.a("Sound off");
        }
        cc.a(new com.invertbit.games.gtw.c.Cat(this, cc));
        ce.c(cc).m().h().i().d(30.0f);
        ce.i();
        com.badlogic.gdx.f.a.a.Cc cc2 = new com.badlogic.gdx.f.a.a.Cc("", ia2);
        if (com.invertbit.games.gtw.Cb.b().a()) {
            cc2.a("Level: EASY");
        } else {
            cc2.a("Level: HARD");
        }
        cc2.a(new com.invertbit.games.gtw.c.Cau(this, cc2));
        ce.c(cc2).m().h().i().d(30.0f);
        ce.i();
        com.badlogic.gdx.f.a.a.Cc cc3 = new com.badlogic.gdx.f.a.a.Cc("Rate !", ia2);
        cc3.a(new com.invertbit.games.gtw.c.Cav(this));
        ce.c(cc3).m().h().i().d(30.0f);
        ce.i();
        com.badlogic.gdx.f.a.a.Cc cc4 = new com.badlogic.gdx.f.a.a.Cc("invertbit.blogsport.com", ia2);
        cc4.a(new com.invertbit.games.gtw.c.Caw(this));
        ce.c(cc4).m().h().i().d(30.0f);
        ce.i();
        com.badlogic.gdx.f.a.a.Cc cc5 = new com.badlogic.gdx.f.a.a.Cc("More games", ia2);
        cc5.a(new com.invertbit.games.gtw.c.Cax(this));
        ce.c(cc5).m().h().i().d(30.0f);
        ce.i();
        com.invertbit.games.gtw.c.a.Cc cc6 = new com.invertbit.games.gtw.c.a.Cc(100, 100, "<", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h);
        cc6.a(new com.invertbit.games.gtw.c.Cay(this));
        ce.c(cc6).m().j().f(20.0f).d().b(150.0f).k();
        this.o.a(ce);
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        super.b(f);
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.badlogic.gdx.Co
    public final void b() {
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca, com.badlogic.gdx.Co
    public final void a() {
        com.badlogic.gdx.Cf.d.a(this.o);
    }

    static boolean e() {
        com.invertbit.games.gtw.b.d.Cp cpA = com.invertbit.games.gtw.b.d.Cp.a();
        cpA.c = !com.invertbit.games.gtw.b.d.Cp.a().c;
        com.invertbit.games.gtw.Cc.a().b.a("v1_sound", cpA.c);
        com.invertbit.games.gtw.Cc.a().b();
        return com.invertbit.games.gtw.b.d.Cp.a().c;
    }
}
