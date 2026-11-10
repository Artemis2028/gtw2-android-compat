package com.invertbit.games.gtw.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.invertbit.games.gtw.nations.Nations;
import com.invertbit.games.gtw.nations.RegionId;
import com.invertbit.games.gtw.nations.Side;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Region outlines loaded from {@code world.svg}. Every named path is kept so
 * multi-part regions (NATO islands, Pacific, Russia far-east, etc.) can be hit;
 * {@link #byName} points at the first outline of that name.
 *
 * <p>Touching rings of the same region are dissolved in {@code world.svg}.
 * Separate starting-home regions that touch (brazil+chile) keep their own
 * paths for markers and hit-testing; {@link #drawableOutlines()} swaps in the
 * dissolved {@code home_display.json} outline so the shared home draws as one.
 */
public final class WorldMap {

    public static final Color UNOWNED = new Color(0.35f, 0.38f, 0.42f, 1f);

    public final float width;
    public final float height;
    public final List<Outline> outlines = new ArrayList<Outline>();
    public final Map<String, Outline> byName = new LinkedHashMap<String, Outline>();
    private final Map<String, List<Outline>> homeDisplays = new HashMap<String, List<Outline>>();
    private List<Outline> drawable;

    public WorldMap(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public static WorldMap load(InputStream in) throws Exception {
        SvgDocument doc = SvgParser.parse(in);
        float w = doc.width > 0 ? doc.width : 2880f;
        float h = doc.height > 0 ? doc.height : 1920f;
        WorldMap map = new WorldMap(w, h);
        if (doc.layer == null) {
            return map;
        }
        for (SvgPath path : doc.layer.paths()) {
            if (path.name == null || path.points().size() < 2) {
                continue;
            }
            Outline outline = new Outline(path.name, path.points());
            map.outlines.add(outline);
            if (!map.byName.containsKey(path.name)) {
                map.byName.put(path.name, outline);
            }
        }
        map.loadHomeDisplays(openHomeDisplay());
        return map;
    }

    /**
     * Outlines to stroke on pick / alliance / battle / map screens: same as
     * {@link #outlines} except starting-home pairs that were dissolved into
     * {@code home_display.json} (currently South America).
     */
    public List<Outline> drawableOutlines() {
        if (drawable == null) {
            drawable = buildDrawable();
        }
        return drawable;
    }

    private List<Outline> buildDrawable() {
        if (homeDisplays.isEmpty()) {
            return outlines;
        }
        Set<String> replaced = new HashSet<String>(homeDisplays.keySet());
        Set<String> absorbed = new HashSet<String>();
        for (Side side : Side.values()) {
            String home = Nations.home(side).name();
            if (!replaced.contains(home)) {
                continue;
            }
            for (RegionId extra : Nations.extraHomes(side)) {
                absorbed.add(extra.name());
            }
        }
        List<Outline> out = new ArrayList<Outline>();
        Set<String> emitted = new HashSet<String>();
        for (Outline outline : outlines) {
            if (absorbed.contains(outline.name)) {
                continue;
            }
            if (replaced.contains(outline.name)) {
                if (emitted.add(outline.name)) {
                    out.addAll(homeDisplays.get(outline.name));
                }
                continue;
            }
            out.add(outline);
        }
        return out;
    }

    void loadHomeDisplays(InputStream in) {
        if (in == null) {
            return;
        }
        try {
            JsonValue root = new JsonReader().parse(in);
            for (JsonValue region = root.child; region != null; region = region.next) {
                List<Outline> rings = new ArrayList<Outline>();
                for (JsonValue ring = region.child; ring != null; ring = ring.next) {
                    List<Vector3> pts = new ArrayList<Vector3>();
                    for (JsonValue pt = ring.child; pt != null; pt = pt.next) {
                        pts.add(new Vector3(pt.getFloat(0), 0f, pt.getFloat(1)));
                    }
                    if (pts.size() >= 2) {
                        rings.add(new Outline(region.name, pts));
                    }
                }
                if (!rings.isEmpty()) {
                    homeDisplays.put(region.name, rings);
                }
            }
        } catch (Exception ignored) {
            homeDisplays.clear();
        } finally {
            try {
                in.close();
            } catch (Exception ignored) {
            }
        }
        drawable = null;
    }

    private static InputStream openHomeDisplay() {
        String[] candidates = {
                "../../src/android/src/main/assets/home_display.json",
                "../src/android/src/main/assets/home_display.json",
                "src/android/src/main/assets/home_display.json",
                "../android/src/main/assets/home_display.json",
                "home_display.json"
        };
        for (int i = 0; i < candidates.length; i++) {
            File file = new File(candidates[i]);
            if (file.isFile()) {
                try {
                    return new FileInputStream(file);
                } catch (Exception ignored) {
                }
            }
        }
        try {
            if (Gdx.files != null) {
                FileHandle handle = Gdx.files.internal("home_display.json");
                if (handle.exists()) {
                    return handle.read();
                }
            }
        } catch (Throwable ignored) {
        }
        return null;
    }

    public static Color colorFor(String regionName) {
        return colorFor(regionName, Nations.ownerOf(regionName));
    }

    public static Color colorFor(String regionName, Side owner) {
        if (owner == null) {
            return UNOWNED;
        }
        float[] rgb = Nations.rgb(owner);
        return new Color(rgb[0], rgb[1], rgb[2], 1f);
    }

    public static final class Outline {
        public final String name;
        public final List<Vector3> points;

        public Outline(String name, List<Vector3> points) {
            this.name = name;
            this.points = points;
        }
    }
}
