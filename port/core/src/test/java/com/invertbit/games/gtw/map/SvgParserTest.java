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
}
