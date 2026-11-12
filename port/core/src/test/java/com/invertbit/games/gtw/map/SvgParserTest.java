package com.invertbit.games.gtw.map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.invertbit.games.gtw.nations.RegionId;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

public class SvgParserTest {

    private static final File SVG = new File("../../src/android/src/main/assets/world.svg");

    static SvgDocument parseWorld() throws Exception {
        FileInputStream in = new FileInputStream(SVG);
        try {
            return SvgParser.parse(in);
        } finally {
            in.close();
        }
    }

    @Test
    public void worldSvgLoadsWithTheGameParser() throws Exception {
        SvgDocument doc = parseWorld();
        assertNotNull(doc);
        assertNotNull(doc.layer);
        assertTrue(doc.layer.paths().size() > 50);
        assertEquals(2880f, doc.width, 0.01f);
        assertEquals(1920f, doc.height, 0.01f);
    }

    @Test
    public void pathParserAcceptsClosePath() throws Exception {
        String svg = "<svg width=\"10\" height=\"10\"><g><path d=\"M 0,0 L 10,0 L 10,10 L 0,0 Z\" onclick=\"x\" />"
                + "<path d=\"m 0,0 5,0 0,5 z\" onclick=\"y\" /></g></svg>";
        SvgDocument doc = SvgParser.parse(new ByteArrayInputStream(svg.getBytes(StandardCharsets.UTF_8)));
        assertEquals(4, doc.layer.paths().get(0).points().size());
        assertEquals(3, doc.layer.paths().get(1).points().size());
        assertEquals("x", doc.layer.paths().get(0).name);
        assertEquals("y", doc.layer.paths().get(1).name);
    }

    @Test
    public void everyNamedRegionHasAnOutline() throws Exception {
        WorldMap map = WorldMap.load(new FileInputStream(SVG));
        for (RegionId id : RegionId.values()) {
            WorldMap.Outline outline = map.byName.get(id.name());
            assertNotNull(id + ": no outline path in world.svg", outline);
            assertTrue(id + ": outline too short", outline.points.size() >= 3);
        }
    }

    @Test
    public void namedRegionsKeepEveryPath() throws Exception {
        WorldMap map = WorldMap.load(new FileInputStream(SVG));
        int europe = 0;
        int pacific = 0;
        int russia = 0;
        int china = 0;
        for (WorldMap.Outline outline : map.outlines) {
            if ("europe".equals(outline.name)) {
                europe++;
            } else if ("pacific".equals(outline.name)) {
                pacific++;
            } else if ("russia".equals(outline.name)) {
                russia++;
            } else if ("china".equals(outline.name)) {
                china++;
            }
        }
        assertTrue("europe should keep NATO pieces, not just the first path: " + europe, europe > 1);
        assertTrue("pacific should keep Japan + Korea + Philippines: " + pacific, pacific > 1);
        assertTrue("russia should keep far-east/island rings after dissolving seams: " + russia, russia >= 2);
        assertTrue("china should keep Hainan after dissolving Mongolia: " + china, china >= 2);
    }

    /** User decision (t135u): Taiwan is part of the Pacific region, drawn like the Philippines pieces. */
    @Test
    public void taiwanIsPacificTerritory() throws Exception {
        WorldMap map = WorldMap.load(new FileInputStream(SVG));
        // Taiwan's centre and both tips, projected x = 1440.5 + 8.069 lon, z = 963.7 - 10.854 lat
        float[][] lonLat = {{120.96f, 23.70f}, {121.50f, 25.15f}, {120.85f, 22.05f}};
        for (float[] p : lonLat) {
            float x = 1440.5f + 8.069f * p[0];
            float z = 963.7f - 10.854f * p[1];
            String owner = null;
            int hits = 0;
            for (WorldMap.Outline outline : map.outlines) {
                if (com.invertbit.games.gtw.sim.RegionMarkers.contains(outline.points, x, z)) {
                    owner = outline.name;
                    hits++;
                }
            }
            assertEquals("exactly one outline covers Taiwan at " + p[0] + "," + p[1], 1, hits);
            assertEquals("pacific", owner);
            assertEquals(RegionId.pacific, RegionId.valueOf(owner));
        }
    }

    /** User decision (t136u): one Pacific grid cell on Taiwan so units can deploy there. */
    @Test
    public void taiwanHasOnePacificGridCellOnTheIsland() throws Exception {
        WorldMap map = WorldMap.load(new FileInputStream(SVG));
        java.util.Map<String, com.invertbit.games.gtw.sim.Region> regions =
                com.invertbit.games.gtw.sim.WorldRegions.load(new FileInputStream(SVG));
        float tx = 1440.5f + 8.069f * 120.96f;
        float tz = 963.7f - 10.854f * 23.70f;
        java.util.List<com.badlogic.gdx.math.Vector3> taiwan = null;
        for (WorldMap.Outline outline : map.outlines) {
            if ("pacific".equals(outline.name)
                    && com.invertbit.games.gtw.sim.RegionMarkers.contains(outline.points, tx, tz)) {
                taiwan = outline.points;
            }
        }
        assertNotNull("Taiwan outline", taiwan);
        java.util.List<com.badlogic.gdx.math.Vector3> pacific = regions.get("pacific").grid;
        assertEquals("Japan/Korea had 4 Pacific cells; Taiwan adds one", 5, pacific.size());
        int onTaiwan = 0;
        for (com.badlogic.gdx.math.Vector3 cell : pacific) {
            if (!com.invertbit.games.gtw.sim.RegionMarkers.contains(taiwan, cell.x, cell.z)) {
                continue;
            }
            onTaiwan++;
            for (WorldMap.Outline outline : map.outlines) {
                if (!"pacific".equals(outline.name)) {
                    assertTrue("Taiwan cell is inside " + outline.name,
                            !com.invertbit.games.gtw.sim.RegionMarkers.contains(outline.points, cell.x, cell.z));
                }
            }
            for (com.invertbit.games.gtw.sim.Region other : regions.values()) {
                if ("pacific".equals(other.name) || "submarine".equals(other.name)) {
                    continue;
                }
                for (com.badlogic.gdx.math.Vector3 g : other.grid) {
                    float d = (float) Math.hypot(g.x - cell.x, g.z - cell.z);
                    assertTrue("Taiwan cell is " + d + " from a " + other.name + " cell", d >= 40f);
                }
            }
        }
        assertEquals("exactly one Pacific cell on Taiwan", 1, onTaiwan);
    }

    /** User decision (t138u): Japan's cells were just offshore; every Pacific cell must be on Pacific land. */
    @Test
    public void everyPacificGridCellLiesInsideAPacificOutline() throws Exception {
        WorldMap map = WorldMap.load(new FileInputStream(SVG));
        java.util.Map<String, com.invertbit.games.gtw.sim.Region> regions =
                com.invertbit.games.gtw.sim.WorldRegions.load(new FileInputStream(SVG));
        java.util.List<com.badlogic.gdx.math.Vector3> pacific = regions.get("pacific").grid;
        assertEquals(5, pacific.size());
        for (com.badlogic.gdx.math.Vector3 cell : pacific) {
            String where = cell.x + "," + cell.z;
            boolean home = false;
            for (WorldMap.Outline outline : map.outlines) {
                boolean in = com.invertbit.games.gtw.sim.RegionMarkers.contains(outline.points, cell.x, cell.z);
                if ("pacific".equals(outline.name)) {
                    home |= in;
                } else {
                    assertTrue("Pacific cell " + where + " is inside " + outline.name, !in);
                }
            }
            assertTrue("Pacific cell " + where + " is not on Pacific land", home);
            for (com.invertbit.games.gtw.sim.Region other : regions.values()) {
                if ("pacific".equals(other.name) || "submarine".equals(other.name)) {
                    continue;
                }
                for (com.badlogic.gdx.math.Vector3 g : other.grid) {
                    float d = (float) Math.hypot(g.x - cell.x, g.z - cell.z);
                    assertTrue("Pacific cell " + where + " is " + d + " from a " + other.name + " cell", d >= 40f);
                }
            }
        }
    }
}
