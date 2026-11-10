package com.invertbit.games.gtw.sim;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArsenalEditTest {

    private static Arsenal sheet() {
        Arsenal a = new Arsenal();
        a.populationCount = 6;
        a.siloCount = 10;
        a.submarineCount = 8;
        a.maxPoints = (10 * 5) + (8 * 5);
        a.city.add5(6 * 5);
        return a;
    }

    @Test
    public void emptySheetCanStartBecauseItWillAutoDeploy() {
        Arsenal a = sheet();
        assertTrue(a.empty());
        assertTrue(a.canStart());
    }

    @Test
    public void spendAndRefundFiveOnASilo() {
        Arsenal a = sheet();
        a.spend(5, a.silo);
        assertEquals(5, a.points);
        assertEquals(5, a.silo.m5);
        assertEquals(1, a.silo.count());
        assertFalse(a.empty());
        a.refund(5, a.silo);
        assertEquals(0, a.points);
        assertEquals(0, a.silo.m5);
    }

    @Test
    public void cannotRefundTheFreeCitySlots() {
        Arsenal a = sheet();
        assertEquals(6, a.city.count());
        a.refund(5, a.city);
        assertEquals(30, a.city.m5);
        a.spend(5, a.city);
        assertEquals(35, a.city.m5);
        a.refund(5, a.city);
        assertEquals(30, a.city.m5);
    }

    @Test
    public void spendingOnlyOnCitiesWithoutWeaponsBlocksStart() {
        Arsenal a = sheet();
        a.spend(5, a.city);
        assertFalse(a.empty());
        assertTrue(a.noWeapons());
        assertFalse(a.canStart());
        a.spend(5, a.silo);
        assertTrue(a.canStart());
    }

    @Test
    public void cannotOverspendPoints() {
        Arsenal a = sheet();
        a.maxPoints = 5;
        a.spend(10, a.silo);
        assertEquals(0, a.points);
        a.spend(5, a.silo);
        assertEquals(5, a.points);
        a.spend(5, a.silo);
        assertEquals(5, a.points);
    }
}
