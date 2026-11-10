package com.invertbit.games.gtw.audio;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SoundsThrottleTest {

    @Before
    public void reset() {
        Sounds.resetLaunchThrottle();
    }

    @Test
    public void firstLaunchCueIsAllowed() {
        assertTrue(Sounds.tryAcquireLaunchSlot(1_000L));
    }

    @Test
    public void salvoWithin150msIsThrottled() {
        assertTrue(Sounds.tryAcquireLaunchSlot(1_000L));
        assertFalse(Sounds.tryAcquireLaunchSlot(1_149L));
        assertTrue(Sounds.tryAcquireLaunchSlot(1_150L));
    }
}
