package com.invertbit.games.gtw.c.c;

/* loaded from: classes.dex */
public final class Cb implements com.badlogic.gdx.d.Ca.Ia {
    com.invertbit.games.gtw.c.c.Ca a;

    public Cb(com.invertbit.games.gtw.c.c.Ca ca) {
        this.a = ca;
    }

    @Override // com.badlogic.gdx.d.Ca.Ia
    public final boolean a(float f, float f2) {
        this.a.a((2880.0f / com.badlogic.gdx.Cf.b.a()) * f, (1920.0f / com.badlogic.gdx.Cf.b.b()) * f2);
        return false;
    }

    @Override // com.badlogic.gdx.d.Ca.Ia
    public final boolean b(float f, float f2) {
        this.a.b(f, -f2);
        return true;
    }

    @Override // com.badlogic.gdx.d.Ca.Ia
    public final boolean c(float f, float f2) {
        return this.a.c(f, f2);
    }
}
