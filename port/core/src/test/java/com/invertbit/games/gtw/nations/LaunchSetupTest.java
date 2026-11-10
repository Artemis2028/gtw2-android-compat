package com.invertbit.games.gtw.nations;

import com.invertbit.games.gtw.AiOff;
import com.invertbit.games.gtw.sim.AlliancePicker;
import com.invertbit.games.gtw.sim.Difficulty;
import com.invertbit.games.gtw.sim.Match;
import com.invertbit.games.gtw.sim.Player;
import com.invertbit.games.gtw.sim.Region;
import com.invertbit.games.gtw.sim.RoundSetup;
import com.invertbit.games.gtw.sim.WorldRegions;
import com.invertbit.games.gtw.sim.YieldRow;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Port of the source LaunchSetupTest pairing sweep: parse the real world.svg and
 * run {@link RoundSetup#deploy} for every side pairing, alliances and difficulty.
 */
public class LaunchSetupTest {

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
    public void freshSingletons() {
        WorldRegions.get().reset();
        AlliancePicker.reset();
        AiOff.reset();
        Difficulty.setInProgress(Difficulty.NORMAL);
    }

    @Test
    public void everyRegionHasOutlineGridAndAllianceLabel() throws Exception {
        Map<String, Region> regions = parseWorld();
        for (RegionId id : RegionId.values()) {
            Region region = regions.get(id.name());
            assertNotNull(id + ": no outline path in world.svg", region);
            assertNotNull(id + ": no unit grid (rect without onmouseover)", region.grid);
            assertFalse(id + ": empty unit grid", region.grid.isEmpty());
            assertNotNull(id + ": no label rect (rect with onmouseover)", region.label);
        }
        Region sea = regions.get("submarine");
        assertNotNull(sea);
        assertNotNull(sea.grid);
        assertFalse(sea.grid.isEmpty());
    }

    private static Match newGame(Side player, Side opponent, int defcon, Map<String, Region> regions,
                                 boolean spendArsenal) {
        WorldRegions.get().install(regions);
        Match match = Match.create(player, opponent, defcon);
        new AlliancePicker().assignAllies(defcon, match.player());
        if (spendArsenal) {
            YieldRow[] rows = {
                    match.player().arsenal.city,
                    match.player().arsenal.silo,
                    match.player().arsenal.bomber,
                    match.player().arsenal.submarine
            };
            for (int k = 0; k < 6; k++) {
                for (YieldRow row : rows) {
                    match.player().arsenal.spend(5, row);
                    match.player().arsenal.spend(10, row);
                }
            }
        }
        return match;
    }

    @Test(timeout = 60000)
    public void aiOffSkipsOpponentAlliancesButStillDeploys() throws Exception {
        AiOff.set(true);
        try {
            Map<String, Region> regions = parseWorld();
            Match match = newGame(Side.SOUTH_AMERICA, Side.RUSSIA, 5, regions, false);
            int homes = match.opponent().regionCount();
            RoundSetup.deploy(match, RoundSetup.gridFromWorld());
            assertEquals("AI-off picks no extra allies", homes, match.opponent().regionCount());
            assertDeployed(match, "AI-off SOUTH_AMERICA vs RUSSIA");
        } finally {
            AiOff.reset();
        }
    }

    @Test(timeout = 60000)
    public void southAmericaVsRussiaLaunches() throws Exception {
        Map<String, Region> regions = parseWorld();
        Match match = newGame(Side.SOUTH_AMERICA, Side.RUSSIA, 0, regions, false);
        RoundSetup.deploy(match, RoundSetup.gridFromWorld());
        assertDeployed(match, "SOUTH_AMERICA vs RUSSIA");
    }

    @Test(timeout = 120000)
    public void everyPairingLaunchesAtEveryDefconAndDifficulty() throws Exception {
        int runs = 0;
        for (Difficulty level : Difficulty.values()) {
            Difficulty.setInProgress(level);
            for (Side player : Side.values()) {
                for (Side opponent : Side.values()) {
                    if (player == opponent) {
                        continue;
                    }
                    for (int defcon : new int[]{0, 1, 2, 3, 5, 8, 9, 12}) {
                        for (boolean spend : new boolean[]{false, true}) {
                            AlliancePicker.reset();
                            Map<String, Region> regions = parseWorld();
                            String what = player + " vs " + opponent + " @DEFCON " + defcon + " " + level
                                    + (spend ? " manual" : " auto");
                            Match match = newGame(player, opponent, defcon, regions, spend);
                            try {
                                RoundSetup.deploy(match, RoundSetup.gridFromWorld());
                            } catch (RuntimeException e) {
                                throw new AssertionError(what, e);
                            }
                            assertDeployed(match, what);
                            runs++;
                        }
                    }
                }
            }
        }
        assertEquals(3 * 30 * 8 * 2, runs);
    }

    private static void assertDeployed(Match match, String what) {
        Set<String> owned = new HashSet<String>();
        for (Player player : match.sides) {
            for (Region region : player.regions()) {
                assertTrue(what + ": " + region.name + " owned by two sides", owned.add(region.name));
            }
        }
        for (Player player : match.sides) {
            int cities = player.cityCount();
            int weapons = player.weaponCount();
            assertEquals(what + ": " + player.side + " cities placed", player.arsenal.populationCount, cities);
            assertTrue(what + ": " + player.side + " has no weapons", weapons > 0);
            assertTrue(what + ": " + player.side + " owns its home",
                    Nations.isHome(player.side, player.regions().get(0).name));
        }
    }

    @Test
    public void usaStartsWithAlaskaAsPlayerAndAsOpponent() throws Exception {
        Map<String, Region> regions = parseWorld();
        Match match = newGame(Side.USA, Side.RUSSIA, 0, regions, false);
        assertOwnsAlaska(match.player());
        RoundSetup.deploy(match, RoundSetup.gridFromWorld());
        assertDeployed(match, "USA vs RUSSIA");

        freshSingletons();
        regions = parseWorld();
        match = newGame(Side.RUSSIA, Side.USA, 9, regions, false);
        Player usa = match.opponent();
        assertOwnsAlaska(usa);
        RoundSetup.deploy(match, RoundSetup.gridFromWorld());
        assertDeployed(match, "RUSSIA vs USA");
        for (Player player : match.sides) {
            if (player != usa) {
                assertFalse("alaska must not be an ally of " + player.side,
                        player.owns(regions.get("alaska")));
            }
        }
        for (String region : new String[]{"usa", "alaska"}) {
            com.invertbit.games.gtw.sim.RegionStats original =
                    Nations.stats(RegionId.valueOf(region));
            com.invertbit.games.gtw.sim.RegionStats now = Nations.stats(region, Side.USA);
            assertArrayEquals(region + " keeps its original stats",
                    new int[]{original.silos, original.submarines, original.cities},
                    new int[]{now.silos, now.submarines, now.cities});
        }
    }

    private static void assertOwnsAlaska(Player usa) {
        assertEquals(Side.USA, usa.side);
        java.util.List<String> names = new java.util.ArrayList<String>();
        for (Region region : usa.regions()) {
            names.add(region.name);
        }
        assertEquals("usa", names.get(0));
        assertTrue("USA owns alaska: " + names, names.contains("alaska"));
        for (Region region : usa.regions()) {
            if ("alaska".equals(region.name)) {
                assertEquals("alaska drawn in USA colours", Side.USA, region.owner);
            }
        }
    }

    @Test
    public void twoHomeSidesCountAsOneOwnedForAllyOffers() throws Exception {
        for (int defcon = 0; defcon <= 3; defcon++) {
            for (Side side : Side.values()) {
                freshSingletons();
                Map<String, Region> regions = parseWorld();
                Side other = side == Side.RUSSIA ? Side.CHINA : Side.RUSSIA;
                Match match = newGame(side, other, defcon, regions, false);
                int homes = 1 + Nations.extraHomes(side).length;
                int allies = match.player().regions().size() - homes;
                int expected = defcon;
                assertTrue(side + " at DEFCON " + defcon + ": " + allies + " allies, rule allows " + expected,
                        allies <= expected);
                assertEquals(side + " alliance-owned count",
                        1 + allies, match.player().allianceOwnedCount());
            }
        }
    }
}
