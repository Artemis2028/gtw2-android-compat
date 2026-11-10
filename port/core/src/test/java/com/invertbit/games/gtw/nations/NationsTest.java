package com.invertbit.games.gtw.nations;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NationsTest {

    @Test
    public void originalSidesKeepTheirOrdinals() {
        assertEquals(0, Side.USA.ordinal());
        assertEquals(1, Side.RUSSIA.ordinal());
        assertEquals(2, Side.CHINA.ordinal());
    }

    @Test
    public void homesAndExtrasMatchTheDecompiledSource() {
        assertEquals(RegionId.usa, Nations.home(Side.USA));
        assertEquals(RegionId.russia, Nations.home(Side.RUSSIA));
        assertEquals(RegionId.china, Nations.home(Side.CHINA));
        assertEquals(RegionId.europe, Nations.home(Side.EUROPE));
        assertEquals(RegionId.africa, Nations.home(Side.AFRICA));
        assertEquals(RegionId.brazil, Nations.home(Side.SOUTH_AMERICA));
        assertArrayEquals(new RegionId[]{RegionId.alaska}, Nations.extraHomes(Side.USA));
        assertArrayEquals(new RegionId[]{RegionId.chile}, Nations.extraHomes(Side.SOUTH_AMERICA));
        assertEquals(0, Nations.extraHomes(Side.EUROPE).length);
        assertTrue(Nations.isHome(Side.USA, "alaska"));
        assertTrue(Nations.isHome(Side.SOUTH_AMERICA, "chile"));
        assertFalse(Nations.isHome(Side.USA, "canada"));
        assertEquals(Side.USA, Nations.ownerOf("alaska"));
        assertEquals(Side.SOUTH_AMERICA, Nations.ownerOf("brazil"));
        assertNull(Nations.ownerOf("canada"));
        assertEquals("SOUTH AMERICA", Nations.displayName(Side.SOUTH_AMERICA));
    }

    @Test
    public void coloursMatchTheDecompiledSource() {
        assertArrayEquals(new float[]{0.0f, 0.52f, 1.0f}, Nations.rgb(Side.USA), 0.0001f);
        assertArrayEquals(new float[]{1.0f, 0.37f, 0.0f}, Nations.rgb(Side.RUSSIA), 0.0001f);
        assertArrayEquals(new float[]{0.0f, 1.0f, 0.5f}, Nations.rgb(Side.CHINA), 0.0001f);
        assertArrayEquals(new float[]{0.65f, 0.45f, 1.0f}, Nations.rgb(Side.EUROPE), 0.0001f);
        assertArrayEquals(new float[]{1.0f, 0.85f, 0.1f}, Nations.rgb(Side.AFRICA), 0.0001f);
        assertArrayEquals(new float[]{1.0f, 0.3f, 0.65f}, Nations.rgb(Side.SOUTH_AMERICA), 0.0001f);
    }


    @Test
    public void everySideHasADistinctHomeRegion() {
        java.util.Set<RegionId> homes = java.util.EnumSet.noneOf(RegionId.class);
        for (Side side : Side.values()) {
            assertTrue(side + " home reused", homes.add(Nations.home(side)));
            assertTrue(Nations.isHome(side, Nations.home(side).name()));
        }
        assertEquals(6, Nations.PICK_ORDER.length);
        assertEquals(java.util.EnumSet.allOf(Side.class), java.util.EnumSet.copyOf(java.util.Arrays.asList(Nations.PICK_ORDER)));
        assertTrue(Nations.isHome(Side.SOUTH_AMERICA, "brazil"));
        assertTrue(Nations.isHome(Side.SOUTH_AMERICA, "chile"));
        assertFalse(Nations.isHome(Side.SOUTH_AMERICA, "africa"));
        assertEquals(1, Nations.extraHomes(Side.SOUTH_AMERICA).length);
        assertEquals(RegionId.chile, Nations.extraHomes(Side.SOUTH_AMERICA)[0]);
        assertEquals(0, Nations.extraHomes(Side.EUROPE).length);
        assertTrue(Nations.isHome(Side.USA, "alaska"));
        assertFalse(Nations.isHome(Side.RUSSIA, "alaska"));
        assertEquals("SOUTH AMERICA", Nations.displayName(Side.SOUTH_AMERICA));
        assertFalse(Nations.isAnyHome(RegionId.easteurope));
        assertTrue(Nations.isAnyHome(RegionId.europe));
    }

    @Test
    public void newSideHomeGetsSuperpowerProfileButAllyKeepsItsOwn() {
        com.invertbit.games.gtw.sim.RegionStats usa = Nations.stats(RegionId.usa);
        for (Side side : new Side[]{Side.EUROPE, Side.AFRICA, Side.SOUTH_AMERICA}) {
            com.invertbit.games.gtw.sim.RegionStats s = Nations.stats(Nations.home(side).name(), side);
            assertEquals(usa.silos, s.silos);
            assertEquals(usa.submarines, s.submarines);
            assertEquals(usa.cities, s.cities);
        }
        com.invertbit.games.gtw.sim.RegionStats ally = Nations.stats("europe", Side.USA);
        com.invertbit.games.gtw.sim.RegionStats orig = Nations.stats(RegionId.europe);
        assertEquals(orig.silos, ally.silos);
        assertEquals(orig.submarines, ally.submarines);
        assertEquals(orig.cities, ally.cities);
    }

    @Test
    public void europeIsColdWarNatoCities() {
        java.util.Set<String> names = new java.util.HashSet<String>();
        for (Nations.City c : Nations.cities(Side.EUROPE)) {
            names.add(c.name);
        }
        assertTrue(names.contains("Lisbon"));
        assertTrue(names.contains("Bonn"));
        assertTrue(names.contains("Madrid"));
        assertFalse(names.contains("Berlin"));
        assertFalse(names.contains("Warsaw"));
        assertEquals(6, names.size());
    }

    @Test
    public void allianceTablesExistForEverySide() {
        com.invertbit.games.gtw.sim.AllianceTables tables = com.invertbit.games.gtw.sim.AllianceTables.get();
        for (Side side : Side.values()) {
            java.util.List<RegionId> early = tables.offer(side, 0);
            assertFalse(side + " has no allies", early.isEmpty());
            assertFalse(early.contains(Nations.home(side)));
            assertFalse(early.contains(RegionId.usa));
            java.util.List<RegionId> late = tables.offer(side, 9);
            assertFalse(late.contains(Nations.home(side)));
        }
        assertTrue(tables.offer(Side.EUROPE, 9).containsAll(java.util.Arrays.asList(RegionId.usa, RegionId.russia, RegionId.china)));
        assertFalse(tables.offer(Side.EUROPE, 8).contains(RegionId.usa));
    }

    @Test
    public void regionDisplayNamesAndPacificStats() {
        assertEquals("Egypt", Nations.displayName(RegionId.egypt));
        assertEquals("East Europe", Nations.displayName(RegionId.easteurope));
        assertEquals("Middle East", Nations.displayName(RegionId.middleeast));
        assertEquals("Pacific", Nations.displayName(RegionId.pacific));
        assertEquals("USA", Nations.displayName(RegionId.usa));
        assertEquals("Russia", Nations.displayName(RegionId.russia));
        com.invertbit.games.gtw.sim.RegionStats pacific = Nations.stats(RegionId.pacific);
        assertEquals(15, pacific.silos);
        assertEquals(14, pacific.submarines);
        assertEquals(3, pacific.cities);
        com.invertbit.games.gtw.sim.RegionStats russia = Nations.stats(RegionId.russia);
        assertEquals(30, russia.silos);
        assertEquals(26, russia.submarines);
        assertEquals(6, russia.cities);
    }
}
