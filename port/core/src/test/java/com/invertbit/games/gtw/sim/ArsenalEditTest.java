package com.invertbit.games.gtw.sim;

import com.invertbit.games.gtw.nations.Side;

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

    @Test
    public void southAmericaHomesDoNotDoubleCountCitySlots() {
        Arsenal a = new Arsenal();
        Region brazil = new Region("brazil");
        brazil.owner = Side.SOUTH_AMERICA;
        Region chile = new Region("chile");
        chile.owner = Side.SOUTH_AMERICA;
        a.add(brazil);
        a.add(chile);
        // brazil is a new-side primary home, so it uses the USA 6-city profile;
        // chile keeps its own 3. Slots must be 9/9, not 15/9.
        assertEquals(9, a.populationCount);
        assertEquals(9, a.city.count());
        assertEquals(45, a.city.m5);
        assertEquals(45, a.siloCount);
        assertEquals(46, a.submarineCount);
        assertEquals((30 + 26 + 15 + 20) * 5, a.maxPoints);
    }

    @Test
    public void usaPlusAlaskaDoesNotDoubleCountCitySlots() {
        Arsenal a = new Arsenal();
        Region usa = new Region("usa");
        usa.owner = Side.USA;
        Region alaska = new Region("alaska");
        alaska.owner = Side.USA;
        a.add(usa);
        a.add(alaska);
        assertEquals(7, a.populationCount);
        assertEquals(7, a.city.count());
        assertEquals(35, a.city.m5);
    }

    @Test
    public void singleHomeStillGetsItsOwnCitySlots() {
        Arsenal a = new Arsenal();
        Region russia = new Region("russia");
        russia.owner = Side.RUSSIA;
        a.add(russia);
        assertEquals(6, a.populationCount);
        assertEquals(6, a.city.count());
        assertEquals(30, a.city.m5);
    }
}
