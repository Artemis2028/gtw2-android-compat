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
}
