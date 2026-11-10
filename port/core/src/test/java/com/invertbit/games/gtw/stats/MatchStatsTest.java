package com.invertbit.games.gtw.stats;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.nations.Side;
import com.invertbit.games.gtw.sim.City;
import com.invertbit.games.gtw.sim.Difficulty;
import com.invertbit.games.gtw.sim.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MatchStatsTest {

    @Before
    public void resetDifficulty() {
        Difficulty.reset();
    }

    @After
    public void restoreDifficulty() {
        Difficulty.reset();
    }

    @Test
    public void readsLaunchesCasualtiesAndDestroyedCitiesFromTheSide() {
        Player side = new Player(Side.USA, false);
        side.launchesUsed = 3;
        side.population = 80;
        side.remainingWeapons = 200;
        side.losses.city.add5(15);
        side.losses.silo.add5(30);
        side.losses.submarine.add5(10);
        side.losses.bomber.add5(20);

        City wrecked = new City(new Vector3(), 30);
        wrecked.destroyed = true;
        City standing = new City(new Vector3(), 30);
        standing.destroyed = false;
        side.units.add(wrecked);
        side.units.add(standing);

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
        Player side = new Player(Side.RUSSIA, false);
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
        Player side = new Player(Side.SOUTH_AMERICA, false);
        side.population = 1;
        side.remainingWeapons = 1;
        assertEquals("SOUTH AMERICA", MatchStats.of(side).name);
    }
}
