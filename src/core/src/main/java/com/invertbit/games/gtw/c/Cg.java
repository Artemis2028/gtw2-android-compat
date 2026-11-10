package com.invertbit.games.gtw.c;

/* "Choose your opponent" screen. Layout shared with the player screen, see nations.NationPicker. */
public final class Cg extends com.invertbit.games.gtw.c.Cb {
    com.invertbit.games.gtw.a.Cg r;
    com.invertbit.games.gtw.a.Ci.Ia s;
    java.util.Map<com.invertbit.games.gtw.a.Ci.Ia, java.util.List<com.badlogic.gdx.graphics.a.Cg>> t;

    public Cg(com.invertbit.games.gtw.Ca ca) {
        super(ca);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void c() {
        super.c();
        this.t = com.invertbit.games.gtw.nations.NationPicker.previews();
    }

    @Override // com.invertbit.games.gtw.c.Ca
    public final void a(java.lang.Object... objArr) {
        super.a(objArr);
        if (objArr != null && (objArr[0] instanceof com.invertbit.games.gtw.a.Cg)) {
            this.r = (com.invertbit.games.gtw.a.Cg) objArr[0];
            this.o.d();
            this.s = this.r.b.get(0).e;
            this.o.a(com.invertbit.games.gtw.nations.NationPicker.table("Choose your opponent:", this.s, new com.invertbit.games.gtw.nations.NationPicker.Callback() {
                @Override
                public void picked(com.invertbit.games.gtw.a.Ci.Ia ia) {
                    com.invertbit.games.gtw.c.Cg.a(com.invertbit.games.gtw.c.Cg.this, ia);
                }
            }));
            return;
        }
        throw new java.lang.IllegalArgumentException("No new game bean");
    }

    @Override // com.invertbit.games.gtw.c.Cb, com.invertbit.games.gtw.c.Ca
    public final void b(float f) {
        super.b(f);
        this.j.a(this.f);
        com.invertbit.games.gtw.nations.NationPicker.render(this.j, this.k, this.t, this.s);
        this.j.c();
    }

    static void a(com.invertbit.games.gtw.c.Cg cg, com.invertbit.games.gtw.a.Ci.Ia ia) {
        cg.r.a(ia, com.invertbit.games.gtw.a.Cg.a(ia), false);
        cg.e.a(com.invertbit.games.e.Ca.game2, cg.r);
    }
}
