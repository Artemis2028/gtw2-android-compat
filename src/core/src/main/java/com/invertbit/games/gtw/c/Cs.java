package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
public final class Cs extends com.invertbit.games.gtw.c.Cb {
    com.invertbit.games.gtw.a.Cg p;

    static com.invertbit.games.gtw.a.Cg a(com.invertbit.games.gtw.c.Cs cs) {
        return cs.p;
    }

    public Cs(com.invertbit.games.gtw.Ca ca) {
        super(ca);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void c() {
        super.c();
    }

    private com.invertbit.games.gtw.c.a.Ca a(com.invertbit.games.gtw.b.a.Ca.Ia ia, com.invertbit.games.gtw.a.Cj cj) {
        com.invertbit.games.gtw.c.a.Ca ca = new com.invertbit.games.gtw.c.a.Ca(ia);
        ca.a(new com.invertbit.games.gtw.c.Cu(this, ca, cj));
        return ca;
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void a(java.lang.Object... objArr) {
        com.invertbit.games.gtw.b.a.Ca.Ia ia;
        super.a(objArr);
        if (objArr != null && (objArr[0] instanceof com.invertbit.games.gtw.a.Cg)) {
            this.p = (com.invertbit.games.gtw.a.Cg) objArr[0];
            this.o.d();
            com.badlogic.gdx.f.a.a.Ce ce = new com.badlogic.gdx.f.a.a.Ce();
            ce.J = true;
            ce.c(new com.badlogic.gdx.f.a.a.Cc(" ", new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.invertbit.games.gtw.b.e.Cg.s37.h))).m().h().i().a(2);
            ce.i();
            ce.c(a(com.invertbit.games.gtw.b.a.Ca.Ia.city, this.p.a().i.f)).m().l().a(500.0f).b(360.0f).d(5.0f);
            ce.c(a(com.invertbit.games.gtw.b.a.Ca.Ia.silo, this.p.a().i.g)).m().j().a(500.0f).b(360.0f).d(5.0f);
            ce.i();
            switch (this.p.a().e) {
                case USA:
                    ia = com.invertbit.games.gtw.b.a.Ca.Ia.usabomber;
                    break;
                case RUSSIA:
                    ia = com.invertbit.games.gtw.b.a.Ca.Ia.russiabomber;
                    break;
                case CHINA:
                    ia = com.invertbit.games.gtw.b.a.Ca.Ia.chinabomber;
                    break;
                default:
                    ia = com.invertbit.games.gtw.b.a.Ca.Ia.usabomber;
                    break;
            }
            ce.c(a(ia, this.p.a().i.h)).m().l().a(500.0f).b(360.0f).d(5.0f);
            ce.c(a(com.invertbit.games.gtw.b.a.Ca.Ia.submarine, this.p.a().i.i)).m().j().a(500.0f).b(360.0f).d(5.0f);
            ce.i();
            com.invertbit.games.gtw.c.a.Cc cc = new com.invertbit.games.gtw.c.a.Cc(100, 100, "<", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h);
            cc.a(new com.invertbit.games.gtw.c.Ct(this));
            ce.c(cc).m().j().f(20.0f).d().b(150.0f);
            this.o.a(ce);
            return;
        }
        throw new java.lang.IllegalArgumentException("No new game bean");
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        super.b(f);
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void d() {
        this.e.a(com.invertbit.games.e.Ca.game2, this.p);
    }
}
