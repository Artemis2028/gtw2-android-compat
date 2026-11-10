package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LauncherTest {

    @Test
    public void bomberIsSlowerThanEveryMissileYield() {
        assertEquals(40.0f, Launcher.missileSpeed(5), 0.01f);
        assertEquals(20.0f, Launcher.missileSpeed(10), 0.01f);
        assertEquals(200.0f / 15.0f, Launcher.missileSpeed(15), 0.01f);
        assertEquals(10.0f, Launcher.BOMBER_SPEED, 0.01f);
        assertTrue(Launcher.BOMBER_SPEED < Launcher.missileSpeed(5));
        assertTrue(Launcher.BOMBER_SPEED < Launcher.missileSpeed(10));
        assertTrue(Launcher.BOMBER_SPEED < Launcher.missileSpeed(15));
    }

    @Test
    public void bomberFlightTimeUsesCruiseSpeed() {
        Launcher bomber = Launcher.bomber(new Vector3(0f, 0f, 0f), 10);
        City city = new City(new Vector3(300f, 0f, 400f), 20);
        bomber.beginMoveTo(city);
        assertEquals(50.0f, bomber.moveDuration, 0.01f);
    }

    @Test
    public void parkedBomberFacesNorth() {
        assertEquals(0f, Launcher.bomber(new Vector3(0f, 0f, 0f), 5).headingDegrees(), 0.01f);
    }

    @Test
    public void flyingBomberNoseFollowsVelocity() {
        Launcher bomber = Launcher.bomber(new Vector3(100f, 0f, 100f), 5);
        bomber.beginMoveTo(new City(new Vector3(100f, 0f, 0f), 10));
        assertEquals(0f, bomber.headingDegrees(), 0.5f);

        bomber = Launcher.bomber(new Vector3(100f, 0f, 100f), 5);
        bomber.beginMoveTo(new City(new Vector3(200f, 0f, 100f), 10));
        assertEquals(-90f, bomber.headingDegrees(), 0.5f);

        bomber = Launcher.bomber(new Vector3(100f, 0f, 100f), 5);
        bomber.beginMoveTo(new City(new Vector3(0f, 0f, 100f), 10));
        assertEquals(90f, bomber.headingDegrees(), 0.5f);

        bomber = Launcher.bomber(new Vector3(100f, 0f, 100f), 5);
        bomber.beginMoveTo(new City(new Vector3(100f, 0f, 200f), 10));
        assertEquals(180f, Math.abs(bomber.headingDegrees()), 0.5f);

        bomber = Launcher.bomber(new Vector3(0f, 0f, 0f), 5);
        bomber.beginMoveTo(new City(new Vector3(100f, 0f, -100f), 10));
        assertEquals(-45f, bomber.headingDegrees(), 0.5f);
    }
}
