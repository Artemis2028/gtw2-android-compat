package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
final class Cat extends com.badlogic.gdx.f.a.Cg {
    final /* synthetic */ com.badlogic.gdx.f.a.a.Cc a;
    final /* synthetic */ com.invertbit.games.gtw.c.Cas b;

    Cat(com.invertbit.games.gtw.c.Cas cas, com.badlogic.gdx.f.a.a.Cc cc) {
        this.b = cas;
        this.a = cc;
    }

    @Override // com.badlogic.gdx.f.a.Cg
    public final boolean a() {
        if (com.invertbit.games.gtw.c.Cas.e()) {
            this.a.a("Sound on");
            return true;
        }
        this.a.a("Sound off");
        return true;
    }
}
