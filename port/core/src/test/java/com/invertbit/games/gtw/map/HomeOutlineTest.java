package com.invertbit.games.gtw.map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.nations.RegionId;
import com.invertbit.games.gtw.sim.RegionMarkers;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeOutlineTest {

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

    @Test
    public void chilePathRemainsForHitTestingButIsNotDrawn() throws Exception {
        FileInputStream in = new FileInputStream(worldSvg());
        WorldMap map;
        try {
            map = WorldMap.load(in);
        } finally {
            in.close();
        }
        boolean chileStored = false;
        for (WorldMap.Outline outline : map.outlines) {
            if (RegionId.chile.name().equals(outline.name)) {
                chileStored = true;
                break;
            }
        }
        assertTrue("chile must remain a real region outline for markers/hit-testing", chileStored);
        for (WorldMap.Outline outline : map.drawableOutlines()) {
            assertFalse("chile should be absorbed into the SA home display outline",
                    RegionId.chile.name().equals(outline.name));
        }
    }

    @Test
    public void southAmericaDisplayCoversBrazilAndChile() throws Exception {
        FileInputStream in = new FileInputStream(worldSvg());
        WorldMap map;
        try {
            map = WorldMap.load(in);
        } finally {
            in.close();
        }
        List<List<Vector3>> brazilDrawn = new ArrayList<List<Vector3>>();
        for (WorldMap.Outline outline : map.drawableOutlines()) {
            if (RegionId.brazil.name().equals(outline.name) && outline.points.size() >= 3) {
                brazilDrawn.add(outline.points);
            }
        }
        assertFalse("dissolved SA outline missing from drawable brazil", brazilDrawn.isEmpty());
        // Equirectangular: x=(lon+180)*8, z=(90-lat)*(1920/180)
        assertTrue("Brasilia should be inside the SA display outline",
                insideAny(brazilDrawn, 1056.8f, 1128.5f));
        assertTrue("Santiago should be inside the SA display outline",
                insideAny(brazilDrawn, 875.2f, 1316.3f));
    }

    @Test
    public void sameNameRingsNoLongerShareASeam() throws Exception {
        FileInputStream in = new FileInputStream(worldSvg());
        SvgDocument doc;
        try {
            doc = SvgParser.parse(in);
        } finally {
            in.close();
        }
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
        String[] homes = {"russia", "china", "europe", "pacific", "chile"};
        for (int h = 0; h < homes.length; h++) {
            List<List<Vector3>> named = rings.get(homes[h]);
            assertTrue(homes[h] + " missing", named != null && !named.isEmpty());
            for (int i = 0; i < named.size(); i++) {
                for (int j = i + 1; j < named.size(); j++) {
                    float dist = minVertexDistance(named.get(i), named.get(j));
                    assertTrue(homes[h] + " rings " + i + " and " + j
                                    + " still touch (" + dist + ")",
                            dist >= 1.2f);
                }
            }
        }
    }

    private static boolean insideAny(List<List<Vector3>> rings, float x, float z) {
        for (List<Vector3> ring : rings) {
            if (RegionMarkers.contains(ring, x, z)) {
                return true;
            }
        }
        return false;
    }

    private static float minVertexDistance(List<Vector3> a, List<Vector3> b) {
        float best = Float.MAX_VALUE;
        for (int i = 0; i < a.size(); i++) {
            Vector3 p = a.get(i);
            for (int j = 0; j < b.size(); j++) {
                Vector3 q = b.get(j);
                float dx = p.x - q.x;
                float dz = p.z - q.z;
                float d = (float) Math.sqrt(dx * dx + dz * dz);
                if (d < best) {
                    best = d;
                }
            }
        }
        return best;
    }
}
