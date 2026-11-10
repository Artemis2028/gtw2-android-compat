package com.invertbit.games.gtw.sim;

import com.invertbit.games.gtw.nations.Nations;

/**
 * A side's arsenal sheet (original {@code a.Cb}). Points spent on the manual
 * picker live in {@link #points}; {@link #empty()} is {@code points <= 0},
 * which is how Start decides to auto-deploy the player's arsenal.
 */
public final class Arsenal {
    public int populationCount;
    public int siloCount;
    public int submarineCount;
    public int maxPoints;
    public int points;
    public final YieldRow city = new YieldRow(WeaponKind.CITY);
    public final YieldRow silo = new YieldRow(WeaponKind.SILO);
    public final YieldRow bomber = new YieldRow(WeaponKind.BOMBER);
    public final YieldRow submarine = new YieldRow(WeaponKind.SUBMARINE);
    public boolean autoDeployed;

    public boolean empty() {
        return points <= 0;
    }

    public Arsenal copy() {
        Arsenal out = new Arsenal();
        out.city.m5 = city.m5;
        out.city.m10 = city.m10;
        out.city.m15 = city.m15;
        out.silo.m5 = silo.m5;
        out.silo.m10 = silo.m10;
        out.silo.m15 = silo.m15;
        out.bomber.m5 = bomber.m5;
        out.bomber.m10 = bomber.m10;
        out.bomber.m15 = bomber.m15;
        out.submarine.m5 = submarine.m5;
        out.submarine.m10 = submarine.m10;
        out.submarine.m15 = submarine.m15;
        out.populationCount = populationCount;
        out.siloCount = siloCount;
        out.submarineCount = submarineCount;
        out.maxPoints = maxPoints;
        out.points = points;
        return out;
    }

    /**
     * Manual spend of {@code mt} (5 or 10) on {@code row}. Mirrors {@code a.Cb.a(int, Cj)}
     * including the original's always-false silo-and-bomber conjunction.
     */
    public void spend(int mt, YieldRow row) {
        if (points + mt <= maxPoints) {
            if (!(WeaponKind.SUBMARINE.equals(row.kind) && row.count() + 1 > submarineCount)) {
                if (!(WeaponKind.SILO.equals(row.kind) && WeaponKind.BOMBER.equals(row.kind) && row.count() + 1 > siloCount)) {
                    points += mt;
                    if (mt == 5) {
                        row.add5(mt);
                    } else if (mt == 10) {
                        row.add10(mt);
                    } else if (mt == 15) {
                        row.add15(mt);
                    }
                }
            }
        }
    }

    public void add(Region region) {
        RegionStats stats = Nations.stats(region);
        populationCount += stats.cities;
        city.add5(populationCount * 5);
        siloCount += stats.silos;
        submarineCount += stats.submarines;
        maxPoints = (stats.submarines * 5) + (stats.silos * 5) + maxPoints;
    }
}
