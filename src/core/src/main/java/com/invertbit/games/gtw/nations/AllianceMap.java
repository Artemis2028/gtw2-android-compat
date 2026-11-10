package com.invertbit.games.gtw.nations;

import java.util.Collection;
import java.util.List;

/** Bounded lookups used by the Alliance screen (c/Cp). Every loop here terminates by construction. */
public final class AllianceMap {

    private AllianceMap() {
    }

    /** Index of the first owned-set containing {@code region}, or -1 (null/empty input included). */
    public static int ownerIndex(Object region, List<? extends Collection<?>> owned) {
        if (region == null || owned == null) {
            return -1;
        }
        for (int i = 0; i < owned.size(); i++) {
            Collection<?> c = owned.get(i);
            if (c != null && c.contains(region)) {
                return i;
            }
        }
        return -1;
    }

    /** The side owning {@code region} in this game, or null. */
    public static com.invertbit.games.gtw.a.Ci owner(com.invertbit.games.gtw.b.Cf region, List<com.invertbit.games.gtw.a.Ci> sides) {
        if (region == null || sides == null) {
            return null;
        }
        java.util.List<java.util.List<com.invertbit.games.gtw.b.Ca>> owned = new java.util.ArrayList<java.util.List<com.invertbit.games.gtw.b.Ca>>(sides.size());
        for (com.invertbit.games.gtw.a.Ci ci : sides) {
            owned.add(ci.d);
        }
        int i = ownerIndex(region, owned);
        return i < 0 ? null : sides.get(i);
    }
}
