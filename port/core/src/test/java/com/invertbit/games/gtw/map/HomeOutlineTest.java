package com.invertbit.games.gtw.map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.RegionId;
import com.invertbit.games.gtw.nations.Side;
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
    public void chileAndBrazilAreDrawnSeparately() throws Exception {
        WorldMap map = loadMap();
        assertTrue("brazil must be drawn", drawn(map, RegionId.brazil.name()));
        assertTrue("chile must be drawn as its own outline", drawn(map, RegionId.chile.name()));
    }

    @Test
    public void southAmericaTilesWithoutOverlappingMexico() throws Exception {
        WorldMap map = loadMap();
        List<List<Vector3>> brazil = ringsNamed(map, RegionId.brazil.name());
        List<List<Vector3>> chile = ringsNamed(map, RegionId.chile.name());
        List<List<Vector3>> mexico = ringsNamed(map, RegionId.mexico.name());
        assertFalse("brazil outline missing", brazil.isEmpty());
        assertFalse("chile outline missing", chile.isEmpty());
        assertFalse("mexico outline missing", mexico.isEmpty());

        // Equirectangular: x=(lon+180)*8, z=(90-lat)*(1920/180)
        assertOwned("Brasilia", brazil, chile, mexico, xz(-47.88f, -15.79f));
        assertOwned("Manaus", brazil, chile, mexico, xz(-60.02f, -3.12f));
        assertOwned("Sao Paulo", brazil, chile, mexico, xz(-46.63f, -23.55f));
        assertOwned("Santiago", chile, brazil, mexico, xz(-70.67f, -33.45f));
        assertOwned("Buenos Aires", chile, brazil, mexico, xz(-58.38f, -34.60f));
        assertOwned("Lima", chile, brazil, mexico, xz(-77.04f, -12.05f));
        assertOwned("Quito", chile, brazil, mexico, xz(-78.47f, -0.18f));
        assertOwned("Bogota", mexico, brazil, chile, xz(-74.07f, 4.71f));
        assertOwned("Georgetown", mexico, brazil, chile, xz(-58.16f, 6.80f));
        assertOwned("Cayenne", mexico, brazil, chile, xz(-52.33f, 4.94f));
        // Game city slots (Nations.SOUTH_AMERICA_CITIES x,z): all on SA's own land.
        assertOwned("Lima (game)", chile, brazil, mexico, new float[]{819f, 1094f});
        assertOwned("Manaus (game)", brazil, chile, mexico, new float[]{956f, 998f});
        assertOwned("Brasilia (game)", brazil, chile, mexico, new float[]{1054f, 1135f});
        assertOwned("Sao Paulo (game)", brazil, chile, mexico, new float[]{1064f, 1219f});
        assertOwned("Buenos Aires (game)", chile, brazil, mexico, new float[]{969f, 1339f});
        assertOwned("Santiago (game)", chile, brazil, mexico, new float[]{870f, 1327f});
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

    @Test
    public void namedCitiesSitOnTheirOwnSideAndOffForeignGrids() throws Exception {
        WorldMap map = loadMap();
        SvgDocument doc = parseDoc();
        for (Side side : Nations.PICK_ORDER) {
            List<Nations.City> cities = Nations.cities(side);
            if (cities.isEmpty()) {
                continue;
            }
            List<List<Vector3>> own = new ArrayList<List<Vector3>>();
            for (WorldMap.Outline outline : map.drawableOutlines()) {
                if (Nations.isHome(side, outline.name) && outline.points.size() >= 3) {
                    own.add(outline.points);
                }
            }
            for (Nations.City city : cities) {
                assertTrue(side + " " + city.name + " is off its own land",
                        insideAny(own, city.x, city.z));
                float[] p = Nations.project(city.lat, city.lon);
                assertTrue(city.name + " drifted from its lat/lon",
                        Math.hypot(p[0] - city.x, p[1] - city.z) <= 20.0);
                for (Map.Entry<String, List<SvgRect>> grid : doc.layer.grids.entrySet()) {
                    if ("submarine".equals(grid.getKey()) || Nations.isHome(side, grid.getKey())) {
                        continue;
                    }
                    for (SvgRect cell : grid.getValue()) {
                        // Deployer clears own cells within 40 of a named city. SA's slots
                        // (the old Bogota/Caracas problem) keep that room from foreign
                        // grids; every side's city must at least not sit inside one.
                        float room = side == Side.SOUTH_AMERICA ? 40f : 0.01f;
                        float dx = Math.max(Math.max(cell.origin.x - city.x, 0f),
                                city.x - (cell.origin.x + cell.width));
                        float dz = Math.max(Math.max(cell.origin.z - city.z, 0f),
                                city.z - (cell.origin.z + cell.height));
                        assertTrue(side + " " + city.name + " is within " + room + " of "
                                        + grid.getKey() + "'s unit grid",
                                Math.hypot(dx, dz) >= room);
                    }
                }
            }
        }
    }

    @Test
    public void neighbouringRegionsDoNotOverlapAlongTheirSeams() throws Exception {
        // Shared borders coincide, so no outline of one region pokes into another
        // (the old double-line slivers). Checked by sampling a fine grid over the
        // bounding-box overlap of every pair of regions.
        WorldMap map = loadMap();
        Map<String, List<List<Vector3>>> byName = new HashMap<String, List<List<Vector3>>>();
        for (WorldMap.Outline outline : map.drawableOutlines()) {
            if (outline.name == null || outline.points.size() < 3) {
                continue;
            }
            List<List<Vector3>> list = byName.get(outline.name);
            if (list == null) {
                list = new ArrayList<List<Vector3>>();
                byName.put(outline.name, list);
            }
            list.add(outline.points);
        }
        int overlapCells = 0;
        String worst = null;
        for (Map.Entry<String, List<List<Vector3>>> a : byName.entrySet()) {
            for (Map.Entry<String, List<List<Vector3>>> b : byName.entrySet()) {
                if (a.getKey().compareTo(b.getKey()) >= 0) {
                    continue;
                }
                float[] box = overlapBox(a.getValue(), b.getValue());
                if (box == null) {
                    continue;
                }
                for (float x = box[0] + 0.25f; x < box[2]; x += 0.5f) {
                    for (float z = box[1] + 0.25f; z < box[3]; z += 0.5f) {
                        if (insideAny(a.getValue(), x, z) && insideAny(b.getValue(), x, z)) {
                            overlapCells++;
                            worst = a.getKey() + "/" + b.getKey() + " at " + x + "," + z;
                        }
                    }
                }
            }
        }
        // 0.25 area per cell; allow a couple of cells of float rounding.
        assertTrue("regions overlap in " + overlapCells + " cells, e.g. " + worst,
                overlapCells <= 8);
    }

    private static float[] overlapBox(List<List<Vector3>> a, List<List<Vector3>> b) {
        float[] ba = bounds(a);
        float[] bb = bounds(b);
        float x0 = Math.max(ba[0], bb[0]);
        float z0 = Math.max(ba[1], bb[1]);
        float x1 = Math.min(ba[2], bb[2]);
        float z1 = Math.min(ba[3], bb[3]);
        return (x0 < x1 && z0 < z1) ? new float[]{x0, z0, x1, z1} : null;
    }

    private static float[] bounds(List<List<Vector3>> rings) {
        float[] b = {Float.MAX_VALUE, Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE};
        for (List<Vector3> ring : rings) {
            for (Vector3 p : ring) {
                b[0] = Math.min(b[0], p.x);
                b[1] = Math.min(b[1], p.z);
                b[2] = Math.max(b[2], p.x);
                b[3] = Math.max(b[3], p.z);
            }
        }
        return b;
    }

    private static SvgDocument parseDoc() throws Exception {
        FileInputStream in = new FileInputStream(worldSvg());
        try {
            return SvgParser.parse(in);
        } finally {
            in.close();
        }
    }

    private static WorldMap loadMap() throws Exception {
        FileInputStream in = new FileInputStream(worldSvg());
        try {
            return WorldMap.load(in);
        } finally {
            in.close();
        }
    }

    private static boolean drawn(WorldMap map, String name) {
        for (WorldMap.Outline outline : map.drawableOutlines()) {
            if (name.equals(outline.name)) {
                return true;
            }
        }
        return false;
    }

    private static List<List<Vector3>> ringsNamed(WorldMap map, String name) {
        List<List<Vector3>> rings = new ArrayList<List<Vector3>>();
        for (WorldMap.Outline outline : map.drawableOutlines()) {
            if (name.equals(outline.name) && outline.points.size() >= 3) {
                rings.add(outline.points);
            }
        }
        return rings;
    }

    private static float[] xz(float lon, float lat) {
        return new float[]{(lon + 180f) * 8f, (90f - lat) * (1920f / 180f)};
    }

    private static void assertOwned(String label, List<List<Vector3>> owner,
                                    List<List<Vector3>> otherA, List<List<Vector3>> otherB,
                                    float[] xz) {
        assertTrue(label + " should be inside its region", insideAny(owner, xz[0], xz[1]));
        assertFalse(label + " leaked into a neighbor", insideAny(otherA, xz[0], xz[1]));
        assertFalse(label + " leaked into a neighbor", insideAny(otherB, xz[0], xz[1]));
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
