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
