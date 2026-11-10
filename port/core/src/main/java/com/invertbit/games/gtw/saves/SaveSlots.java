package com.invertbit.games.gtw.saves;

import com.invertbit.games.gtw.nations.Side;
import com.invertbit.games.gtw.settings.Prefs;
import com.invertbit.games.gtw.sim.Difficulty;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.Player;
import com.invertbit.games.gtw.sim.Region;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static Prefs prefs(int slot) {
        if (slot == FIRST) {
            return Prefs.v1();
        }
        return Prefs.of(slot == AUTOSAVE ? "v1_auto_" : "v1_s" + slot + "_");
    }

    public static boolean exists(int slot) {
        return prefs(slot).contains("player.0");
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
        Prefs p = prefs(slot);
        if (!p.contains("player.0")) {
            return name(slot) + ": empty";
        }
        StringBuilder sb = new StringBuilder(name(slot)).append(": ").append(p.getString("player.0").replace('_', ' '));
        if (p.contains("player.1")) {
            sb.append(" vs ").append(p.getString("player.1").replace('_', ' '));
        }
        sb.append("  DEFCON ").append(p.getInt("defcon"));
        sb.append("  ").append(level(slot).name());
        if (p.contains("save.time")) {
            try {
                long t = Long.parseLong(p.getString("save.time"));
                sb.append("  ").append(new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).format(new Date(t)));
            } catch (NumberFormatException e) {
                // ignore
            }
        } else {
            sb.append("  (older save)");
        }
        return sb.toString();
    }

    /** Difficulty stored in the slot; slots from the original game fall back to the global setting. */
    public static Difficulty level(int slot) {
        Prefs p = prefs(slot);
        return p.contains("save.level")
                ? Difficulty.parse(p.getString("save.level"), Difficulty.global())
                : Difficulty.global();
    }

    /** Writes the game in exactly the original format (plus metadata) into one slot. */
    public static void save(Match match, int slot) {
        Prefs p = prefs(slot);
        int i = 0;
        for (Player player : match.sides) {
            player.arsenal.write(i + "." + player.side.name(), p);
            p.putString("player." + i, player.side.name());
            StringBuilder sb = new StringBuilder();
            for (Region region : player.regions()) {
                sb.append(region.name).append(",");
            }
            p.putString("player." + i + ".country", sb.toString());
            i++;
        }
        p.putInt("defcon", match.defcon);
        p.putString("save.time", String.valueOf(System.currentTimeMillis()));
        p.putString("save.level", Difficulty.current().name());
        p.flush();
    }

    /** Start and end of every DEFCON round: the game's slot and the autosave. */
    public static void saveCurrent(Match match) {
        if (current != AUTOSAVE) {
            save(match, current);
        }
        save(match, AUTOSAVE);
    }

    /**
     * Loads a slot (same steps as the original "Continue game") and makes it the current game.
     * Countries are added first, then the saved arsenal overwrites the rows accumulated by
     * {@link Match#create} / {@link Player#add}, so yields are not double-counted.
     */
    public static Match load(int slot) {
        Prefs p = prefs(slot);
        Difficulty.setInProgress(level(slot));
        current = slot;
        Side human = Side.valueOf(p.getString("player.0"));
        Side ai = Side.valueOf(p.getString("player.1"));
        Match match = Match.create(human, ai, p.getInt("defcon"));
        addCountries(match.player(), p.getString("player.0.country"));
        addCountries(match.opponent(), p.getString("player.1.country"));
        match.player().arsenal.read("0." + human.name(), p);
        match.opponent().arsenal.read("1." + ai.name(), p);
        return match;
    }

    private static void addCountries(Player player, String csv) {
        if (csv == null || csv.isEmpty()) {
            return;
        }
        for (String raw : csv.split(",")) {
            String name = raw.trim();
            if (name.isEmpty()) {
                continue;
            }
            boolean owned = false;
            for (Region region : player.regions()) {
                if (region.name.equals(name)) {
                    owned = true;
                    break;
                }
            }
            if (!owned) {
                player.add(new Region(name));
            }
        }
    }
}
