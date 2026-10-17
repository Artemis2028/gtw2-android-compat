package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
public final class Caf extends com.invertbit.games.gtw.c.Cb {
    com.invertbit.games.gtw.a.Cg p;

    public Caf(com.invertbit.games.gtw.Ca ca) {
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
        if (objArr != null && (objArr[0] instanceof com.invertbit.games.gtw.a.Cg)) {
            this.p = (com.invertbit.games.gtw.a.Cg) objArr[0];
            this.o.d();
            com.badlogic.gdx.f.a.a.Ce ce = new com.badlogic.gdx.f.a.a.Ce();
            ce.J = true;
            com.badlogic.gdx.f.a.a.Cc.Ia ia = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.invertbit.games.gtw.b.e.Cg.s37.h);
            com.badlogic.gdx.f.a.a.Cc.Ia ia2 = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.badlogic.gdx.graphics.Cb.y);
            com.badlogic.gdx.f.a.Cb cb = ce.c(new com.invertbit.games.gtw.c.a.Cc(500, 120, "DEFCON " + this.p.c, com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).m().a(4).w;
            ce.i();
            ce.c(new com.badlogic.gdx.f.a.a.Cc("Losses:", ia)).a(4).m().h().g();
            ce.i();
            for (com.invertbit.games.gtw.a.Ci ci : this.p.b) {
                ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia)).a(300.0f).h().g();
                ce.c(new com.badlogic.gdx.f.a.a.Cc(ci.e.name(), ia)).m().j().f(30.0f);
                com.badlogic.gdx.f.a.a.Cc cc = new com.badlogic.gdx.f.a.a.Cc("", ia2);
                if (ci.g()) {
                    cc.a("Defated");
                }
                ce.c(cc).m().l().g(15.0f);
                ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia)).a(300.0f).h().g();
                ce.i();
                com.invertbit.games.gtw.a.Cb cb2 = ci.j;
                com.badlogic.gdx.f.a.a.Cc.Ia ia3 = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s36), com.invertbit.games.gtw.b.e.Cg.s37.h);
                ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia3)).a(300.0f).h().g();
                ce.c(new com.badlogic.gdx.f.a.a.Cc("Population", ia3)).m().j().f(60.0f);
                ce.c(new com.badlogic.gdx.f.a.a.Cc(java.lang.String.valueOf(cb2.f.b() + " mln"), ia3)).m().l().g(15.0f);
                ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia3)).a(300.0f).h().g();
                ce.i();
                ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia3)).a(300.0f).h().g();
                ce.c(new com.badlogic.gdx.f.a.a.Cc("Silos", ia3)).m().j().f(60.0f);
                ce.c(new com.badlogic.gdx.f.a.a.Cc(java.lang.String.valueOf(cb2.g.b() + " MT"), ia3)).m().l().g(15.0f);
                ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia3)).a(300.0f).h().g();
                ce.i();
                ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia3)).a(300.0f).h().g();
                ce.c(new com.badlogic.gdx.f.a.a.Cc("Submarines", ia3)).m().j().f(60.0f);
                ce.c(new com.badlogic.gdx.f.a.a.Cc(java.lang.String.valueOf(cb2.i.b() + " MT"), ia3)).m().l().g(15.0f);
                ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia3)).a(300.0f).h().g();
                ce.i();
                ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia3)).a(300.0f).h().g();
                ce.c(new com.badlogic.gdx.f.a.a.Cc("Bombers", ia3)).m().j().f(60.0f);
                ce.c(new com.badlogic.gdx.f.a.a.Cc(java.lang.String.valueOf(cb2.h.b() + " MT"), ia3)).m().l().g(15.0f);
                ce.c(new com.badlogic.gdx.f.a.a.Cc("", ia3)).a(300.0f).h().g();
                ce.i();
            }
            if (this.p.a().g()) {
                com.invertbit.games.gtw.c.a.Cc cc2 = new com.invertbit.games.gtw.c.a.Cc(100, 100, "<", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h);
                cc2.a(new com.invertbit.games.gtw.c.Cag(this));
                ce.c(cc2).m().j().f(20.0f).d().b(150.0f).a(4);
            } else {
                com.invertbit.games.gtw.c.a.Cc cc3 = new com.invertbit.games.gtw.c.a.Cc(100, 100, ">", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h);
                cc3.a(new com.invertbit.games.gtw.c.Cah(this));
                ce.c(cc3).m().l().g(20.0f).d().b(150.0f).a(4);
            }
            this.o.a(ce);
            return;
        }
        throw new java.lang.IllegalArgumentException("No new game bean");
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        super.b(f);
    }

    static void a(com.invertbit.games.gtw.c.Caf caf) {
        if (!caf.p.a().g()) {
            caf.p.c++;
        }
        com.invertbit.games.gtw.a.Cg cg = caf.p;
        if (cg.a != null) {
            java.util.Iterator<com.invertbit.games.gtw.b.d.Cb> it = cg.a.iterator();
            while (it.hasNext()) {
                it.next().e();
            }
        }
        cg.c();
        java.util.Iterator<com.invertbit.games.gtw.a.Ci> it2 = cg.b.iterator();
        while (it2.hasNext()) {
            it2.next().a();
        }
        // Original save point (end of round): now the selected slot + the autosave slot.
        com.invertbit.games.gtw.saves.SaveSlots.saveCurrent(caf.p);
        caf.e.a(com.invertbit.games.e.Ca.game2, caf.p);
    }
}
