package com.invertbit.games.gtw.stats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MatchStatsTest {

    @Test
    public void readsLaunchesCasualtiesAndDestroyedCitiesFromTheSide() {
        com.invertbit.games.gtw.a.Ci side = new com.invertbit.games.gtw.a.Ci();
        side.e = com.invertbit.games.gtw.a.Ci.Ia.USA;
        side.f = 3;
        // leave population/silo MT non-zero so g() is not "defeated"
        side.g = 80;
        side.h = 200;
        side.j.f.a(15);
        side.j.g.a(30);
        side.j.i.a(10);
        side.j.h.a(20);

        com.invertbit.games.gtw.b.Cd wrecked = new com.invertbit.games.gtw.b.Cd(new com.badlogic.gdx.math.Cl(), 30);
        wrecked.d = true;
        com.invertbit.games.gtw.b.Cd standing = new com.invertbit.games.gtw.b.Cd(new com.badlogic.gdx.math.Cl(), 30);
        standing.d = false;
        side.d.add(wrecked);
        side.d.add(standing);

        MatchStats stats = MatchStats.of(side);
        assertEquals("USA", stats.name);
        assertFalse(stats.defeated);
        assertEquals(3, stats.launches);
        assertEquals(4, stats.launchBudget);
        assertEquals("3/4", stats.launchesLabel());
        assertEquals(15, stats.casualtiesMln);
        assertEquals("15 mln", stats.casualtiesLabel());
        assertEquals(30, stats.silosLostMt);
        assertEquals("30 MT", stats.silosLabel());
        assertEquals(10, stats.subsLostMt);
        assertEquals(20, stats.bombersLostMt);
        assertEquals(1, stats.citiesDestroyed);
        assertEquals("1", stats.citiesLabel());
    }

    @Test
    public void defeatedWhenTheSimMarksTheSideLost() {
        com.invertbit.games.gtw.a.Ci side = new com.invertbit.games.gtw.a.Ci();
        side.e = com.invertbit.games.gtw.a.Ci.Ia.RUSSIA;
        // g and h default to 0, so Ci.g() reports defeated
        MatchStats stats = MatchStats.of(side);
        assertTrue(stats.defeated);
        assertEquals("RUSSIA", stats.name);
        assertEquals(0, stats.launches);
        assertEquals(4, stats.launchBudget);
        assertEquals(0, stats.casualtiesMln);
        assertEquals(0, stats.citiesDestroyed);
    }

    @Test
    public void displayNamesForAddedSidesComeFromNations() {
        com.invertbit.games.gtw.a.Ci side = new com.invertbit.games.gtw.a.Ci();
        side.e = com.invertbit.games.gtw.a.Ci.Ia.SOUTH_AMERICA;
        side.g = 1;
        side.h = 1;
        assertEquals("SOUTH AMERICA", MatchStats.of(side).name);
    }
}
