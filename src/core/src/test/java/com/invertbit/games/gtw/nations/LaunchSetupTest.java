package com.invertbit.games.gtw.nations;

import com.invertbit.games.gtw.a.Cg;
import com.invertbit.games.gtw.a.Ci;
import com.invertbit.games.gtw.a.Cj;
import com.invertbit.games.gtw.b.Cf;
import com.invertbit.games.gtw.b.e.Ce;
import com.invertbit.games.gtw.b.e.RoundSetup;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Regression for "crashed when launching SOUTH AMERICA vs SOVIETS" (logcat: NPE reading
 * {@code Cf.i} in c/Cl.a() on Start). Cause: world.svg's new outlines ended in an SVG closepath
 * {@code Z}; the game's path parser threw NumberFormatException on it, loading the map aborted,
 * the region map stayed empty and Start dereferenced the missing "submarine" sea grid.
 *
 * <p>These tests parse the real world.svg with the game's own SAX handler and run the real Start
 * path ({@link RoundSetup#deploy}) for every side pairing, alliances and difficulty, without GL.
 */
public class LaunchSetupTest {

    private static final File SVG = new File("../android/src/main/assets/world.svg");

    private static com.invertbit.games.c.a.a.Cd parseWorld() throws Exception {
        InputStream in = new FileInputStream(SVG);
        try {
            return com.invertbit.games.c.a.Ca.parse(in);
        } finally {
            in.close();
        }
    }

    /** Headless Gdx.graphics: map objects read the screen size (b/e/Ch) when created. */
    @org.junit.BeforeClass
    public static void headlessGraphics() {
        if (com.badlogic.gdx.Cf.b != null) {
            return;
        }
        com.badlogic.gdx.Cf.b = (com.badlogic.gdx.Cg) java.lang.reflect.Proxy.newProxyInstance(
                com.badlogic.gdx.Cg.class.getClassLoader(), new Class<?>[]{com.badlogic.gdx.Cg.class},
                new java.lang.reflect.InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, java.lang.reflect.Method m, Object[] args) {
                        Class<?> r = m.getReturnType();
                        if (r == int.class) {
                            return "a".equals(m.getName()) ? 2880 : 1920;   // width, height
                        }
                        if (r == float.class) {
                            return 1.0f;
                        }
                        if (r == long.class) {
                            return 0L;
                        }
                        if (r == boolean.class) {
                            return false;
                        }
                        if (r == double.class) {
                            return 0.0d;
                        }
                        return null;
                    }
                });
    }

    @Before
    public void freshSingletons() throws Exception {
        com.invertbit.games.gtw.b.e.Ca.a = null;
        setDifficulty(com.invertbit.games.gtw.Cb.Ia.NORMAL);
    }

    /** Sets the in-progress difficulty without touching libGDX Preferences. */
    private static void setDifficulty(com.invertbit.games.gtw.Cb.Ia level) throws Exception {
        Field uf = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
        uf.setAccessible(true);
        sun.misc.Unsafe unsafe = (sun.misc.Unsafe) uf.get(null);
        com.invertbit.games.gtw.Cb cb = (com.invertbit.games.gtw.Cb) unsafe.allocateInstance(com.invertbit.games.gtw.Cb.class);
        cb.b(level);
        Field a = com.invertbit.games.gtw.Cb.class.getDeclaredField("a");
        a.setAccessible(true);
        a.set(null, cb);
    }

    @Test
    public void worldSvgLoadsWithTheGameParser() throws Exception {
        com.invertbit.games.c.a.a.Cd cd = parseWorld();   // threw NumberFormatException("Z") before the fix
        assertNotNull(cd);
        assertNotNull(cd.c);
        assertTrue(cd.c.a().size() > 50);
    }

    @Test
    public void pathParserAcceptsClosePath() throws Exception {
        String svg = "<svg width=\"10\" height=\"10\"><g><path d=\"M 0,0 L 10,0 L 10,10 L 0,0 Z\" onclick=\"x\" />"
                + "<path d=\"m 0,0 5,0 0,5 z\" onclick=\"y\" /></g></svg>";
        com.invertbit.games.c.a.a.Cd cd = com.invertbit.games.c.a.Ca.parse(new ByteArrayInputStream(svg.getBytes(StandardCharsets.UTF_8)));
        assertEquals(4, cd.c.a().get(0).a().size());
        assertEquals(3, cd.c.a().get(1).a().size());
    }

    @Test
    public void everyRegionHasOutlineGridAndAllianceLabel() throws Exception {
        Map<String, Cf> regions = com.invertbit.games.gtw.b.a.Ca.regions(parseWorld());
        for (Cf.Ib ib : Cf.Ib.values()) {
            Cf cf = regions.get(ib.name());
            assertNotNull(ib + ": no outline path in world.svg", cf);
            assertNotNull(ib + ": no unit grid (rect without onmouseover)", cf.i);
            assertFalse(ib + ": empty unit grid", cf.i.isEmpty());
            // c/Cp places the Alliance-screen button at cf.f; null = NPE when the region is offered.
            assertNotNull(ib + ": no label rect (rect with onmouseover)", cf.f);
        }
        Cf sea = regions.get("submarine");
        assertNotNull(sea);
        assertNotNull(sea.i);
        assertFalse(sea.i.isEmpty());
    }

    /** Same lookup as the battle map (b/d/Cg.a): a copy of the region's grid, error if empty. */
    private static Ce.Grid grid(final Map<String, Cf> regions) {
        return new Ce.Grid() {
            @Override
            public List<com.badlogic.gdx.math.Cl> cells(Cf.Ib region) {
                Cf cf = regions.get(region.name());
                if (cf == null || cf.i == null || cf.i.isEmpty()) {
                    throw new IllegalArgumentException(region.name());
                }
                return new ArrayList<com.badlogic.gdx.math.Cl>(cf.i);
            }
        };
    }

    private static Cg newGame(Ci.Ia player, Ci.Ia opponent, int defcon, Map<String, Cf> regions, boolean spendArsenal) {
        com.invertbit.games.gtw.b.a.Ca.a().installRegions(regions);
        Cg game = new Cg();
        game.a(player, Nations.home(player), true);
        game.a(opponent, Cg.a(opponent), false);
        game.c = defcon;
        // Alliance screen: the player takes what the alliance table offers at this DEFCON
        // (same rule the AI uses for its own allies).
        new com.invertbit.games.gtw.b.e.Ca().a(defcon, game.a());
        if (spendArsenal) {
            com.invertbit.games.gtw.a.Cb ab = game.a().i;
            Cj[] rows = {ab.f, ab.g, ab.h, ab.i};
            for (int k = 0; k < 6; k++) {
                for (Cj row : rows) {
                    ab.a(5, row);
                    ab.a(10, row);
                }
            }
        }
        return game;
    }

    @Test(timeout = 60000)
    public void southAmericaVsRussiaLaunches() throws Exception {
        Map<String, Cf> regions = com.invertbit.games.gtw.b.a.Ca.regions(parseWorld());
        Cg game = newGame(Ci.Ia.SOUTH_AMERICA, Ci.Ia.RUSSIA, 0, regions, false);
        RoundSetup.deploy(game, grid(regions));
        assertDeployed(game, "SOUTH_AMERICA vs RUSSIA");
    }

    @Test(timeout = 120000)
    public void everyPairingLaunchesAtEveryDefconAndDifficulty() throws Exception {
        int runs = 0;
        for (com.invertbit.games.gtw.Cb.Ia level : com.invertbit.games.gtw.Cb.Ia.values()) {
            setDifficulty(level);
            for (Ci.Ia player : Ci.Ia.values()) {
                for (Ci.Ia opponent : Ci.Ia.values()) {
                    if (player == opponent) {
                        continue;
                    }
                    for (int defcon : new int[]{0, 1, 2, 3, 5, 8, 9, 12}) {
                        for (boolean spend : new boolean[]{false, true}) {
                            com.invertbit.games.gtw.b.e.Ca.a = null;
                            // fresh map each game: ownership lives on the shared region objects
                            Map<String, Cf> regions = com.invertbit.games.gtw.b.a.Ca.regions(parseWorld());
                            String what = player + " vs " + opponent + " @DEFCON " + defcon + " " + level + (spend ? " manual" : " auto");
                            Cg game = newGame(player, opponent, defcon, regions, spend);
                            try {
                                RoundSetup.deploy(game, grid(regions));
                            } catch (RuntimeException e) {
                                throw new AssertionError(what, e);
                            }
                            assertDeployed(game, what);
                            runs++;
                        }
                    }
                }
            }
        }
        assertEquals(3 * 30 * 8 * 2, runs);
    }

    private static void assertDeployed(Cg game, String what) {
        java.util.Set<String> owned = new java.util.HashSet<String>();
        for (Ci ci : game.b) {
            for (Cf cf : ci.e()) {
                assertTrue(what + ": " + cf.e + " owned by two sides", owned.add(cf.e));
            }
        }
        for (Ci ci : game.b) {
            int cities = 0;
            int weapons = 0;
            for (com.invertbit.games.gtw.b.Ca u : ci.d) {
                assertNotNull(what, u);
                if (u instanceof com.invertbit.games.gtw.b.Cd) {
                    cities++;
                } else if (!(u instanceof Cf)) {
                    weapons++;
                }
            }
            assertEquals(what + ": " + ci.e + " cities placed", ci.i.a, cities);
            assertTrue(what + ": " + ci.e + " has no weapons", weapons > 0);
            assertTrue(what + ": " + ci.e + " owns its home", Nations.isHome(ci.e, ci.e().get(0).e));
        }
    }

    // ------------------------------------------------------------- map outlines (stray lines)

    /**
     * "Europe has a stray line": path_nato_main doubled back on itself (a 179-degree turn), and the
     * outline builder (b/a/Ca) joins thick-line segments with a miter, whose corner then landed
     * hundreds of units away: a long thin line across the map. The SVG must have no such turns...
     */
    @Test
    public void everyOutlineCornerIsSane() throws Exception {
        float max = com.invertbit.games.b.a.Ca.MAX_MITER;
        float minCosHalf = 1.0f / max;               // miter = width / cos(turn/2)
        List<String> bad = new ArrayList<String>();
        for (com.invertbit.games.c.a.a.Cb cb : parseWorld().c.a()) {
            List<com.badlogic.gdx.math.Cl> p = cb.a();
            for (int k = 1; k + 1 < p.size(); k++) {
                com.badlogic.gdx.math.Cl a = p.get(k - 1), b = p.get(k), c = p.get(k + 1);
                if (wraps(a, b) || wraps(b, c)) {
                    continue;
                }
                double ux = b.a - a.a, uz = b.c - a.c, vx = c.a - b.a, vz = c.c - b.c;
                double lu = Math.hypot(ux, uz), lv = Math.hypot(vx, vz);
                if (lu == 0 || lv == 0) {
                    continue;                        // duplicate point: builder skips the miter
                }
                double cos = Math.max(-1, Math.min(1, (ux * vx + uz * vz) / (lu * lv)));
                double cosHalf = Math.cos(Math.acos(cos) / 2);
                if (cosHalf < minCosHalf) {
                    bad.add(cb.a + " point " + k + " (" + b.a + "," + b.c + ") turns "
                            + Math.round(Math.toDegrees(Math.acos(cos))) + " deg");
                }
            }
        }
        assertTrue("outline spikes: " + bad, bad.isEmpty());
    }

    /**
     * "South America has a stray line": the new chile outline had a point exactly on the straight
     * line between its neighbours. There the two offset edges are parallel, the float intersection
     * lands ~50 units down the coast, and the twisted quad draws a sliver out into the sea. So:
     * every drawn quad stays next to its own segment, both with the original (uncapped) miter on the
     * shipped world.svg, and with the builder's cap on the old spike and the old straight corner.
     */
    @Test
    public void drawnOutlinesHaveNoJumpSegments() throws Exception {
        List<com.invertbit.games.c.a.a.Cb> shipped = parseWorld().c.a();
        assertNoJumps(shipped, Float.POSITIVE_INFINITY);
        assertNoJumps(shipped, com.invertbit.games.b.a.Ca.MAX_MITER);
        List<com.invertbit.games.c.a.a.Cb> old = com.invertbit.games.c.a.Ca.parse(new ByteArrayInputStream((
                "<svg width=\"10\" height=\"10\"><g>"
                        + "<path onclick=\"europe\" d=\"M 1520,480 L 1539.75,470.93 L 1548.62,467.67 "
                        + "L 1511.51,481.78 L 1496.98,491.55\"/>"
                        + "<path onclick=\"chile\" d=\"M 899.88,1555.24 L 887.77,1560.67 L 875.67,1549.82 "
                        + "L 859.53,1528.11 L 843.39,1506.40 L 835.32,1495.55\"/>"
                        + "</g></svg>").getBytes(StandardCharsets.UTF_8))).c.a();
        assertEquals(2, old.size());
        assertNoJumps(old, com.invertbit.games.b.a.Ca.MAX_MITER);
        for (com.invertbit.games.c.a.a.Cb cb : old) {
            boolean spiked = false;
            try {
                assertNoJumps(java.util.Collections.singletonList(cb), Float.POSITIVE_INFINITY);
            } catch (AssertionError expected) {
                spiked = true;                       // the regression input really reproduces the bug
            }
            assertTrue("old " + cb.a + " corner should spike with the original miter", spiked);
        }
    }

    private static void assertNoJumps(List<com.invertbit.games.c.a.a.Cb> paths, float maxMiter) {
        com.invertbit.games.b.a.Ca builder = new com.invertbit.games.b.a.Ca();
        float reach = com.invertbit.games.b.a.Ca.MAX_MITER * builder.c + 0.01f;
        for (com.invertbit.games.c.a.a.Cb cb : paths) {
            List<com.badlogic.gdx.math.Cl> p = cb.a();
            float minX = Float.MAX_VALUE, maxX = -Float.MAX_VALUE, minZ = Float.MAX_VALUE, maxZ = -Float.MAX_VALUE;
            for (com.badlogic.gdx.math.Cl v : p) {
                minX = Math.min(minX, v.a); maxX = Math.max(maxX, v.a);
                minZ = Math.min(minZ, v.c); maxZ = Math.max(maxZ, v.c);
            }
            double diag = Math.hypot(maxX - minX, maxZ - minZ);
            for (com.invertbit.games.b.a.Ca.Ia ia : builder.joined(cb, maxMiter)) {
                float[][] q = com.invertbit.games.b.a.Ca.corners(ia);
                // q: start-left, end-left, end-right, start-right, start, end
                if (Math.abs(q[4][0] - q[5][0]) == 2880f) {
                    continue;                        // map wrap: not drawn
                }
                double seg = dist(q[4], q[5]);
                String what = cb.a + " segment " + q[4][0] + "," + q[4][1] + " (miter cap " + maxMiter + ")";
                assertTrue(what, dist(q[0], q[4]) <= reach && dist(q[3], q[4]) <= reach);
                assertTrue(what, dist(q[1], q[5]) <= reach && dist(q[2], q[5]) <= reach);
                assertTrue(what + " edge longer than its segment", dist(q[0], q[1]) <= seg + 2 * reach);
                assertTrue(what + " edge vs region size", dist(q[0], q[1]) <= diag + 2 * reach);
            }
        }
    }

    /** Every region outline is a closed loop (the open Antarctica coast line is unnamed). */
    @Test
    public void everyRegionOutlineIsClosed() throws Exception {
        for (com.invertbit.games.c.a.a.Cb cb : parseWorld().c.a()) {
            if (cb.a == null || cb.a.isEmpty()) {
                continue;
            }
            List<com.badlogic.gdx.math.Cl> p = cb.a();
            com.badlogic.gdx.math.Cl first = p.get(0), last = p.get(p.size() - 1);
            double dx = Math.abs(first.a - last.a) % 2880f;
            dx = Math.min(dx, 2880f - dx);
            assertTrue(cb.a + " outline not closed: gap " + Math.hypot(dx, first.c - last.c),
                    Math.hypot(dx, first.c - last.c) <= 4.0);   // originals close within 3.2
        }
    }

    private static boolean wraps(com.badlogic.gdx.math.Cl a, com.badlogic.gdx.math.Cl b) {
        return a.a - 2880f == b.a || a.a + 2880f == b.a;
    }

    private static double dist(float[] a, float[] b) {
        return Math.hypot(a[0] - b[0], a[1] - b[1]);
    }

    // ------------------------------------------------------------- USA owns alaska

    @Test
    public void usaStartsWithAlaskaAsPlayerAndAsOpponent() throws Exception {
        Map<String, Cf> regions = com.invertbit.games.gtw.b.a.Ca.regions(parseWorld());
        Cg game = newGame(Ci.Ia.USA, Ci.Ia.RUSSIA, 0, regions, false);
        assertOwnsAlaska(game.a());
        RoundSetup.deploy(game, grid(regions));
        assertDeployed(game, "USA vs RUSSIA");

        freshSingletons();
        regions = com.invertbit.games.gtw.b.a.Ca.regions(parseWorld());
        game = newGame(Ci.Ia.RUSSIA, Ci.Ia.USA, 9, regions, false);
        Ci usa = game.b();
        assertOwnsAlaska(usa);
        RoundSetup.deploy(game, grid(regions));
        assertDeployed(game, "RUSSIA vs USA");
        for (Ci ci : game.b) {
            if (ci != usa) {
                assertFalse("alaska must not be an ally of " + ci.e, ci.d.contains(regions.get("alaska")));
            }
        }
        for (String region : new String[]{"usa", "alaska"}) {
            Cf.Ia original = com.invertbit.games.gtw.b.e.Cc.a(Cf.Ib.valueOf(region));
            Cf.Ia now = Nations.stats(region, Ci.Ia.USA);
            assertArrayEquals(region + " keeps its original stats", new int[]{original.a, original.b, original.c},
                    new int[]{now.a, now.b, now.c});
        }
    }

    private static void assertOwnsAlaska(Ci usa) {
        assertEquals(Ci.Ia.USA, usa.e);
        List<String> names = new ArrayList<String>();
        for (Cf cf : usa.e()) {
            names.add(cf.e);
        }
        assertEquals("usa", names.get(0));
        assertTrue("USA owns alaska: " + names, names.contains("alaska"));
        for (Cf cf : usa.e()) {
            if ("alaska".equals(cf.e)) {
                assertEquals("alaska drawn in USA colours", Ci.Ia.USA, cf.c);
            }
        }
    }

    /**
     * Original alliance rule (b/e/Ca, c/Cp): allies while own regions - 1 &lt; DEFCON. Sides with
     * two home regions (USA: usa + alaska, SOUTH_AMERICA: brazil + chile) therefore get one ally
     * fewer at each DEFCON than one-region sides. Documented, not changed.
     */
    @Test
    public void twoHomeSidesGetOneAllyFewer() throws Exception {
        for (int defcon = 0; defcon <= 3; defcon++) {
            for (Ci.Ia side : Ci.Ia.values()) {
                freshSingletons();
                Map<String, Cf> regions = com.invertbit.games.gtw.b.a.Ca.regions(parseWorld());
                Ci.Ia other = side == Ci.Ia.RUSSIA ? Ci.Ia.CHINA : Ci.Ia.RUSSIA;
                Cg game = newGame(side, other, defcon, regions, false);
                int homes = 1 + Nations.extraHomes(side).length;
                int allies = game.a().e().size() - homes;
                int expected = Math.max(0, defcon - (homes - 1));
                assertTrue(side + " at DEFCON " + defcon + ": " + allies + " allies, rule allows " + expected,
                        allies <= expected);
                if (homes == 2 && defcon <= 1) {
                    assertEquals(side + " has no allies at DEFCON " + defcon, 0, allies);
                }
            }
        }
    }

    // ------------------------------------------------------------- arsenal icons

    @Test
    public void arsenalIconMatchesTheRowForEverySide() {
        for (Ci.Ia side : Ci.Ia.values()) {
            assertEquals(com.invertbit.games.gtw.b.a.Ca.Ia.city, com.invertbit.games.gtw.c.Cw.icon(Cj.Ia.CITY, side));
            assertEquals(com.invertbit.games.gtw.b.a.Ca.Ia.silo, com.invertbit.games.gtw.c.Cw.icon(Cj.Ia.SILO, side));
            assertEquals(side + " bomber row shows a sub", Nations.bomber(side), com.invertbit.games.gtw.c.Cw.icon(Cj.Ia.BOMBER, side));
            assertEquals(com.invertbit.games.gtw.b.a.Ca.Ia.submarine, com.invertbit.games.gtw.c.Cw.icon(Cj.Ia.SUBMARINE, side));
        }
        assertEquals(com.invertbit.games.gtw.b.a.Ca.Ia.usabomber, com.invertbit.games.gtw.c.Cw.icon(Cj.Ia.BOMBER, Ci.Ia.USA));
        assertEquals(com.invertbit.games.gtw.b.a.Ca.Ia.russiabomber, com.invertbit.games.gtw.c.Cw.icon(Cj.Ia.BOMBER, Ci.Ia.RUSSIA));
        assertEquals(com.invertbit.games.gtw.b.a.Ca.Ia.chinabomber, com.invertbit.games.gtw.c.Cw.icon(Cj.Ia.BOMBER, Ci.Ia.CHINA));
    }
}
