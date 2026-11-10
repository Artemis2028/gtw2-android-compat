package com.invertbit.games.gtw.stats;

/**
 * End-of-match / end-of-round figures taken from a side ({@link com.invertbit.games.gtw.a.Ci})
 * at the moment {@code Caf} is shown. Only values the sim already stores; nothing is estimated.
 *
 * <ul>
 *   <li>{@link #launches}/{@link #launchBudget} — {@code Ci.f}/{@code Ci.a}, the same pair
 *       the side's {@code toString} prints as {@code [used/budget]}</li>
 *   <li>{@link #casualtiesMln} — {@code Ci.j.f.b()}, the population-loss total already shown
 *       under Losses (hits add the warhead yield into {@code j.f})</li>
 *   <li>{@link #citiesDestroyed} — units in {@code Ci.d} that are cities ({@code b.Cd})
 *       with the sim's own destroyed flag ({@code Ca.d})</li>
 *   <li>Silo / submarine / bomber MT lost — {@code Ci.j.g/i/h.b()}, unchanged from the
 *       original summary</li>
 * </ul>
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

    public static MatchStats of(com.invertbit.games.gtw.a.Ci side) {
        int cities = 0;
        if (side.d != null) {
            for (com.invertbit.games.gtw.b.Ca unit : side.d) {
                if ((unit instanceof com.invertbit.games.gtw.b.Cd) && unit.d) {
                    cities++;
                }
            }
        }
        com.invertbit.games.gtw.a.Cb lost = side.j;
        int pop = 0;
        int silo = 0;
        int sub = 0;
        int bomber = 0;
        if (lost != null) {
            if (lost.f != null) {
                pop = lost.f.b();
            }
            if (lost.g != null) {
                silo = lost.g.b();
            }
            if (lost.i != null) {
                sub = lost.i.b();
            }
            if (lost.h != null) {
                bomber = lost.h.b();
            }
        }
        return new MatchStats(
                com.invertbit.games.gtw.nations.Nations.displayName(side.e),
                side.g(),
                side.f,
                side.launchBudget(),
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
