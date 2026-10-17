package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
public abstract class Ca implements com.badlogic.gdx.Co {
    public static int a;
    public static int b;
    public static int c = 1920;
    public static int d = 1080;
    protected com.invertbit.games.gtw.Ca e;
    public com.badlogic.gdx.graphics.Ca f;
    protected com.invertbit.games.a.Ca g;
    protected com.badlogic.gdx.Cj h;
    public boolean i;

    public abstract void b(float f);

    public Ca(com.invertbit.games.gtw.Ca ca) {
        this.e = ca;
        this.g = ca.c;
        c();
    }

    public void c() {
        a = com.badlogic.gdx.Cf.b.a();
        b = com.badlogic.gdx.Cf.b.b();
        this.f = new com.badlogic.gdx.graphics.Cl(com.badlogic.gdx.Cf.b.a(), com.badlogic.gdx.Cf.b.b());
        com.badlogic.gdx.graphics.Cl cl = (com.badlogic.gdx.graphics.Cl) this.f;
        cl.c.a(0.0f, 1.0f, 0.0f);
        cl.b.a(0.0f, 0.0f, -1.0f);
        cl.a.a((cl.n * 2880.0f) / 2.0f, (cl.n * 1920.0f) / 2.0f, 0.0f);
        cl.j = 2880.0f;
        cl.k = 1920.0f;
        cl.a();
        this.f.a.a(1440.0f, 10.0f, 960.0f);
        com.badlogic.gdx.graphics.Ca ca = this.f;
        ca.m.a(1440.0f, 0.0f, 960.0f).c(ca.a).c();
        com.badlogic.gdx.math.Cl cl2 = ca.m;
        if (!(cl2.a == 0.0f && cl2.b == 0.0f && cl2.c == 0.0f)) {
            float f = ca.m.f(ca.c);
            if (java.lang.Math.abs(f - 1.0f) < 1.0E-9f) {
                ca.c.a(ca.b).b(-1.0f);
            } else if (java.lang.Math.abs(f + 1.0f) < 1.0E-9f) {
                ca.c.a(ca.b);
            }
            ca.b.a(ca.m);
            ca.m.a(ca.b).g(ca.c).c();
            ca.c.a(ca.m).g(ca.b).c();
        }
        ((com.badlogic.gdx.graphics.Cl) this.f).n = 1.0f;
        ((com.badlogic.gdx.graphics.Cl) this.f).h = 1.0f;
        ((com.badlogic.gdx.graphics.Cl) this.f).i = 100.0f;
        this.f.a();
        this.h = new com.badlogic.gdx.Cj();
        this.h.a(new com.invertbit.games.e.a.Ca(this));
    }

    public void a(java.lang.Object... objArr) {
        a(-1);
    }

    @Override // com.badlogic.gdx.Co
    public final void a(float f) {
        com.badlogic.gdx.Cf.g.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        com.badlogic.gdx.Cf.g.glClear(16640);
        b(f);
    }

    public void d() {
        com.invertbit.games.e.Cb cb = this.e.b;
        java.lang.Object[] objArr = new java.lang.Object[0];
        if (com.invertbit.games.e.Ca.exit.equals(cb.b.b)) {
            com.badlogic.gdx.Cf.a.d();
        } else {
            cb.a(cb.b.b, objArr);
        }
    }

    @Override // com.badlogic.gdx.Co
    public void a() {
        this.h = new com.badlogic.gdx.Cj();
        this.h.a(new com.invertbit.games.e.a.Ca(this));
        com.badlogic.gdx.Cf.d.a(this.h);
        com.badlogic.gdx.Cf.d.a(true);
    }

    public final void a(int i) {
        this.e.c.a(i);
    }
}
