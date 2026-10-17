package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
public abstract class Cb extends com.invertbit.games.gtw.c.Ca {
    protected com.badlogic.gdx.graphics.a.Cf j;
    protected com.invertbit.games.gtw.d.Ca k;
    public com.invertbit.games.gtw.b.d.Cj l;
    protected com.invertbit.games.gtw.b.a.Ca m;
    protected com.badlogic.gdx.graphics.a.Cg n;
    public com.badlogic.gdx.f.a.Ci o;

    public Cb(com.invertbit.games.gtw.Ca ca) {
        super(ca);
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public void c() {
        super.c();
        this.m = com.invertbit.games.gtw.b.a.Ca.a();
        this.j = new com.badlogic.gdx.graphics.a.Cf((byte) 0);
        this.n = new com.badlogic.gdx.graphics.a.Cg(com.invertbit.games.gtw.b.a.Ca.a().b.a(5760.0f, 0.01f, 3840.0f, com.invertbit.games.gtw.b.a.Ca.a().c));
        this.k = com.invertbit.games.gtw.d.Ca.a();
        this.l = com.invertbit.games.gtw.b.d.Cj.a();
        this.o = new com.badlogic.gdx.f.a.Ci();
        this.o.b = new com.badlogic.gdx.utils.c.Cb(c, d);
    }

    @Override // com.badlogic.gdx.Co
    public final void a(int i, int i2) {
        this.o.b.a(i, i2);
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public void b(float f) {
        this.j.a(this.f);
        this.j.a(this.n, this.k.a(com.invertbit.games.gtw.d.Ca.Ia.background));
        this.j.a();
        this.j.c();
        this.o.a(f);
        this.o.a();
    }

    @Override // com.badlogic.gdx.Co
    public void b() {
        this.o.b();
    }

    @Override // com.invertbit.games.gtw.c.Ca, com.badlogic.gdx.Co
    public void a() {
        super.a();
        this.h.a(this.o);
    }
}
