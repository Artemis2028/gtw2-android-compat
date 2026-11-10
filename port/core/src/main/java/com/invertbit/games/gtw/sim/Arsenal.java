package com.invertbit.games.gtw.sim;

import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.settings.Prefs;

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

    /** Original {@code a.Cb.a(String, Cc)}: rows under {@code <key>.AB.<KIND>.m5/m10/m15} plus counters. */
    public void write(String key, Prefs prefs) {
        String ab = key + ".AB";
        YieldRow[] rows = {city, silo, bomber, submarine};
        for (YieldRow row : rows) {
            prefs.putInt(ab + "." + row.kind.name() + ".m5", row.m5);
            prefs.putInt(ab + "." + row.kind.name() + ".m10", row.m10);
            prefs.putInt(ab + "." + row.kind.name() + ".m15", row.m15);
        }
        prefs.putInt(key + ".populationCount", populationCount);
        prefs.putInt(key + ".siloCount", siloCount);
        prefs.putInt(key + ".submarineCount", submarineCount);
        prefs.putInt(key + ".maxPoints", maxPoints);
        prefs.putInt(key + ".points", points);
    }

    /**
     * Original {@code a.Cb.b(String, Cc)} reads the four rows and {@code points}. The original
     * adds the saved rows onto rows already accumulated from the countries; here the rows are
     * replaced instead, so a reloaded save does not double its yields.
     */
    public void read(String key, Prefs prefs) {
        String ab = key + ".AB";
        YieldRow[] rows = {city, silo, bomber, submarine};
        for (YieldRow row : rows) {
            row.m5 = prefs.getInt(ab + "." + row.kind.name() + ".m5");
            row.m10 = prefs.getInt(ab + "." + row.kind.name() + ".m10");
            row.m15 = prefs.getInt(ab + "." + row.kind.name() + ".m15");
        }
        points = prefs.getInt(key + ".points");
    }

    /** Loss bookkeeping (original {@code b.d.Ck}): add {@code mt} into the 5/10/15 bucket for {@code yield}. */
    public static void addLoss(YieldRow row, int yield, int mt) {
        if (yield == 5) {
            row.add5(mt);
        } else if (yield == 10) {
            row.add10(mt);
        } else if (yield == 15) {
            row.add15(mt);
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
