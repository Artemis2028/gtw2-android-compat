package com.invertbit.games.d;

/* loaded from: classes.dex */
public final class Ca implements com.badlogic.gdx.graphics.a.Cj {
    int a;
    int b;
    int c;
    int d;
    int e;
    int f;
    int g;
    public com.badlogic.gdx.math.Ck h;
    com.badlogic.gdx.graphics.glutils.Cn i;
    com.badlogic.gdx.graphics.Ca j;
    com.badlogic.gdx.graphics.Cb k;
    java.lang.String l;

    public Ca() {
        this.l = "border";
    }

    public Ca(com.badlogic.gdx.graphics.Cb cb) {
        this.k = cb;
        this.l = "border";
    }

    public Ca(com.badlogic.gdx.graphics.Cb cb, java.lang.String str) {
        this.k = cb;
        this.l = str;
    }

    @Override // com.badlogic.gdx.graphics.a.Cj
    public final void a() {
        this.i = new com.badlogic.gdx.graphics.glutils.Cn(com.badlogic.gdx.Cf.e.b(this.l + ".vertex.glsl").f(), com.badlogic.gdx.Cf.e.b(this.l + ".fragment.glsl").f());
        if (!this.i.b) {
            throw new com.badlogic.gdx.utils.Cg(this.i.a());
        }
        this.a = this.i.c("u_projTrans");
        this.b = this.i.c("u_worldTrans");
        this.c = this.i.c("u_color");
        this.e = this.i.c("u_texture1");
        this.f = this.i.c("u_texture2");
        this.g = this.i.c("u_texture3");
        this.d = this.i.c("u_resolution");
    }

    @Override // com.badlogic.gdx.utils.Cd
    public final void b() {
        this.i.b();
    }

    @Override // com.badlogic.gdx.graphics.a.Cj
    public final void a(com.badlogic.gdx.graphics.Ca ca, com.badlogic.gdx.graphics.a.e.Cj cj) {
        this.j = ca;
        this.i.c();
        this.i.a(this.a, ca.f);
    }

    @Override // com.badlogic.gdx.graphics.a.Cj
    public final void b(com.badlogic.gdx.graphics.a.Ch ch) {
        this.i.a(this.b, ch.a);
        this.i.a(this.c, this.k.H, this.k.I, this.k.J, 1.0f);
        this.i.a(this.e, 1);
        ch.b.a(this.i, ch.e, ch.c, ch.d);
    }

    @Override // com.badlogic.gdx.graphics.a.Cj
    public final void c() {
        com.badlogic.gdx.graphics.glutils.Cn.d();
    }

    @Override // com.badlogic.gdx.graphics.a.Cj
    public final boolean a(com.badlogic.gdx.graphics.a.Ch ch) {
        return true;
    }
}
