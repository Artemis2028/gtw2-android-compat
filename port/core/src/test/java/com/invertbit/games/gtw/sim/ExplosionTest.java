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
    public void shadeFollowsTheRocketobjectShaderFromCentreToRim() {
        // centre: v = 0, p.y = -1 -> (1/0.9) * |sin(-1)|
        assertEquals((float) (Math.sin(1.0) / 0.9), Explosion.shade(0f), 0.0005f);
        assertEquals(0.935f, Explosion.shade(0f), 0.001f);
        // half way out: v = asin(0.5)/PI = 1/6, p.y = -2/3
        assertEquals((float) (Math.sin(2.0 / 3.0) / 0.9), Explosion.shade(0.5f), 0.0005f);
        assertEquals(0f, Explosion.shade(1f), 0.0001f);
        float prev = Explosion.shade(0f);
        for (int i = 1; i <= 20; i++) {
            float s = Explosion.shade(i / 20f);
            assertTrue("shade must fall off towards the rim", s <= prev + 1e-6f);
            assertTrue(s >= 0f && s <= 1f);
            prev = s;
        }
        // much brighter than the old 0.5-alpha additive radial (0.5 at centre)
        assertTrue(Explosion.shade(0f) > 0.9f);
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
