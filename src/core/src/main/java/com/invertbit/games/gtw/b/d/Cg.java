package com.invertbit.games.gtw.b.d;

/* loaded from: classes.dex */
public final class Cg extends com.invertbit.games.gtw.b.d.Cb {
    com.invertbit.games.gtw.a.Cf b;

    @Override // com.invertbit.games.gtw.b.d.Cb
    protected final void c() {
        super.c();
        java.util.List<com.badlogic.gdx.graphics.a.Ce> listD = com.invertbit.games.gtw.b.a.Ca.a().d();
        java.util.Map<java.lang.String, com.invertbit.games.gtw.b.Cf> mapB = com.invertbit.games.gtw.b.a.Ca.a().b();
        for (com.badlogic.gdx.graphics.a.Ce ce : listD) {
            com.invertbit.games.gtw.b.Cf cf = mapB.get(((com.badlogic.gdx.graphics.a.c.Cb) ce.e.a(java.lang.Object.class)[0]).a);
            com.badlogic.gdx.graphics.a.Cg cg = new com.badlogic.gdx.graphics.a.Cg(ce);
            cg.g = cf;
            this.a.add(cg);
        }
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void d() {
        java.util.Iterator<com.badlogic.gdx.graphics.a.Cg> it = this.a.iterator();
        while (it.hasNext()) {
            ((com.invertbit.games.gtw.b.Cf) it.next().g).c = null;
        }
        if (this.a == null) {
        }
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final java.lang.Class a() {
        return com.invertbit.games.gtw.b.Cf.class;
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void a(com.invertbit.games.gtw.b.Ca ca) {
        com.invertbit.games.gtw.b.Cf cf = (com.invertbit.games.gtw.b.Cf) ca;
        for (com.badlogic.gdx.graphics.a.Cg cg : this.a) {
            if (cg.g.equals(cf)) {
                ((com.invertbit.games.gtw.b.Cf) cg.g).c = cf.c;
            }
        }
    }

    @Override // com.invertbit.games.gtw.b.d.Cb, com.invertbit.games.gtw.b.c.Ca
    public final void a(com.badlogic.gdx.graphics.a.Cf cf, com.invertbit.games.gtw.d.Ca ca) {
        if (this.a != null) {
            for (com.badlogic.gdx.graphics.a.Cg cg : this.a) {
                com.invertbit.games.gtw.b.Cf cf2 = (com.invertbit.games.gtw.b.Cf) cg.g;
                cf.a(cg, ca.a(com.invertbit.games.gtw.nations.Nations.border(cf2.c)));
            }
        }
    }

    public final java.util.List<com.badlogic.gdx.math.Cl> a(com.invertbit.games.gtw.b.Cf.Ib ib) {
        java.lang.String strValueOf = java.lang.String.valueOf(ib);
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator<com.badlogic.gdx.graphics.a.Cg> it = this.a.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            com.badlogic.gdx.graphics.a.Cg next = it.next();
            if (strValueOf.equals(((com.invertbit.games.gtw.b.Cf) next.g).e)) {
                arrayList.addAll(((com.invertbit.games.gtw.b.Cf) next.g).i);
                break;
            }
        }
        if (!arrayList.isEmpty()) {
            return arrayList;
        }
        throw new java.lang.IllegalArgumentException(strValueOf);
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final boolean a(com.badlogic.gdx.graphics.Ca ca, com.badlogic.gdx.graphics.a.Cg cg) {
        return false;
    }

    @Override // com.invertbit.games.gtw.b.d.Cb
    public final void b() {
        java.util.Collections.sort(this.a, new com.invertbit.games.gtw.b.d.Ch(this));
    }
}
