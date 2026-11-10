package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MissileTest {

    @Test
    public void wrapShortestShiftsTheEndOnly() {
        Vector3 from = new Vector3(100f, 0f, 50f);
        Vector3 to = new Vector3(2000f, 0f, 50f);
        Missile.wrapShortest(from, to);
        assertEquals(100f, from.x, 0.01f);
        assertEquals(2000f - 2880f, to.x, 0.01f);

        from.set(2000f, 0f, 50f);
        to.set(100f, 0f, 50f);
        Missile.wrapShortest(from, to);
        assertEquals(2000f, from.x, 0.01f);
        assertEquals(100f + 2880f, to.x, 0.01f);

        from.set(10f, 0f, 0f);
        to.set(200f, 0f, 0f);
        Missile.wrapShortest(from, to);
        assertEquals(200f, to.x, 0.01f);
    }

    @Test
    public void cubicBezierHitsEndsAndCurvesInZ() {
        Vector3 p0 = new Vector3(0f, 0f, 100f);
        Vector3 p3 = new Vector3(400f, 0f, 100f);
        float dx = p3.x - p0.x;
        float ctrlZ = p3.z - Math.abs(dx) / 2.0f;
        if (ctrlZ < 0f) {
            ctrlZ = 10f;
        }
        Vector3 p1 = new Vector3(p0.x + dx / 2f, 0f, ctrlZ);
        assertEquals(10f, p1.z, 0.01f);

        Vector3 out = new Vector3();
        Missile.bezier(p0, p1, p3, 0f, out);
        assertEquals(0f, out.x, 0.01f);
        assertEquals(100f, out.z, 0.01f);
        Missile.bezier(p0, p1, p3, 1f, out);
        assertEquals(400f, out.x, 0.01f);
        assertEquals(100f, out.z, 0.01f);
        Missile.bezier(p0, p1, p3, 0.5f, out);
        assertEquals(200f, out.x, 0.5f);
        // Straight line would stay at z=100; coincident controls pull z down.
        assertEquals(32.5f, out.z, 0.5f);
    }

    @Test
    public void missileFollowsBezierForItsDuration() {
        Launcher silo = Launcher.silo(new Vector3(0f, 0f, 100f), 10, true);
        City city = new City(new Vector3(400f, 0f, 100f), 20);
        Missile missile = new Missile(silo, city);
        assertEquals(10f, missile.control.z, 0.01f);
        float expected = (float) Math.hypot(400f, 0f) * silo.speedFactor / 100f;
        assertEquals(expected, missile.duration, 0.01f);
        assertTrue(missile.advance(missile.duration));
        assertEquals(400f, missile.position.x, 0.5f);
        assertEquals(100f, missile.position.z, 0.5f);
    }
}
