package com.invertbit.games.gtw.sim;

import com.invertbit.games.gtw.nations.Side;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class AlliancePickTest {

    static File worldSvg() {
        String[] candidates = {
                "../../src/android/src/main/assets/world.svg",
                "../src/android/src/main/assets/world.svg",
                "src/android/src/main/assets/world.svg",
                "../android/src/main/assets/world.svg"
        };
        for (String path : candidates) {
            File f = new File(path);
            if (f.isFile()) {
                return f;
            }
        }
        throw new AssertionError("world.svg not found");
    }

    static Map<String, Region> parseWorld() throws Exception {
        InputStream in = new FileInputStream(worldSvg());
        try {
            return WorldRegions.load(in);
        } finally {
            in.close();
        }
    }

    @Before
    public void fresh() throws Exception {
        WorldRegions.get().reset();
        AlliancePicker.reset();
        WorldRegions.get().install(parseWorld());
    }

    @Test
    public void unownedHomesStayUnclaimedWhenNotInTheMatch() {
        Match match = Match.create(Side.SOUTH_AMERICA, Side.RUSSIA, 0);
        assertEquals(Side.SOUTH_AMERICA, WorldRegions.get().get("brazil").owner);
        assertEquals(Side.SOUTH_AMERICA, WorldRegions.get().get("chile").owner);
        assertEquals(Side.RUSSIA, WorldRegions.get().get("russia").owner);
        assertNull("USA is not in this match", WorldRegions.get().get("usa").owner);
        assertNull("Alaska is not in this match", WorldRegions.get().get("alaska").owner);
        assertEquals(2, match.player().regionCount());
        assertEquals(0, match.defcon);
        assertFalse(match.alliancesOpen());
    }

    @Test
    public void noPicksAtDefconZero() {
        Match match = Match.create(Side.SOUTH_AMERICA, Side.RUSSIA, 0);
        AlliancePicker picker = AlliancePicker.get();
        assertTrue(picker.eligible(match).isEmpty());
        Region kazakhstan = WorldRegions.get().get("kazakhstan");
        assertNotNull(kazakhstan);
        assertFalse(picker.tryPick(match, kazakhstan));
        assertEquals(2, match.player().regionCount());
    }

    @Test
    public void playerCanPickUpToDefconPlusOne() {
        Match match = Match.create(Side.SOUTH_AMERICA, Side.RUSSIA, 2);
        AlliancePicker picker = AlliancePicker.get();
        assertTrue(match.alliancesOpen());
        List<Region> offered = picker.eligible(match);
        assertFalse(offered.isEmpty());
        Region pick = offered.get(0);
        assertFalse("opponent home must not be offered", "russia".equals(pick.name));
        assertTrue(picker.tryPick(match, pick));
        assertEquals(3, match.player().regionCount());
        assertEquals(Side.SOUTH_AMERICA, pick.owner);
        assertTrue("markers hide once r >= defcon", picker.eligible(match).isEmpty());
        assertFalse(match.alliancesOpen());
    }
}
