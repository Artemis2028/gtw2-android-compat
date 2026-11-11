package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.nations.Side;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LaunchCueTest {

    @Test
    public void siloLaunchCountsAsSiloCueNotSub() {
        Launcher silo = Launcher.silo(new Vector3(10f, 0f, 10f), 10, true);
        Battle battle = armed(silo);
        City city = targetOf(battle);
        battle.launch(silo, city);
        assertEquals(1, battle.siloLaunches);
        assertEquals(0, battle.subLaunches);
        assertEquals(0, battle.bomberLaunches);
        assertEquals(1, battle.missiles.size());
    }

    @Test
    public void subLaunchCountsAsSubCueNotSilo() {
        Launcher sub = Launcher.submarine(new Vector3(10f, 0f, 10f), 10, true);
        Battle battle = armed(sub);
        City city = targetOf(battle);
        battle.launch(sub, city);
        assertEquals(0, battle.siloLaunches);
        assertEquals(1, battle.subLaunches);
        assertEquals(0, battle.bomberLaunches);
        assertEquals(1, battle.missiles.size());
    }

    @Test
    public void bomberSortieCountsAsBomberCueNotMissileCue() {
        Launcher bomber = Launcher.bomber(new Vector3(10f, 0f, 10f), 10);
        Battle battle = armed(bomber);
        City city = targetOf(battle);
        battle.launch(bomber, city);
        assertEquals(0, battle.siloLaunches);
        assertEquals(0, battle.subLaunches);
        assertEquals(1, battle.bomberLaunches);
        assertEquals(0, battle.missiles.size());
        assertEquals(1, battle.sorties.size());
    }

    private static Battle armed(Launcher source) {
        Match match = Match.create(Side.USA, Side.RUSSIA, 0);
        Battle battle = new Battle(match);
        source.owner = Side.USA;
        City city = new City(new Vector3(400f, 0f, 120f), 20);
        city.owner = Side.RUSSIA;
        match.player().add(source);
        match.opponent().add(city);
        match.player().remainingWeapons = 10;
        return battle;
    }

    private static City targetOf(Battle battle) {
        for (MapUnit unit : battle.match.opponent().units) {
            if (unit instanceof City) {
                return (City) unit;
            }
        }
        throw new IllegalStateException("no city");
    }
}
