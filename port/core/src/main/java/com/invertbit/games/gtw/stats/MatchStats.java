package com.invertbit.games.gtw.stats;

import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.sim.City;
import com.invertbit.games.gtw.sim.MapUnit;
import com.invertbit.games.gtw.sim.Player;

/**
 * End-of-match / end-of-round figures taken from a side at the moment the summary
 * is shown. Only values the sim already stores; nothing is estimated.
 *
 * There is no intercept counter in the sim, so this object does not invent one.
 */
public final class MatchStats {

    public final String name;
    public final boolean defeated;
    public final int launches;
    public final int launchBudget;
    public final int casualtiesMln;
    public final int silosLostMt;
    public final int subsLostMt;
    public final int bombersLostMt;
    public final int citiesDestroyed;

    public MatchStats(String name, boolean defeated, int launches, int launchBudget,
                      int casualtiesMln, int silosLostMt, int subsLostMt, int bombersLostMt,
                      int citiesDestroyed) {
        this.name = name;
        this.defeated = defeated;
        this.launches = launches;
        this.launchBudget = launchBudget;
        this.casualtiesMln = casualtiesMln;
        this.silosLostMt = silosLostMt;
        this.subsLostMt = subsLostMt;
        this.bombersLostMt = bombersLostMt;
        this.citiesDestroyed = citiesDestroyed;
    }

    public static MatchStats of(Player side) {
        int cities = 0;
        if (side.units != null) {
            for (MapUnit unit : side.units) {
                if ((unit instanceof City) && unit.destroyed) {
                    cities++;
                }
            }
        }
        int pop = side.losses.city.totalMt();
        int silo = side.losses.silo.totalMt();
        int sub = side.losses.submarine.totalMt();
        int bomber = side.losses.bomber.totalMt();
        return new MatchStats(
                Nations.displayName(side.side),
                side.defeated(),
                side.launchesUsed,
                side.launchBudget,
                pop, silo, sub, bomber,
                cities);
    }

    public String launchesLabel() {
        return launches + "/" + launchBudget;
    }

    public String casualtiesLabel() {
        return casualtiesMln + " mln";
    }

    public String silosLabel() {
        return silosLostMt + " MT";
    }

    public String subsLabel() {
        return subsLostMt + " MT";
    }

    public String bombersLabel() {
        return bombersLostMt + " MT";
    }

    public String citiesLabel() {
        return Integer.toString(citiesDestroyed);
    }
}
