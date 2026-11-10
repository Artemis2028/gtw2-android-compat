package com.invertbit.games.gtw.sim;

import com.badlogic.gdx.math.Vector3;
import com.invertbit.games.gtw.map.SvgDocument;
import com.invertbit.games.gtw.map.SvgParser;
import com.invertbit.games.gtw.map.SvgPath;
import com.invertbit.games.gtw.map.SvgRect;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Region name → map region (label rect, unit grid) of a parsed world.svg, plus
 * the "submarine" sea grid. The first path of each name wins (as in 1.5).
 * Alliance markers use the pole of inaccessibility of the largest landmass,
 * not the SVG label-rect origin (those callouts often sit in the ocean).
 */
public final class WorldRegions {
    private static WorldRegions instance;
    private Map<String, Region> regions;

    public static WorldRegions get() {
        if (instance == null) {
            instance = new WorldRegions();
        }
        return instance;
    }

    public Map<String, Region> all() {
        return regions;
    }

    public Region get(String name) {
        return regions == null ? null : regions.get(name);
    }

    public void install(Map<String, Region> map) {
        this.regions = map;
    }

    public void reset() {
        this.regions = null;
    }

    public static Map<String, Region> load(InputStream in) throws Exception {
        return from(SvgParser.parse(in));
    }

    public static Map<String, Region> from(SvgDocument doc) {
        Map<String, Region> out = new HashMap<String, Region>();
        if (doc == null || doc.layer == null) {
            return out;
        }
        Map<String, SvgRect> labels = doc.layer.labels;
        Map<String, List<SvgRect>> grids = doc.layer.grids;
        Map<String, List<List<Vector3>>> rings = new HashMap<String, List<List<Vector3>>>();
        for (SvgPath path : doc.layer.paths()) {
            if (path.name == null) {
                continue;
            }
            if (!out.containsKey(path.name)) {
                Region region = new Region(path.name);
                region.label = labels.get(region.name);
                List<SvgRect> cells = grids.get(region.name);
                if (cells != null) {
                    region.grid = cellsOf(cells);
                }
                out.put(path.name, region);
            }
            List<List<Vector3>> list = rings.get(path.name);
            if (list == null) {
                list = new ArrayList<List<Vector3>>();
                rings.put(path.name, list);
            }
            list.add(path.points());
        }
        for (Map.Entry<String, List<List<Vector3>>> e : rings.entrySet()) {
            Region region = out.get(e.getKey());
            if (region != null) {
                region.marker = RegionMarkers.interiorOfLargest(e.getValue());
            }
        }
        Region sea = new Region("submarine");
        List<SvgRect> seaCells = grids.get("submarine");
        if (seaCells != null) {
            sea.grid = cellsOf(seaCells);
        }
        out.put("submarine", sea);
        return out;
    }

    /**
     * Unit-placement grid of a list of SVG rects. Formula matches
     * {@code b.e.Ce.a(Cc)}: step 48, inset 32.
     */
    public static List<Vector3> cellsOf(List<SvgRect> rects) {
        List<Vector3> out = new ArrayList<Vector3>();
        for (SvgRect rect : rects) {
            int cols = Math.max(0, (int) ((rect.width - 32.0f) / 48.0f));
            int rows = Math.max(0, (int) ((rect.height - 32.0f) / 48.0f));
            Vector3 origin = rect.origin;
            for (int i = 0; i <= cols; i++) {
                for (int j = 0; j <= rows; j++) {
                    out.add(new Vector3(
                            origin.x + (i * 32.0f) + ((i * 32.0f) / 2.0f),
                            0.0f,
                            origin.z + (j * 32.0f) + ((j * 32.0f) / 2.0f)));
                }
            }
        }
        return out;
    }
}
