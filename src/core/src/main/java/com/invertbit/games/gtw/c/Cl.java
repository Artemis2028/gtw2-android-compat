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
            // Save the round setup (arsenal/alliances) into the game's slot and the autosave before the round starts.
            com.invertbit.games.gtw.saves.SaveSlots.saveCurrent(cgB);
            com.invertbit.games.gtw.b.e.RoundSetup.deploy(cgB, (com.invertbit.games.gtw.b.d.Cg) cgB.a.get(0));
            objArr[0] = cgB;
            ca.a(ca2, objArr);
        }
        return true;
    }
}
