package com.invertbit.games.gtw.a;

/* loaded from: classes.dex */
public final class Cf extends com.invertbit.games.gtw.a.Ci {
    @Override // com.invertbit.games.gtw.a.Ci
    protected final int b() {
        // Human launch budget per DEFCON round (before +2 per allied country).
        // Original: EASY 8, HARD 5. NORMAL (new): 6.
        switch (com.invertbit.games.gtw.Cb.b().e()) {
            case EASY:
                return 8;
            case NORMAL:
                return 6;
            default:
                return 5;
        }
    }
}
