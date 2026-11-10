package com.invertbit.games.gtw.sim;

import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.RegionId;
import com.invertbit.games.gtw.nations.Side;

import java.util.ArrayList;
import java.util.List;

/** One side in a match (original {@code a.Ci}, human {@code a.Cf} / AI {@code a.Ca}). */
public final class Player {
    public final Side side;
    public final boolean human;
    public final List<MapUnit> units = new ArrayList<MapUnit>();
    public final List<AttackPlan> aiTargets = new ArrayList<AttackPlan>();
    public final List<AttackPlan> inFlightPlans = new ArrayList<AttackPlan>();
    public int population;
    public int remainingWeapons;
    public int launchesUsed;
    public int launchBudget;
    public Arsenal arsenal = new Arsenal();
    /** What this side lost in the match (original {@code Ci.j}); read by the post-match summary. */
    public Arsenal losses = new Arsenal();
    private boolean lost;

    public Player(Side side, boolean human) {
        this.side = side;
        this.human = human;
        this.launchBudget = baseBudget();
    }

    private int baseBudget() {
        if (!human) {
            return 4;
        }
        switch (Difficulty.current()) {
            case EASY:
                return 8;
            case NORMAL:
                return 6;
            default:
                return 5;
        }
    }

    public void add(MapUnit unit) {
        units.add(unit);
        if (unit instanceof City) {
            population += ((City) unit).population;
            return;
        }
        if (unit instanceof Launcher) {
            Launcher launcher = (Launcher) unit;
            if (launcher.kind == WeaponKind.BOMBER) {
                remainingWeapons += launcher.yield;
            } else {
                remainingWeapons += launcher.yield * launcher.ammo;
            }
            return;
        }
        if (unit instanceof Region) {
            Region region = (Region) unit;
            if (!Nations.isHome(side, region.name)) {
                launchBudget += 2;
            }
            region.owner = side;
            arsenal.add(region);
        }
    }

    /** Debug-only quick defeat (see {@link com.invertbit.games.gtw.QuickDefeat}). */
    public void markDefeated() {
        lost = true;
    }

    /**
     * Start of the next DEFCON round (original {@code a.Ci.a()}): clear the lost flag,
     * in-flight count and losses; rebuild the launch budget (+2 per non-home region)
     * and, for an auto-deployed arsenal, a fresh sheet from the owned regions; drop
     * everything except regions.
     */
    public void resetForNextRound() {
        lost = false;
        launchesUsed = 0;
        launchBudget = baseBudget();
        losses = new Arsenal();
        aiTargets.clear();
        inFlightPlans.clear();
        arsenal = new Arsenal();
        population = 0;
        remainingWeapons = 0;
        java.util.Iterator<MapUnit> it = units.iterator();
        while (it.hasNext()) {
            if (!(it.next() instanceof Region)) {
                it.remove();
            }
        }
        for (Region region : regions()) {
            arsenal.add(region);
            region.owner = side;
            if (!Nations.isHome(side, region.name)) {
                launchBudget += 2;
            }
        }
    }

    public boolean budgetExhausted() {
        return launchesUsed >= launchBudget;
    }

    public List<Region> regions() {
        List<Region> list = new ArrayList<Region>();
        for (MapUnit unit : units) {
            if (unit instanceof Region) {
                list.add((Region) unit);
            }
        }
        return list;
    }

    public int regionCount() {
        return regions().size();
    }

    /**
     * Owned extras of a two-home side (alaska, chile) count with the primary
     * home as one region for the {@code owned - 1 < DEFCON} alliance rule.
     */
    public int allianceOwnedCount() {
        int extras = 0;
        for (RegionId extra : Nations.extraHomes(side)) {
            for (Region region : regions()) {
                if (extra.name().equals(region.name)) {
                    extras++;
                    break;
                }
            }
        }
        return regionCount() - extras;
    }

    public boolean owns(Region region) {
        return units.contains(region);
    }

    /**
     * A side is defeated the first time population or remaining weapons hit zero
     * (original {@code a.Ci.g()}).
     */
    public boolean defeated() {
        if (lost) {
            return true;
        }
        if (population != 0 && remainingWeapons != 0) {
            return false;
        }
        lost = true;
        return true;
    }

    public int cityCount() {
        int n = 0;
        for (MapUnit unit : units) {
            if (unit instanceof City) {
                n++;
            }
        }
        return n;
    }

    public int weaponCount() {
        int n = 0;
        for (MapUnit unit : units) {
            if (unit instanceof Launcher) {
                n++;
            }
        }
        return n;
    }
}
