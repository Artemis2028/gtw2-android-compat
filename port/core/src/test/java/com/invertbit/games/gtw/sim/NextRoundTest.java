package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.nations.Side;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NextRoundTest {

    @Before
    public void world() throws Exception {
        WorldRegions.get().reset();
        AlliancePicker.reset();
        String[] candidates = {
                "../../src/android/src/main/assets/world.svg",
                "../src/android/src/main/assets/world.svg",
                "src/android/src/main/assets/world.svg",
                "../android/src/main/assets/world.svg"
        };
        File svg = null;
        for (String path : candidates) {
            File f = new File(path);
            if (f.isFile()) {
                svg = f;
                break;
            }
        }
        if (svg == null) {
            throw new AssertionError("world.svg not found");
        }
        InputStream in = new FileInputStream(svg);
        try {
            WorldRegions.get().install(WorldRegions.load(in));
        } finally {
            in.close();
        }
    }

    @Test
    public void winIncrementsDefconThenResetsSides() {
        Match match = Match.create(Side.SOUTH_AMERICA, Side.RUSSIA, 0);
        Player you = match.player();
        Player them = match.opponent();
        you.add(new City(new Vector3(10f, 0f, 10f), 40));
        them.add(new City(new Vector3(20f, 0f, 10f), 30));
        you.remainingWeapons = 25;
        them.remainingWeapons = 0;
        assertTrue(them.defeated());
        assertFalse(you.defeated());
        int homes = you.regionCount();
        match.nextRound();
        assertEquals(1, match.defcon);
        assertEquals(homes, you.regionCount());
        assertEquals(0, you.population);
        assertEquals(0, you.remainingWeapons);
        assertEquals(0, you.weaponCount());
        assertEquals(0, you.launchesUsed);
    }

    @Test
    public void lossDoesNotIncrementDefcon() {
        Match match = Match.create(Side.SOUTH_AMERICA, Side.RUSSIA, 4);
        match.player().markDefeated();
        match.nextRound();
        assertEquals(4, match.defcon);
    }

    @Test
    public void bomberFliesThenStrikesWithoutAnIcbm() {
        Match match = Match.create(Side.SOUTH_AMERICA, Side.RUSSIA, 0);
        Battle battle = new Battle(match);
        Launcher bomber = Launcher.bomber(new Vector3(0f, 0f, 0f), 5);
        bomber.owner = Side.SOUTH_AMERICA;
        City city = new City(new Vector3(60f, 0f, 0f), 20);
        city.owner = Side.RUSSIA;
        match.player().add(bomber);
        match.opponent().add(city);
        match.opponent().population = 20;
        match.player().remainingWeapons = 5;
        battle.launch(bomber, city);
        assertTrue(bomber.isMoving());
        assertEquals(0, battle.missiles.size());
        assertEquals(1, battle.sorties.size());
        assertEquals(0, match.player().launchesUsed);
        battle.advance((long) (bomber.moveDuration * 1000f) + 50);
        assertTrue(bomber.spent);
        assertEquals(0, battle.sorties.size());
        assertTrue(battle.impacts >= 1);
        assertEquals(15, city.population);
    }

    @Test
    public void subSurfacesWhenItFiresAndStaysHiddenUntilThen() {
        Match match = Match.create(Side.SOUTH_AMERICA, Side.RUSSIA, 0);
        Battle battle = new Battle(match);
        Launcher enemy = Launcher.submarine(new Vector3(80f, 0f, 0f), 5, false);
        enemy.owner = Side.RUSSIA;
        Launcher own = Launcher.submarine(new Vector3(10f, 0f, 0f), 5, true);
        own.owner = Side.SOUTH_AMERICA;
        City city = new City(new Vector3(200f, 0f, 10f), 20);
        city.owner = Side.RUSSIA;
        match.opponent().add(enemy);
        match.player().add(own);
        match.opponent().add(city);
        assertFalse(battle.visible(enemy));
        assertTrue(battle.visible(own));
        assertFalse(own.canFireAt(enemy));
        battle.launch(own, city);
        assertTrue(own.surfaced);
        assertEquals(1, battle.missiles.size());
        assertEquals(9, own.ammo);
    }
}
