package com.invertbit.games.gtw.nations;

import java.util.Collection;
import java.util.List;

/** Bounded lookups used by the Alliance screen. Every loop here terminates by construction. */
public final class AllianceMap {

    private AllianceMap() {}

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
}
