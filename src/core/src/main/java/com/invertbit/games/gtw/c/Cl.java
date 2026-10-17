package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
final class Cl extends com.badlogic.gdx.f.a.Cg {
    final /* synthetic */ com.invertbit.games.gtw.c.Ck a;

    Cl(com.invertbit.games.gtw.c.Ck ck) {
        this.a = ck;
    }

    @Override // com.badlogic.gdx.f.a.Cg
    public final boolean a() {
        if (com.invertbit.games.gtw.c.Ck.a(this.a)) {
            com.invertbit.games.gtw.Ca ca = this.a.e;
            com.invertbit.games.e.Ca ca2 = com.invertbit.games.e.Ca.start;
            java.lang.Object[] objArr = new java.lang.Object[1];
            com.invertbit.games.gtw.a.Cg cgB = com.invertbit.games.gtw.c.Ck.b(this.a);
            cgB.d = new java.util.ArrayList();
            if (com.invertbit.games.gtw.b.e.Ca.a == null) {
                com.invertbit.games.gtw.b.e.Ca.a = new com.invertbit.games.gtw.b.e.Ca();
            }
            com.invertbit.games.gtw.b.e.Ca ca3 = com.invertbit.games.gtw.b.e.Ca.a;
            ca3.a(cgB.c, cgB.b());
            ca3.a(cgB.b());
            if (cgB.a().i.a()) {
                ca3.a(cgB.a());
                cgB.d.add("Your arsenal has been automatically deployed.");
            }
            java.util.List<com.badlogic.gdx.math.Cl> list = com.invertbit.games.gtw.b.a.Ca.a().b().get("submarine").i;
            java.util.Collections.shuffle(list);
            java.util.Iterator<com.badlogic.gdx.math.Cl> it = list.iterator();
            for (com.invertbit.games.gtw.a.Ci ci : cgB.b) {
                boolean zEquals = cgB.a().equals(ci);
                com.invertbit.games.gtw.b.e.Ce.a(ci, (com.invertbit.games.gtw.b.d.Cg) cgB.a.get(0), zEquals);
                com.invertbit.games.gtw.a.Cj cj = ci.i.i;
                com.invertbit.games.gtw.b.e.Ce.a(5, cj.a / 5, ci, it, zEquals);
                com.invertbit.games.gtw.b.e.Ce.a(10, cj.b / 10, ci, it, zEquals);
                com.invertbit.games.gtw.b.e.Ce.a(15, cj.c / 15, ci, it, zEquals);
            }
            cgB.d();
            objArr[0] = cgB;
            ca.a(ca2, objArr);
        }
        return true;
    }
}
