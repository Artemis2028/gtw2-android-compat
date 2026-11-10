package com.invertbit.games.gtw.nations;

import com.invertbit.games.gtw.a.Ci;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Shared layout for the "Choose player" and "Choose your opponent" screens: the six home outlines
 * in two rows of three, with two matching rows of buttons underneath (order {@link Nations#PICK_ORDER}).
 * Replaces the original screens' three hardcoded previews and buttons.
 */
public final class NationPicker {

    public interface Callback {
        void picked(Ci.Ia ia);
    }

    private NationPicker() {
    }

    /** Home-region outline models of every side, translated to its preview slot. */
    public static Map<Ci.Ia, List<com.badlogic.gdx.graphics.a.Cg>> previews() {
        Map<Ci.Ia, List<com.badlogic.gdx.graphics.a.Cg>> map = new EnumMap<Ci.Ia, List<com.badlogic.gdx.graphics.a.Cg>>(Ci.Ia.class);
        for (Ci.Ia ia : Ci.Ia.values()) {
            map.put(ia, new ArrayList<com.badlogic.gdx.graphics.a.Cg>());
        }
        List<com.badlogic.gdx.graphics.a.Ce> models = com.invertbit.games.gtw.b.a.Ca.a().d();
        Map<String, com.invertbit.games.gtw.b.Cf> regions = com.invertbit.games.gtw.b.a.Ca.a().b();
        for (com.badlogic.gdx.graphics.a.Ce ce : models) {
            com.invertbit.games.gtw.b.Cf cf = regions.get(((com.badlogic.gdx.graphics.a.c.Cb) ce.e.a(Object.class)[0]).a);
            if (cf == null) {
                continue;
            }
            for (Ci.Ia ia : Ci.Ia.values()) {
                if (Nations.isHome(ia, cf.e)) {
                    com.badlogic.gdx.graphics.a.Cg cg = new com.badlogic.gdx.graphics.a.Cg(ce);
                    cg.g = cf;
                    float[] off = Nations.previewOffset(ia);
                    cg.f.a(off[0], 0.0f, off[1]);
                    map.get(ia).add(cg);
                }
            }
        }
        return map;
    }

    /** Draws the outlines; {@code greyed} (may be null) is drawn in the neutral border colour. */
    public static void render(com.badlogic.gdx.graphics.a.Cf batch, com.invertbit.games.gtw.d.Ca materials,
                              Map<Ci.Ia, List<com.badlogic.gdx.graphics.a.Cg>> previews, Ci.Ia greyed) {
        for (Map.Entry<Ci.Ia, List<com.badlogic.gdx.graphics.a.Cg>> e : previews.entrySet()) {
            com.badlogic.gdx.graphics.a.Cj material = e.getKey() == greyed
                    ? materials.a(com.invertbit.games.gtw.d.Ca.Ia.defaultBorder)
                    : com.invertbit.games.gtw.c.b.Ca.a(e.getKey(), materials);
            for (com.badlogic.gdx.graphics.a.Cg cg : e.getValue()) {
                batch.a(cg, material);
            }
        }
    }

    /** Title plus two rows of three side buttons; {@code hidden} (may be null) leaves an empty slot. */
    public static com.badlogic.gdx.f.a.a.Ce table(String title, Ci.Ia hidden, final Callback callback) {
        com.badlogic.gdx.f.a.a.Ce ce = new com.badlogic.gdx.f.a.a.Ce();
        ce.J = true;
        com.badlogic.gdx.f.a.a.Cc.Ia style = new com.badlogic.gdx.f.a.a.Cc.Ia(
                com.invertbit.games.gtw.b.d.Cj.a(com.invertbit.games.gtw.b.e.Cg.s73), com.invertbit.games.gtw.b.e.Cg.s37.h);
        // Cell API (obfuscated libGDX 1.7 Cell): m()=expand, a(int)=colspan, i()=top, e(f)=padTop,
        // a(f)=width, b(f)=height.
        ce.c(new com.badlogic.gdx.f.a.a.Cc(title, style)).m().a(3).i().e(10.0f);
        for (int row = 0; row < 2; row++) {
            ce.i();
            for (int col = 0; col < 3; col++) {
                final Ci.Ia ia = Nations.PICK_ORDER[(row * 3) + col];
                if (ia == hidden) {
                    ce.c(new com.badlogic.gdx.f.a.a.Cc("", style)).a(640.0f).b(150.0f);
                    continue;
                }
                com.invertbit.games.gtw.c.a.Cc button = new com.invertbit.games.gtw.c.a.Cc(440, 130, Nations.displayName(ia),
                        com.invertbit.games.gtw.b.e.Cg.s73, com.invertbit.games.gtw.b.e.Cg.s37.h);
                ce.c(button).a(640.0f).b(150.0f);
                button.a(new com.badlogic.gdx.f.a.Cg() {
                    @Override
                    public final boolean a() {
                        callback.picked(ia);
                        return false;
                    }
                });
            }
        }
        return ce;
    }
}
