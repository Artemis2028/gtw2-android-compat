package com.invertbit.games.gtw.nations;

import com.invertbit.games.gtw.a.Ci;
import com.invertbit.games.gtw.b.Cf;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class NationsTest {

    @Test
    public void everySideHasADistinctHomeRegion() {
        Set<Cf.Ib> homes = EnumSet.noneOf(Cf.Ib.class);
        for (Ci.Ia ia : Ci.Ia.values()) {
            assertTrue(ia + " home reused", homes.add(Nations.home(ia)));
            assertTrue(Nations.isHome(ia, Nations.home(ia).name()));
        }
        assertEquals(6, Nations.PICK_ORDER.length);
        assertEquals(EnumSet.allOf(Ci.Ia.class), EnumSet.copyOf(java.util.Arrays.asList(Nations.PICK_ORDER)));
        assertTrue(Nations.isHome(Ci.Ia.SOUTH_AMERICA, "brazil"));
        assertFalse(Nations.isHome(Ci.Ia.SOUTH_AMERICA, "chile"));
        assertEquals("SOUTH AMERICA", Nations.displayName(Ci.Ia.SOUTH_AMERICA));
    }

    @Test
    public void originalSidesKeepTheirOrdinals() {
        assertEquals(0, Ci.Ia.USA.ordinal());
        assertEquals(1, Ci.Ia.RUSSIA.ordinal());
        assertEquals(2, Ci.Ia.CHINA.ordinal());
    }

    @Test
    public void newSideHomeGetsSuperpowerProfileButAllyKeepsItsOwn() {
        Cf.Ia usa = com.invertbit.games.gtw.b.e.Cc.a(Cf.Ib.usa);
        for (Ci.Ia ia : new Ci.Ia[]{Ci.Ia.EUROPE, Ci.Ia.AFRICA, Ci.Ia.SOUTH_AMERICA}) {
            Cf.Ia s = Nations.stats(Nations.home(ia).name(), ia);
            assertEquals(usa.a, s.a);
            assertEquals(usa.b, s.b);
            assertEquals(usa.c, s.c);
        }
        Cf.Ia ally = Nations.stats("europe", Ci.Ia.USA);
        Cf.Ia orig = com.invertbit.games.gtw.b.e.Cc.a(Cf.Ib.europe);
        assertEquals(orig.a, ally.a);
        assertEquals(orig.b, ally.b);
        assertEquals(orig.c, ally.c);
        // the originals are unchanged
        Cf.Ia russia = Nations.stats("russia", Ci.Ia.RUSSIA);
        assertEquals(com.invertbit.games.gtw.b.e.Cc.a(Cf.Ib.russia).a, russia.a);
    }

    @Test
    public void allianceTablesExistForEverySide() {
        com.invertbit.games.gtw.b.e.Cc cc = com.invertbit.games.gtw.b.e.Cc.a();
        for (Ci.Ia ia : Ci.Ia.values()) {
            List<Cf.Ib> early = cc.a(ia, 0);
            assertFalse(ia + " has no allies", early.isEmpty());
            assertFalse(early.contains(Nations.home(ia)));
            assertFalse(early.contains(Cf.Ib.usa));
            List<Cf.Ib> late = cc.a(ia, 9);
            assertFalse(late.contains(Nations.home(ia)));
        }
        assertTrue(cc.a(Ci.Ia.EUROPE, 9).containsAll(java.util.Arrays.asList(Cf.Ib.usa, Cf.Ib.russia, Cf.Ib.china)));
        assertFalse(cc.a(Ci.Ia.EUROPE, 8).contains(Cf.Ib.usa));
    }

    // ---- city data against the real map asset ------------------------------------------------

    private static List<List<float[]>> regionPolygons(String region) throws Exception {
        File svg = new File("../android/src/main/assets/world.svg");
        String s = new String(Files.readAllBytes(svg.toPath()), StandardCharsets.UTF_8);
        List<List<float[]>> polys = new ArrayList<List<float[]>>();
        Matcher m = Pattern.compile("<path\\b(.*?)/?>", Pattern.DOTALL).matcher(s);
        while (m.find()) {
            String a = m.group(1);
            Matcher oc = Pattern.compile("onclick=\"([^\"]*)\"").matcher(a);
            Matcher d = Pattern.compile("\\sd=\"([^\"]*)\"").matcher(a);
            if (!oc.find() || !oc.group(1).equals(region) || !d.find()) {
                continue;
            }
            List<float[]> poly = new ArrayList<float[]>();
            float x = 0, y = 0;
            boolean rel = false;
            for (String tok : d.group(1).trim().split("\\s+")) {
                if (tok.equalsIgnoreCase("m") || tok.equalsIgnoreCase("l")) {
                    rel = Character.isLowerCase(tok.charAt(0));
                    continue;
                }
                if (tok.equalsIgnoreCase("z")) {
                    continue;
                }
                String[] xy = tok.split(",");
                float px = Float.parseFloat(xy[0]), py = Float.parseFloat(xy[1]);
                x = rel ? x + px : px;
                y = rel ? y + py : py;
                poly.add(new float[]{x, y});
            }
            polys.add(poly);
        }
        return polys;
    }

    private static boolean inside(float x, float y, List<float[]> poly) {
        boolean c = false;
        for (int i = 0, j = poly.size() - 1; i < poly.size(); j = i++) {
            float[] p = poly.get(i), q = poly.get(j);
            if ((p[1] > y) != (q[1] > y) && x < (q[0] - p[0]) * (y - p[1]) / (q[1] - p[1]) + p[0]) {
                c = !c;
            }
        }
        return c;
    }

    @Test
    public void realCitiesSitInsideTheirHomeOutline() throws Exception {
        for (Ci.Ia ia : Ci.Ia.values()) {
            List<Nations.City> cities = Nations.cities(ia);
            if (Nations.isOriginal(ia)) {
                assertTrue(cities.isEmpty());
                continue;
            }
            // one real city per population slot of the (superpower-level) home profile
            assertEquals(com.invertbit.games.gtw.b.e.Cc.a(Cf.Ib.usa).c, cities.size());
            List<List<float[]>> polys = regionPolygons(Nations.home(ia).name());
            assertFalse(polys.isEmpty());
            for (Nations.City c : cities) {
                float[] p = Nations.project(c.lat, c.lon);
                double nudge = Math.hypot(p[0] - c.x, p[1] - c.z);
                assertTrue(c.name + " drifted " + nudge + " from its projection", nudge <= 15.0);
                boolean in = false;
                for (List<float[]> poly : polys) {
                    in |= inside(c.x, c.z, poly);
                }
                assertTrue(c.name + " is outside " + Nations.home(ia), in);
            }
            for (int i = 0; i < cities.size(); i++) {
                for (int j = 0; j < i; j++) {
                    Nations.City a = cities.get(i), b = cities.get(j);
                    assertTrue(a.name + "/" + b.name + " overlap", Math.hypot(a.x - b.x, a.z - b.z) >= 35.0);
                }
            }
        }
    }
}
