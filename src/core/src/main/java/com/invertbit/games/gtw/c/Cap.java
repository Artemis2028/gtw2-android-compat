package com.invertbit.games.gtw.c;

/* loaded from: classes.dex */
final class Cap extends com.badlogic.gdx.f.a.Cg {
    final /* synthetic */ com.invertbit.games.gtw.c.Can a;

    Cap(com.invertbit.games.gtw.c.Can can) {
        this.a = can;
    }

    @Override // com.badlogic.gdx.f.a.Cg
    public final boolean a() {
        // Original: load the single save, or start a new game if there is none.
        if (com.invertbit.games.gtw.saves.SaveSlots.anyExists()) {
            this.a.e.a(com.invertbit.games.e.Ca.slotsContinue, new java.lang.Object[0]);
        } else {
            this.a.e.a(com.invertbit.games.e.Ca.slotsNew, new java.lang.Object[0]);
        }
        return true;
    }
}
