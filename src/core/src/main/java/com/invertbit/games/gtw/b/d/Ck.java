package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public final class Ck implements com.invertbit.games.gtw.b.c.Ca {
    public com.badlogic.gdx.f.a.Ci a = new com.badlogic.gdx.f.a.Ci();
    public com.invertbit.games.gtw.a.Ce b;

    @Override // com.invertbit.games.gtw.b.c.Ca
    public final boolean a(long j) {
        return true;
    }

    @Override // com.invertbit.games.gtw.b.c.Ca
    public final void a(long j, com.invertbit.games.gtw.b.d.Cl cl) {
        clock(this.b, j);
        this.a.a(j);
    }

    /** DEFCON countdown and HUD message timer, advanced by {@code j} ms of battle time. */
    public static void clock(com.invertbit.games.gtw.a.Ce ce, long j) {
        if (ce.a()) {
            ce.d -= j / 1000.0f;
        }
        ce.e += j / 1000.0f;
        if (ce.e > 5.0f) {
            ce.e = 0.0f;
            if (ce.c.size() > 0) {
                ce.c.remove(0);
            }
        }
    }

    @Override // com.invertbit.games.gtw.b.c.Ca
    public final void a(com.badlogic.gdx.graphics.a.Cf cf, com.invertbit.games.gtw.d.Ca ca) {
        this.a.a();
    }

    @Override // com.invertbit.games.gtw.b.c.Ca
    public final void a(com.badlogic.gdx.graphics.g2d.Ch ch, com.badlogic.gdx.graphics.Ca ca) {
    }

    public final void a(java.lang.String str) {
        this.b.a(str);
    }

    public final void a(com.invertbit.games.gtw.a.Ci.Ia ia) {
        this.b.b.get(ia).f++;
    }

    public final void a(com.invertbit.games.gtw.a.Cd cd) {
        com.invertbit.games.gtw.b.Cb cb = cd.b;
        this.b.a(cb.c).a(cb.f);
        com.invertbit.games.gtw.b.Cb cb2 = cd.b;
        com.invertbit.games.gtw.b.Ca ca = cd.a;
        com.invertbit.games.gtw.a.Ci ciA = this.b.a(ca.c);
        this.b.a(cb2.c);
        if (!(ca instanceof com.invertbit.games.gtw.b.Cd)) {
            if (!(ca instanceof com.invertbit.games.gtw.b.Ce) && (ca instanceof com.invertbit.games.gtw.b.Ci)) {
                com.invertbit.games.gtw.b.Cb cb3 = (com.invertbit.games.gtw.b.Cb) ca;
                ciA.h -= cb3.f * cb3.e;
                int i = cb3.f;
                int i2 = cb3.e * i;
                if (cb3 instanceof com.invertbit.games.gtw.b.Cj) {
                    if (i == 5) {
                        ciA.j.i.a(i2);
                    } else if (i == 10) {
                        ciA.j.i.b(i2);
                    } else if (i == 15) {
                        ciA.j.i.c(i2);
                    }
                } else if (cb3 instanceof com.invertbit.games.gtw.b.Ci) {
                    if (i == 5) {
                        ciA.j.g.a(i2);
                    } else if (i == 10) {
                        ciA.j.g.b(i2);
                    } else if (i == 15) {
                        ciA.j.g.c(i2);
                    }
                } else if (cb3 instanceof com.invertbit.games.gtw.b.Cc) {
                    if (i == 5) {
                        ciA.j.h.a(i2);
                    } else if (i == 10) {
                        ciA.j.h.b(i2);
                    } else if (i == 15) {
                        ciA.j.h.c(i2);
                    }
                }
                java.util.Iterator<com.invertbit.games.gtw.b.Ch> it = ((com.invertbit.games.gtw.b.Ci) ca).h.iterator();
                while (it.hasNext()) {
                    it.next();
                    ciA.a(((com.invertbit.games.gtw.b.Ci) ca).f);
                }
                if (ca.d) {
                    this.b.a(com.invertbit.games.gtw.b.e.Cj.SILO_DESTROYED.f);
                    return;
                }
                throw new java.lang.IllegalArgumentException(ca.toString());
            }
            return;
        }
        int i3 = ((com.invertbit.games.gtw.b.Cd) ca).f;
        ciA.g -= i3;
        if (i3 == 5) {
            ciA.j.f.a(i3);
        } else if (i3 == 10) {
            ciA.j.f.b(i3);
        } else if (i3 == 15) {
            ciA.j.f.c(i3);
        }
        if (ca.d) {
            this.b.a(com.invertbit.games.gtw.b.e.Cj.CITY_DESTROYED.f);
        }
    }
}
