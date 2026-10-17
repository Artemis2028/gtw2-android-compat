package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public final class Cf extends com.invertbit.games.gtw.b.d.Cb {
    com.badlogic.gdx.graphics.a.Ce b;
    com.invertbit.games.gtw.b.d.Cm c;

    public Cf(com.invertbit.games.gtw.b.d.Cm cm) {
        this.c = cm;
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    protected final void c() {
        super.c();
        this.b = com.invertbit.games.gtw.b.a.Ca.a().b.a(32.0f, 0.1f, 32.0f, com.invertbit.games.gtw.b.a.Ca.a().c);
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final java.lang.Class a() {
        return com.invertbit.games.gtw.b.Ce.class;
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void a(com.invertbit.games.gtw.b.Ca ca) {
        com.badlogic.gdx.graphics.a.Cg cg = new com.badlogic.gdx.graphics.a.Cg(this.b);
        cg.g = ca;
        cg.f.b(ca.b);
        this.a.add(cg);
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void b(com.invertbit.games.gtw.a.Cd cd) {
        int i;
        com.invertbit.games.gtw.b.d.Cm cm = this.c;
        com.invertbit.games.gtw.b.Cb cb = cd.b;
        com.invertbit.games.gtw.b.Ca ca = cd.a;
        if (cb.f == 15) {
            i = 6;
        } else if (cb.f == 10) {
            i = 4;
        } else if (cb.f != 5) {
            i = 0;
        } else {
            i = 2;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(i);
        for (com.invertbit.games.gtw.b.Ch ch : cm.b) {
            if (ca == ch.e.b) {
                arrayList.add(ch);
                if (arrayList.size() == i) {
                    return;
                }
            }
        }
    }
}
