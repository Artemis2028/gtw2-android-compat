package com.invertbit.games.gtw.sim;

import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.RegionId;
import com.invertbit.games.gtw.nations.Side;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Per-side alliance pools (original {@code b.e.Cc}). Key 0 is every non-superpower
 * region (minus a new side's own homes); key 9 unlocks the other superpowers.
 */
public final class AllianceTables {
    private static AllianceTables instance;
    private final Map<Side, Map<Integer, List<RegionId>>> tables =
            new HashMap<Side, Map<Integer, List<RegionId>>>();

    private AllianceTables() {
        build();
    }

    public static AllianceTables get() {
        if (instance == null) {
            instance = new AllianceTables();
        }
        return instance;
    }

    private static boolean superpowerHome(RegionId id) {
        return RegionId.china.equals(id) || RegionId.russia.equals(id) || RegionId.usa.equals(id);
    }

    private void build() {
        putOriginal(Side.USA, RegionId.china, RegionId.russia);
        putOriginal(Side.RUSSIA, RegionId.china, RegionId.usa);
        putOriginal(Side.CHINA, RegionId.usa, RegionId.russia);
        for (Side added : new Side[]{Side.EUROPE, Side.AFRICA, Side.SOUTH_AMERICA}) {
            Map<Integer, List<RegionId>> map = new HashMap<Integer, List<RegionId>>();
            List<RegionId> early = new ArrayList<RegionId>();
            for (RegionId id : RegionId.values()) {
                if (!superpowerHome(id) && !Nations.isHome(added, id.name())) {
                    early.add(id);
                }
            }
            map.put(0, early);
            List<RegionId> late = new ArrayList<RegionId>();
            late.add(RegionId.usa);
            late.add(RegionId.russia);
            late.add(RegionId.china);
            map.put(9, late);
            tables.put(added, map);
        }
    }

    private void putOriginal(Side side, RegionId a, RegionId b) {
        Map<Integer, List<RegionId>> map = new HashMap<Integer, List<RegionId>>();
        List<RegionId> early = new ArrayList<RegionId>();
        for (RegionId id : RegionId.values()) {
            if (!superpowerHome(id)) {
                early.add(id);
            }
        }
        map.put(0, early);
        List<RegionId> late = new ArrayList<RegionId>();
        late.add(a);
        late.add(b);
        map.put(9, late);
        tables.put(side, map);
    }

    public List<RegionId> offer(Side side, int defcon) {
        List<RegionId> out = new ArrayList<RegionId>();
        for (Map.Entry<Integer, List<RegionId>> entry : tables.get(side).entrySet()) {
            if (defcon >= entry.getKey().intValue()) {
                out.addAll(entry.getValue());
            }
        }
        return out;
    }
}
