package com.invertbit.games.gtw.c;

import com.invertbit.games.gtw.Cb;
import com.invertbit.games.gtw.saves.SaveSlots;

/**
 * Save-slot picker, laid out like the Settings screen (Cas) with the game's own fonts/buttons.
 * newGame=true:  pick the slot the new game will save into, plus a difficulty toggle; then the
 *                original "Choose player" screen (game0).
 * newGame=false: list existing saves (autosave first) and continue the chosen one in the
 *                original round-preparation screen (game2).
 */
public final class SlotScreen extends com.invertbit.games.gtw.c.Cb {
    final boolean newGame;
    Cb.Ia level;

    public SlotScreen(com.invertbit.games.gtw.Ca ca, boolean newGame) {
        super(ca);
        this.newGame = newGame;
    }

    @Override
    public final void a(java.lang.Object... objArr) {
        super.a(objArr);
        this.level = Cb.b().d();
        this.o.d();
        com.badlogic.gdx.f.a.a.Ce ce = new com.badlogic.gdx.f.a.a.Ce();
        ce.J = true;
        com.badlogic.gdx.f.a.a.Cc.Ia title = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.invertbit.games.gtw.b.e.Cg.s37.h);
        com.badlogic.gdx.f.a.a.Cc.Ia item = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s72), com.invertbit.games.gtw.b.e.Cg.s30.h);
        ce.c(new com.badlogic.gdx.f.a.a.Cc(this.newGame ? "New game: choose a save slot" : "Continue game", title)).n().h().i().d(50.0f);
        ce.i();
        if (this.newGame) {
            final com.badlogic.gdx.f.a.a.Cc diff = new com.badlogic.gdx.f.a.a.Cc("Difficulty: " + this.level.name(), item);
            diff.a(new com.badlogic.gdx.f.a.Cg() {
                @Override
                public boolean a() {
                    SlotScreen.this.level = SlotScreen.this.level.next();
                    diff.a("Difficulty: " + SlotScreen.this.level.name());
                    return true;
                }
            });
            ce.c(diff).m().h().i().d(30.0f);
            ce.i();
            com.badlogic.gdx.f.a.a.Cc.Ia note = new com.badlogic.gdx.f.a.a.Cc.Ia(com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s36), com.invertbit.games.gtw.b.e.Cg.s37.h);
            ce.c(new com.badlogic.gdx.f.a.a.Cc("A used slot is overwritten at the end of the first DEFCON.", note)).m().h().i().d(20.0f);
            ce.i();
        }
        for (int slot = this.newGame ? SaveSlots.FIRST : SaveSlots.AUTOSAVE; slot <= SaveSlots.LAST; slot++) {
            boolean exists = SaveSlots.exists(slot);
            if (!this.newGame && !exists) {
                continue;
            }
            String text = SaveSlots.describe(slot);
            com.badlogic.gdx.f.a.a.Cc row = new com.badlogic.gdx.f.a.a.Cc(text, item);
            final int chosen = slot;
            row.a(new com.badlogic.gdx.f.a.Cg() {
                @Override
                public boolean a() {
                    SlotScreen.this.pick(chosen);
                    return true;
                }
            });
            ce.c(row).m().h().i().d(30.0f);
            ce.i();
        }
        com.invertbit.games.gtw.c.a.Cc back = new com.invertbit.games.gtw.c.a.Cc(100, 100, "<", com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h);
        back.a(new com.badlogic.gdx.f.a.Cg() {
            @Override
            public boolean a() {
                SlotScreen.this.e.a(com.invertbit.games.e.Ca.menu, new java.lang.Object[0]);
                return true;
            }
        });
        ce.c(back).m().j().f(20.0f).d().b(150.0f).k();
        this.o.a(ce);
    }

    void pick(int slot) {
        if (this.newGame) {
            SaveSlots.select(slot);
            Cb.b().a(this.level);   // remember as the default (same setting as in Settings)
            Cb.b().b(this.level);   // level of this game; stored in the slot at every save
            this.e.a(com.invertbit.games.e.Ca.game0, new java.lang.Object[0]);
        } else {
            this.e.a(com.invertbit.games.e.Ca.game2, SaveSlots.load(slot));
        }
    }

    @Override
    public final void b(float f) {
        super.b(f);
    }
}
