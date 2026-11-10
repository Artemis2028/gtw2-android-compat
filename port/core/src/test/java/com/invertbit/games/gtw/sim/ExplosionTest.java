package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.nations.Side;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExplosionTest {

    @Test
    public void startsPartlyGrownAtOriginalInitialAge() {
        Explosion flash = new Explosion(100f, 200f);
        assertEquals(Explosion.INITIAL_AGE, flash.age, 0.001f);
        assertEquals(1.2f, flash.scale(), 0.001f);
        assertEquals(19.2f, flash.radius(), 0.001f);
        assertEquals(0.5f, flash.alpha(), 0.001f);
    }

    @Test
    public void growsLinearlyThenFinishesAtOriginalDuration() {
        Explosion flash = new Explosion(0f, 0f);
        assertFalse(flash.advance(1.5f));
        assertEquals(3.5f, flash.age, 0.001f);
        assertEquals(2.1f, flash.scale(), 0.001f);
        assertEquals(33.6f, flash.radius(), 0.001f);

        assertTrue(flash.advance(1.5f));
        assertEquals(Explosion.DURATION, flash.age, 0.001f);
        assertEquals(3.0f, flash.scale(), 0.001f);
        assertEquals(48.0f, flash.radius(), 0.001f);
        assertEquals(0.5f, flash.alpha(), 0.001f);
    }

    @Test
    public void strikeSpawnsFlashAtTheTarget() {
        Match match = Match.create(Side.USA, Side.RUSSIA, 0);
        Battle battle = new Battle(match);
        Launcher silo = Launcher.silo(new Vector3(10f, 0f, 10f), 10, true);
        silo.owner = Side.USA;
        City city = new City(new Vector3(400f, 0f, 120f), 20);
        city.owner = Side.RUSSIA;
        match.player().add(silo);
        match.opponent().add(city);
        match.player().remainingWeapons = 10;
        battle.launch(silo, city);
        assertEquals(0, battle.explosions.size());
        battle.advance((long) (battle.missiles.get(0).duration * 1000f) + 50);
        assertEquals(1, battle.explosions.size());
        Explosion flash = battle.explosions.get(0);
        assertEquals(400f, flash.x, 0.01f);
        assertEquals(120f, flash.z, 0.01f);
        assertEquals(Explosion.INITIAL_AGE, flash.age, 0.001f);
        assertEquals(19.2f, flash.radius(), 0.001f);
    }
}
