package com.invertbit.games.gtw;

/* loaded from: classes.dex */
public final class Ca extends com.badlogic.gdx.Ce {
    public com.invertbit.games.e.Cb b;
    public com.invertbit.games.a.Ca c;

    public Ca(com.invertbit.games.a.Ca ca) {
        this.c = ca;
    }

    public final void a(java.lang.Enum<?> r2, java.lang.Object... objArr) {
        this.b.a(r2, objArr);
    }

    @Override // com.badlogic.gdx.Ce, com.badlogic.gdx.Cb
    public final void c() {
        java.util.Iterator<com.invertbit.games.e.Cc> it = this.b.a.iterator();
        while (it.hasNext()) {
            it.next().c.b();
        }
        com.invertbit.games.gtw.b.d.Cj.a().b();
        com.invertbit.games.gtw.b.d.Cp cpA = com.invertbit.games.gtw.b.d.Cp.a();
        cpA.b.entrySet();
        java.util.Iterator<java.util.Map.Entry<com.invertbit.games.gtw.b.d.Cp.Ia, com.badlogic.gdx.b.Cb>> it2 = cpA.b.entrySet().iterator();
        while (it2.hasNext()) {
            it2.next().getValue().b();
        }
        com.invertbit.games.gtw.b.d.Cp.a = null;
        com.invertbit.games.gtw.b.a.Ca.a();
        com.invertbit.games.gtw.b.a.Ca.a = null;
        com.invertbit.games.gtw.Cc.a();
        com.invertbit.games.gtw.Cc.a = null;
    }

    @Override // com.badlogic.gdx.Cb
    public final void a() {
        this.b = new com.invertbit.games.e.Cb(this);
        this.b.a(new com.invertbit.games.e.Cc(com.invertbit.games.e.Ca.loading, com.invertbit.games.e.Ca.exit, new com.invertbit.games.gtw.c.Cai(this)));
        this.b.a(com.invertbit.games.e.Ca.loading, new java.lang.Object[0]);
    }
}
