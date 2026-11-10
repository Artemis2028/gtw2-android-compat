package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
public final class Cw extends com.invertbit.games.gtw.c.Cb {
    com.invertbit.games.gtw.a.Cg p;
    com.badlogic.gdx.graphics.g2d.Cg q;
    com.invertbit.games.gtw.a.Cj r;
    com.invertbit.games.gtw.a.Cb s;
    com.badlogic.gdx.f.a.a.Cc t;
    com.badlogic.gdx.f.a.a.Cc u;
    com.badlogic.gdx.f.a.a.Cc v;
    com.badlogic.gdx.f.a.a.Cc w;
    com.badlogic.gdx.f.a.a.Cc x;

    static com.invertbit.games.gtw.a.Cj a(com.invertbit.games.gtw.c.Cw cw) {
        return cw.r;
    }

    static com.invertbit.games.gtw.a.Cb b(com.invertbit.games.gtw.c.Cw cw) {
        return cw.s;
    }

    static com.badlogic.gdx.f.a.a.Cc c(com.invertbit.games.gtw.c.Cw cw) {
        return cw.t;
    }

    static void d(com.invertbit.games.gtw.c.Cw cw) {
        cw.e();
    }

    static com.badlogic.gdx.f.a.a.Cc e(com.invertbit.games.gtw.c.Cw cw) {
        return cw.u;
    }

    static com.badlogic.gdx.f.a.a.Cc f(com.invertbit.games.gtw.c.Cw cw) {
        return cw.v;
    }

    /**
     * Icon for an arsenal row. jadx dropped the {@code break} after the BOMBER case's inner switch
     * (the smali jumps to the end), so BOMBER fell through to SUBMARINE and every side's bomber
     * screen showed a submarine. The bomber icon now matches the side's in-battle bomber model.
     */
    public static com.invertbit.games.gtw.b.a.Ca.Ia icon(com.invertbit.games.gtw.a.Cj.Ia row, com.invertbit.games.gtw.a.Ci.Ia side) {
        switch (row) {
            case SILO:
                return com.invertbit.games.gtw.b.a.Ca.Ia.silo;
            case BOMBER:
                return com.invertbit.games.gtw.nations.Nations.bomber(side);
            case SUBMARINE:
                return com.invertbit.games.gtw.b.a.Ca.Ia.submarine;
            case CITY:
            default:
                return com.invertbit.games.gtw.b.a.Ca.Ia.city;
        }
    }

    public Cw(com.invertbit.games.gtw.Ca ca) {
        super(ca);
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void a(java.lang.Object... objArr) {
        com.badlogic.gdx.graphics.g2d.Cg cgB;
        super.a(objArr);
        if (objArr != null && (objArr[0] instanceof com.invertbit.games.gtw.a.Cg) && (objArr[1] instanceof com.invertbit.games.gtw.a.Cj) && (objArr[2] instanceof com.invertbit.games.gtw.a.Cb)) {
            this.p = (com.invertbit.games.gtw.a.Cg) objArr[0];
            this.r = (com.invertbit.games.gtw.a.Cj) objArr[1];
            this.s = (com.invertbit.games.gtw.a.Cb) objArr[2];
            this.o.d();
            cgB = this.m.b(icon(this.r.d, this.p.a().e));
            this.q = cgB;
            com.badlogic.gdx.f.a.a.Ce ce = new com.badlogic.gdx.f.a.a.Ce();
            ce.J = true;
            com.badlogic.gdx.f.a.a.Cc.Ia ia = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.invertbit.games.gtw.b.e.Cg.s37.h);
            com.badlogic.gdx.f.a.a.Cc.Ia ia2 = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s37), com.invertbit.games.gtw.b.e.Cg.s30.h);
            ce.c(new com.badlogic.gdx.f.a.a.Cc(com.invertbit.games.gtw.b.e.Ch.a(this.r.d), ia)).n().h().b(100.0f);
            ce.i();
            this.w = new com.badlogic.gdx.f.a.a.Cc("Points: ", ia2);
            ce.c(this.w).n().h().b(100.0f).a(5);
            ce.i();
            this.x = new com.badlogic.gdx.f.a.a.Cc("Slots: ", ia2);
            ce.c(this.x).n().h().b(100.0f).a(5);
            ce.i();
            com.badlogic.gdx.f.a.a.Ce ce2 = new com.badlogic.gdx.f.a.a.Ce();
            this.t = new com.badlogic.gdx.f.a.a.Cc(com.invertbit.games.gtw.b.e.Ch.b(this.r.a, this.r.d), ia);
            ce2.c(new com.badlogic.gdx.f.a.a.Cc(com.invertbit.games.gtw.b.e.Ch.a(5, this.r.d), ia)).l().b(200.0f);
            ce2.c(new com.badlogic.gdx.f.a.a.Cb(this.q)).j();
            ((com.invertbit.games.gtw.c.a.Cc) ce2.c(f()).a(150.0f).h().f(50.0f).w).a(new com.invertbit.games.gtw.c.Cy(this));
            ((com.invertbit.games.gtw.c.a.Cc) ce2.c(g()).a(150.0f).h().f(50.0f).w).a(new com.invertbit.games.gtw.c.Cz(this));
            ce2.c(this.t).a(400.0f).h().f(50.0f);
            ce2.i();
            if (this.r.d.equals(com.invertbit.games.gtw.a.Cj.Ia.SILO)) {
                this.u = new com.badlogic.gdx.f.a.a.Cc(com.invertbit.games.gtw.b.e.Ch.b(this.r.b, this.r.d), ia);
                ce2.c(new com.badlogic.gdx.f.a.a.Cc(com.invertbit.games.gtw.b.e.Ch.a(10, this.r.d), ia)).l().b(200.0f);
                ce2.c(new com.badlogic.gdx.f.a.a.Cb(this.q)).j();
                ((com.invertbit.games.gtw.c.a.Cc) ce2.c(f()).a(150.0f).h().f(50.0f).w).a(new com.invertbit.games.gtw.c.Caa(this));
                ((com.invertbit.games.gtw.c.a.Cc) ce2.c(g()).a(150.0f).h().f(50.0f).w).a(new com.invertbit.games.gtw.c.Cab(this));
                ce2.c(this.u).a(400.0f).h().f(50.0f);
                ce2.i();
                this.v = new com.badlogic.gdx.f.a.a.Cc(com.invertbit.games.gtw.b.e.Ch.b(this.r.c, this.r.d), ia);
                ce2.c(new com.badlogic.gdx.f.a.a.Cc(com.invertbit.games.gtw.b.e.Ch.a(15, this.r.d), ia)).l().b(200.0f);
                ce2.c(new com.badlogic.gdx.f.a.a.Cb(this.q)).j();
                ((com.invertbit.games.gtw.c.a.Cc) ce2.c(f()).a(150.0f).h().f(50.0f).w).a(new com.invertbit.games.gtw.c.Cac(this));
                ((com.invertbit.games.gtw.c.a.Cc) ce2.c(g()).a(150.0f).h().f(50.0f).w).a(new com.invertbit.games.gtw.c.Cad(this));
                ce2.c(this.v).a(400.0f).h().f(50.0f);
                ce2.i();
            }
            com.invertbit.games.gtw.c.a.Cc cc = new com.invertbit.games.gtw.c.a.Cc(100, 100, "<", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h);
            cc.a(new com.invertbit.games.gtw.c.Cx(this));
            ce.c(ce2).h();
            ce.i();
            ce.c(cc).m().j().f(20.0f).d().b(150.0f).k();
            this.o.a(ce);
            e();
            return;
        }
        throw new java.lang.IllegalArgumentException("No new game bean");
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        super.b(f);
    }

    private void e() {
        this.w.a("Points: " + this.s.e + "/" + this.s.d);
        switch (this.r.d) {
            case CITY:
                this.x.a("Slots: " + this.s.a);
                break;
            case SILO:
            case BOMBER:
                com.badlogic.gdx.f.a.a.Cc cc = this.x;
                java.lang.StringBuilder sb = new java.lang.StringBuilder("Slots: ");
                com.invertbit.games.gtw.a.Cb cb = this.s;
                cc.a(sb.append(cb.h.c() + cb.g.c()).append("/").append(this.s.b).toString());
                break;
            case SUBMARINE:
                this.x.a("Slots: " + this.r.c() + "/" + this.s.c);
                break;
        }
    }

    private static com.invertbit.games.gtw.c.a.Cc f() {
        return new com.invertbit.games.gtw.c.a.Cc(150, 150, "+", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h);
    }

    private static com.invertbit.games.gtw.c.a.Cc g() {
        return new com.invertbit.games.gtw.c.a.Cc(150, 150, "-", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h);
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void d() {
        this.e.a(com.invertbit.games.e.Ca.game4, this.p);
    }
}
