package com.invertbit.games.gtw.c;

/* "Choose player" screen. Layout shared with the opponent screen, see nations.NationPicker. */
public final class Cc extends com.invertbit.games.gtw.c.Cb {
    java.util.Map<com.invertbit.games.gtw.a.Ci.Ia, java.util.List<com.badlogic.gdx.graphics.a.Cg>> r;

    public Cc(com.invertbit.games.gtw.Ca ca) {
        super(ca);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void c() {
        super.c();
        this.r = com.invertbit.games.gtw.nations.NationPicker.previews();
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void a(java.lang.Object... objArr) {
        super.a(objArr);
        this.o.d();
        this.o.a(com.invertbit.games.gtw.nations.NationPicker.table("Choose player:", null, new com.invertbit.games.gtw.nations.NationPicker.Callback() {
            @Override
            public void picked(com.invertbit.games.gtw.a.Ci.Ia ia) {
                com.invertbit.games.gtw.c.Cc.a(com.invertbit.games.gtw.c.Cc.this, ia);
            }
        }));
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        super.b(f);
        this.j.a(this.f);
        com.invertbit.games.gtw.nations.NationPicker.render(this.j, this.k, this.r, null);
        this.j.c();
    }

    static void a(com.invertbit.games.gtw.c.Cc cc, com.invertbit.games.gtw.a.Ci.Ia ia) {
        com.invertbit.games.gtw.Ca ca = cc.e;
        com.invertbit.games.e.Ca ca2 = com.invertbit.games.e.Ca.game1;
        com.invertbit.games.gtw.a.Cg cg = new com.invertbit.games.gtw.a.Cg();
        cg.c();
        cg.a(ia, com.invertbit.games.gtw.nations.Nations.home(ia), true);
        ca.a(ca2, cg);
    }
}
