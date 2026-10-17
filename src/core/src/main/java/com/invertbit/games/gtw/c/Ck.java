package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
public final class Ck extends com.invertbit.games.gtw.c.Cb {
    com.invertbit.games.gtw.a.Cg p;
    com.badlogic.gdx.f.a.a.Cc q;
    com.badlogic.gdx.f.a.a.Cc r;

    static com.invertbit.games.gtw.a.Cg b(com.invertbit.games.gtw.c.Ck ck) {
        return ck.p;
    }

    public Ck(com.invertbit.games.gtw.Ca ca) {
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
        if (objArr == null || !(objArr[0] instanceof com.invertbit.games.gtw.a.Cg)) {
            throw new java.lang.IllegalArgumentException("No new game bean");
        }
        this.p = (com.invertbit.games.gtw.a.Cg) objArr[0];
        this.o.d();
        com.badlogic.gdx.f.a.a.Ce ce = new com.badlogic.gdx.f.a.a.Ce();
        ce.J = true;
        com.badlogic.gdx.f.a.a.Cc.Ia ia = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.invertbit.games.gtw.b.e.Cg.s37.h);
        com.badlogic.gdx.f.a.a.Cc.Ia ia2 = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s30), com.invertbit.games.gtw.b.e.Cg.s30.h);
        com.badlogic.gdx.f.a.a.Cc.Ia ia3 = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s30), com.badlogic.gdx.graphics.Cb.y);
        this.q = new com.badlogic.gdx.f.a.a.Cc("", ia2);
        this.q.i();
        this.r = new com.badlogic.gdx.f.a.a.Cc("", ia3);
        this.r.i();
        java.lang.String strValueOf = null;
        java.util.Iterator<com.invertbit.games.gtw.a.Ci> it = this.p.b.iterator();
        while (true) {
            java.lang.String str = strValueOf;
            if (!it.hasNext()) {
                ce.c(new com.badlogic.gdx.f.a.a.Cc(str, ia)).m().a(2);
                ce.i();
                ((com.invertbit.games.gtw.c.a.Cc) ce.c(new com.invertbit.games.gtw.c.a.Cc(500, 200, "Start\nDEFCON " + this.p.c, com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).m().a(2).w).a(new com.invertbit.games.gtw.c.Cl(this));
                ce.i();
                ((com.invertbit.games.gtw.c.a.Cc) ce.c(new com.invertbit.games.gtw.c.a.Cc(400, 200, "Arsenal", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).m().l().g(10.0f).w).a(new com.invertbit.games.gtw.c.Cm(this));
                ((com.invertbit.games.gtw.c.a.Cc) ce.c(new com.invertbit.games.gtw.c.a.Cc(400, 200, "Alliances", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h)).m().j().f(10.0f).w).a(new com.invertbit.games.gtw.c.Cn(this));
                ce.i();
                ce.c(this.q).o().f().a(2);
                ce.i();
                ce.c(this.r).o().f().a(2);
                ce.i();
                com.invertbit.games.gtw.c.a.Cc cc = new com.invertbit.games.gtw.c.a.Cc(100, 100, "<", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h);
                cc.a(new com.invertbit.games.gtw.c.Co(this));
                ce.c(cc).m().j().f(20.0f).d().b(150.0f).c(150.0f).k().a(2);
                this.o.a(ce);
                a(1);
                return;
            }
            com.invertbit.games.gtw.a.Ci next = it.next();
            strValueOf = str == null ? java.lang.String.valueOf(next.e) : str + " vs " + next.e;
        }
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        super.b(f);
        com.invertbit.games.gtw.a.Cb cb = this.p.a().i;
        this.r.a("");
        this.q.a("");
        if (cb.a()) {
            this.q.a("Nuclear arsenal is not defined. It'll be deployed automatically.");
        } else if (cb.b()) {
            this.q.a("Nuclear arsenal is defined but your population is 0. Add at least one city");
        } else if (cb.c()) {
            this.q.a("At least one city is added but there're no silos. Add at least on silo/bomber/submarine");
        } else {
            if (cb.e > 0 && cb.e < cb.d) {
                this.q.a("There're points avaiable for nuclear arsenal or cities.");
            }
        }
        if (this.p.e()) {
            this.q.a(((java.lang.Object) this.q.q) + "\nYou can choose new alliance.");
        }
        if (!cb.d()) {
            this.r.a("You cannot start a new DEFCON.");
        } else {
            this.r.a("");
        }
    }

    static boolean a(com.invertbit.games.gtw.c.Ck ck) {
        return ck.p.a().i.d();
    }
}
