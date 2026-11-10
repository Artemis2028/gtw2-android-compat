package com.invertbit.games.gtw.saves;

import com.invertbit.games.gtw.Cb;
import com.invertbit.games.gtw.Cc;
import com.invertbit.games.gtw.a.Ci;
import com.invertbit.games.gtw.a.Cg;

/**
 * Save slots on top of the original single save.
 *
 * The original game keeps one save in the libGDX preferences file "GTWGame" under keys
 * "v1_player.N", "v1_player.N.country", "v1_N.NATION.*" (arsenal budget) and "v1_defcon",
 * written at the end of every DEFCON round. Here:
 *   slot 1   = the original keys unchanged (so an existing save simply shows up as slot 1,
 *              and the original build can still read it),
 *   slot 2-4 = same keys under prefix "v1_s2_" ... "v1_s4_",
 *   autosave = same keys under prefix "v1_auto_".
 * New per-slot metadata: "save.time" (epoch ms) and "save.level" (difficulty). A slot written
 * by the original game has neither; it shows as "older save" and uses the global difficulty.
 */
public final class SaveSlots {
    public static final int AUTOSAVE = 0;
    public static final int FIRST = 1;
    public static final int LAST = 4;

    /** Slot the game in progress saves into (besides the autosave). */
    static int current = FIRST;

    private SaveSlots() {
    }

    public static int current() {
        return current;
    }

    public static void select(int slot) {
        current = slot;
    }

    public static Cc prefs(int slot) {
        if (slot == FIRST) {
            return Cc.a();
        }
        return new Cc(slot == AUTOSAVE ? "v1_auto_" : "v1_s" + slot + "_");
    }

    public static boolean exists(int slot) {
        return prefs(slot).a("player.0");
    }

    public static boolean anyExists() {
        for (int slot = AUTOSAVE; slot <= LAST; slot++) {
            if (exists(slot)) {
                return true;
            }
        }
        return false;
    }

    public static String name(int slot) {
        return slot == AUTOSAVE ? "Autosave" : "Slot " + slot;
    }

    /** One-line description, e.g. "Slot 2: USA vs CHINA  DEFCON 3  HARD  2026-10-17 14:05". */
    public static String describe(int slot) {
        Cc p = prefs(slot);
        if (!p.a("player.0")) {
            return name(slot) + ": empty";
        }
        StringBuilder sb = new StringBuilder(name(slot)).append(": ").append(p.c("player.0").replace('_', ' '));
        if (p.a("player.1")) {
            sb.append(" vs ").append(p.c("player.1").replace('_', ' '));
        }
        sb.append("  DEFCON ").append(p.b("defcon"));
        sb.append("  ").append(level(slot).name());
        if (p.a("save.time")) {
            try {
                long t = Long.parseLong(p.c("save.time"));
                sb.append("  ").append(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.US).format(new java.util.Date(t)));
            } catch (NumberFormatException e) {
                // ignore
            }
        } else {
            sb.append("  (older save)");
        }
        return sb.toString();
    }

    /** Difficulty stored in the slot; slots from the original game fall back to the global setting. */
    public static Cb.Ia level(int slot) {
        Cc p = prefs(slot);
        return p.a("save.level") ? Cb.Ia.parse(p.c("save.level"), Cb.b().d()) : Cb.b().d();
    }

    /** Writes the game in exactly the original format (plus metadata) into one slot. */
    public static void save(Cg cg, int slot) {
        Cc cc = prefs(slot);
        int i = 0;
        for (Ci ci : cg.b) {
            ci.i.a(i + "." + ci.e.name(), cc);
            cc.a("player." + i, ci.e.name());
            StringBuilder sb = new StringBuilder();
            for (com.invertbit.games.gtw.b.Ca ca : ci.d) {
                if (ca instanceof com.invertbit.games.gtw.b.Cf) {
                    sb.append(((com.invertbit.games.gtw.b.Cf) ca).e).append(",");
                }
            }
            cc.a("player." + i + ".country", sb.toString());
            i++;
        }
        cc.a("defcon", cg.c);
        cc.a("save.time", String.valueOf(System.currentTimeMillis()));
        cc.a("save.level", Cb.b().e().name());
        cc.b();
    }

    /** Start and end of every DEFCON round: the game's slot and the autosave. */
    public static void saveCurrent(Cg cg) {
        if (current != AUTOSAVE) {
            save(cg, current);
        }
        save(cg, AUTOSAVE);
    }

    /** Loads a slot (same steps as the original "Continue game") and makes it the current game. */
    public static Cg load(int slot) {
        Cc cc = prefs(slot);
        // Difficulty must be set before the players are built: the human launch budget is read in
        // the player constructor.
        Cb.b().b(level(slot));
        current = slot;
        Cg cg = new Cg();
        cg.c();
        com.invertbit.games.gtw.a.Cf human = new com.invertbit.games.gtw.a.Cf();
        human.a(cc, 0);
        cg.a(human);
        com.invertbit.games.gtw.a.Ca ai = new com.invertbit.games.gtw.a.Ca();
        ai.a(cc, 1);
        cg.a(ai);
        cg.c = cc.b("defcon");
        return cg;
    }
}
