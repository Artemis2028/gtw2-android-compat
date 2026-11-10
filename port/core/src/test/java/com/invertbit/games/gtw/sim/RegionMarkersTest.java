package com.invertbit.games.gtw.sim;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.map.SvgDocument;
import com.invertbit.games.gtw.map.SvgParser;
import com.invertbit.games.gtw.map.SvgPath;
import com.invertbit.games.gtw.nations.RegionId;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionMarkersTest {

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

    private static List<Vector3> square(float x, float z, float size) {
        return Arrays.asList(
                new Vector3(x, 0f, z),
                new Vector3(x + size, 0f, z),
                new Vector3(x + size, 0f, z + size),
                new Vector3(x, 0f, z + size)
        );
    }

    @Test
    public void interiorPrefersLargestLandmass() {
        List<List<Vector3>> rings = new ArrayList<List<Vector3>>();
        rings.add(square(0f, 0f, 10f));
        rings.add(square(100f, 0f, 40f));
        Vector3 marker = RegionMarkers.interiorOfLargest(rings);
        assertNotNull(marker);
        assertTrue("marker should sit in the large square, not the scrap: " + marker.x + "," + marker.z,
                RegionMarkers.contains(rings.get(1), marker.x, marker.z));
        assertTrue("pole should be near the large square's centre",
                Math.abs(marker.x - 120f) < 4f && Math.abs(marker.z - 20f) < 4f);
    }

    @Test
    public void everySelectableRegionMarkerIsOnItsLand() throws Exception {
        FileInputStream in = new FileInputStream(worldSvg());
        SvgDocument doc;
        try {
            doc = SvgParser.parse(in);
        } finally {
            in.close();
        }
        Map<String, Region> regions = WorldRegions.from(doc);
        Map<String, List<List<Vector3>>> rings = new HashMap<String, List<List<Vector3>>>();
        for (SvgPath path : doc.layer.paths()) {
            if (path.name == null || path.points().size() < 3) {
                continue;
            }
            List<List<Vector3>> list = rings.get(path.name);
            if (list == null) {
                list = new ArrayList<List<Vector3>>();
                rings.put(path.name, list);
            }
            list.add(path.points());
        }
        for (RegionId id : RegionId.values()) {
            Region region = regions.get(id.name());
            assertNotNull(id + ": missing region", region);
            assertNotNull(id + ": no interior marker", region.marker);
            List<List<Vector3>> land = rings.get(id.name());
            assertNotNull(id + ": no outline rings", land);
            boolean onLand = false;
            for (List<Vector3> ring : land) {
                if (RegionMarkers.contains(ring, region.marker.x, region.marker.z)) {
                    onLand = true;
                    break;
                }
            }
            assertTrue(id + " marker at " + region.marker.x + "," + region.marker.z + " is not on land",
                    onLand);
        }
    }

    @Test
    public void russiaIncludesBalticsAndMoldova() throws Exception {
        FileInputStream in = new FileInputStream(worldSvg());
        SvgDocument doc;
        try {
            doc = SvgParser.parse(in);
        } finally {
            in.close();
        }
        List<List<Vector3>> russia = new ArrayList<List<Vector3>>();
        for (SvgPath path : doc.layer.paths()) {
            if ("russia".equals(path.name) && path.points().size() >= 3) {
                russia.add(path.points());
            }
        }
        assertTrue("expected russia to keep a far-east or island ring, got " + russia.size(),
                russia.size() >= 2);
        // Equirectangular: x=(lon+180)*8, z=(90-lat)*(1920/180)
        assertTrue("Estonia should be russia", insideAny(russia, 1640.0f, 334.9f));
        assertTrue("Latvia should be russia", insideAny(russia, 1632.8f, 353.1f));
        assertTrue("Lithuania should be russia", insideAny(russia, 1631.2f, 370.1f));
        assertTrue("Moldova should be russia", insideAny(russia, 1667.2f, 456.5f));
    }

    @Test
    public void chinaIncludesMongolia() throws Exception {
        FileInputStream in = new FileInputStream(worldSvg());
        SvgDocument doc;
        try {
            doc = SvgParser.parse(in);
        } finally {
            in.close();
        }
        List<List<Vector3>> china = new ArrayList<List<Vector3>>();
        for (SvgPath path : doc.layer.paths()) {
            if ("china".equals(path.name) && path.points().size() >= 3) {
                china.add(path.points());
            }
        }
        assertTrue("expected china to keep Hainan after Mongolia dissolve, got " + china.size(),
                china.size() >= 2);
        assertTrue("Mongolia should be china", insideAny(china, 2270.4f, 458.7f));
    }

    private static boolean insideAny(List<List<Vector3>> rings, float x, float z) {
        for (List<Vector3> ring : rings) {
            if (RegionMarkers.contains(ring, x, z)) {
                return true;
            }
        }
        return false;
    }
}
